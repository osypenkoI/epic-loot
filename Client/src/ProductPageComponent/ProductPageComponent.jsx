import React, { useState, useEffect } from 'react';
import styles from './ProductPageComponent.module.css';
import HeaderComponent from '../HeaderComponent/HeaderComponent';
import FooterComponent from '../FooterComponent/FooterComponent';
import SystemRequirementsComponent from '../SystemRequirementsComponent/SystemRequirementsComponent';
import SimilarProductComponent from '../SimilarProductComponent/SimilarProductComponent';
import ReviewsComponent from '../ReviewsComponent/ReviewsComponent';
import ProductSellComponent from '../ProductSellComponent/ProductSellComponent';
import ProductDetailsComponent from '../ProductDetailsComponent/ProductDetailsComponent';
import { useParams } from 'react-router-dom'; // Для отримання id продукту з URL
import apiClient from "../config/ApiClient";

const ProductPageComponent = () => {
    const { id } = useParams(); // Отримуємо id з URL
    const [product, setProduct] = useState(null); // Зберігаємо дані про продукт
    const [loading, setLoading] = useState(true); // Статус завантаження
    const [error, setError] = useState(null); // Статус помилки

    useEffect(() => {
        const fetchProductData = async () => {
            try {
                const response = await apiClient.get(`/api/public/product/viewProductDetails/${id}`);
                setProduct(response.data); // Встановлюємо дані про продукт
            } catch (err) {
                setError("Не вдалося завантажити дані про продукт.");
                console.error(err);
            } finally {
                setLoading(false); // Завершуємо завантаження
            }
        };
    
        fetchProductData();
    }, [id]); // Запит спрацьовує при зміні id продукту    

    if (loading) {
        return <div>Завантаження...</div>; // Показуємо завантаження, поки дані не отримано
    }

    if (error) {
        return <div>Помилка: {error}</div>; // Показуємо помилку, якщо вона виникла
    }

    return (
        <div className={styles.desktop1}>
            <HeaderComponent />
            <div className={styles.frameGroup}>
                <div className={styles.frameContainer}>
                    <img className={styles.frameChild} alt="main" src={`/${product.mainPictureUrl}`} />
                    <div className={styles.frameDiv}>
                        {product.otherPictureUrl.map((url, index) => (
                            <div key={index} className={styles.imageParent}>
                                <img className={styles.imageIcon} alt="additional" src={`/${url}`} />
                            </div>
                        ))}
                    </div>
                </div>
                <ProductDetailsComponent productData={product} />
                <ProductSellComponent productData={product} />
                <SystemRequirementsComponent product={product} />
                <SimilarProductComponent product={product} />
                <ReviewsComponent product={product} />
            </div>
            <div className={styles.footer}>
                <FooterComponent />
            </div>
        </div>
    );
};

export default ProductPageComponent;
