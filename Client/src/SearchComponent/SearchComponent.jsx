import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom"; 
import styles from './SearchComponent.module.css';
import apiClient from "../config/ApiClient";

const SearchComponent = () => {
    const [query, setQuery] = useState("");
    const [loading, setLoading] = useState(true);
    const [products, setProducts] = useState([]);
    const navigate = useNavigate(); 

    useEffect(() => {
        const fetchSearchResults = async () => {
            if (query.length >= 3) { // Пошук тільки при введенні трьох або більше символів
                setLoading(true); // Початок завантаження
                try {
                    const response = await apiClient.get(`/api/public/product/search?query=${query}`);
                    setProducts(response.data); // Оновлюємо стейт з отриманими продуктами
                } catch (err) {
                    setError("Не вдалося завантажити продукти.");
                    console.error(err);
                } finally {
                    setLoading(false); // Завершуємо завантаження
                }
            } else {
                setProducts([]); // Очищаємо результати пошуку при менше ніж 3 символах
            }
        };
    
        fetchSearchResults();
    }, [query]); // Запит спрацьовує при зміні query    

    const handleCardClick = (id) => {
       
        navigate(`/productDetails/${id}`);
    };

    return (
        <div className={styles.searchWrapper}>
            <div className={styles.instanceParent}>
                <div className={styles.linearSearchMagniferWrapper}>
                    <img className={styles.linearSearchMagnifer} alt="search" src="/Magnifer.svg" />
                </div>
                <input
                    type="text"
                    className={styles.searchInput}
                    placeholder="Поиск по продуктам"
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                />
            </div>

           
            <div className={styles.searchResults}>
                {products.length > 0 ? (
                    products.map((product) => (
                        <div 
                            key={product.id} 
                            className={styles.productCard} 
                            onClick={() => handleCardClick(product.productId)} 
                        >
                            <div className={styles.productCardGroup}>
                                <div className={styles.productCardImage}>
                                    <img className={styles.productCardImageRes} src={product.imageUrl} alt={product.title} />
                                </div>
                                <div className={styles.productCardText}>
                                    <div className={styles.productTitle}>
                                        {product.title}
                                    </div>
                                    <div className={styles.productPrice}>
                                        {product.price} ₴
                                    </div>
                                </div>
                            </div>
                        </div>
                    ))
                ) : (
                    <div></div>
                )}
            </div>
        </div>
    );
};

export default SearchComponent;
