import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import ProductGroupSection from "../ProductGroupSection/ProductGroupSection"; 
import styles from './SimilarProductComponent.module.css';

function SimilarProductComponent() {
    const { id } = useParams(); 
    const [groupData, setGroupData] = useState(null); 
    const [loading, setLoading] = useState(true); 
    const [error, setError] = useState(null); 

    useEffect(() => {
        const fetchSimilarGames = async () => {
            setLoading(true);
            setError(null);

            const body = {
                criteria: "Схожі ігри",
                productsId: [parseInt(id, 10)], 
            };

            try {
                const response = await fetch("https://epic-loot-backend-production.up.railway.app/api/public/product/productGroup", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(body),
                });

                if (!response.ok) {
                    throw new Error(`Ошибка HTTP: ${response.status}`);
                }

                const data = await response.json();

                
                if (!data.products || !Array.isArray(data.products)) {
                    throw new Error("Невірний формат даних с сервера");
                }

                setGroupData(data);
            } catch (err) {
                console.error("Помилка завантаження даних:", err.message);
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchSimilarGames();
    }, [id]);

    if (loading) {
        return <div>Завантаження...</div>;
    }

    if (error) {
        return <div>Помилка: {error}</div>;
    }

    if (!groupData) {
        return <div>Нема даних для відображення</div>;
    }

    return (
        <div className={styles.parent}>
            <ProductGroupSection
                title={groupData.title} 
                products={groupData.products} 
                customerId={null} 
            />
        </div>
    );
}

export default SimilarProductComponent;
