import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import styles from "./ProductDetailsComponent.module.css";
import apiClient from "../config/ApiClient"; // Імпортуємо apiClient

function ProductDetailsComponent({ productData }) {
  const { id } = useParams(); // Отримуємо ID товару з URL
  const [loading, setLoading] = useState(true);
  const [isInWishlist, setIsInWishlist] = useState(false); // Стан для відстеження вишліста
  const [heartIcon, setHeartIcon] = useState("/Heart.svg");

  useEffect(() => {
    if (productData) {
      setLoading(false); // Завершуємо завантаження, як тільки productData стає доступним
    }
    checkIfInWishlist();  // Перевіряємо, чи є товар у вишлісті
  }, [productData, id]);

  const checkIfInWishlist = async () => {
    try {
      const response = await apiClient.get(`/api/customer/wishlist/check/${id}`); // Використовуємо apiClient
      setIsInWishlist(response.data); // Приклад: true або false
      setHeartIcon(response.data ? "/FilledHeart.svg" : "/Heart.svg");  // Змінюємо іконку залежно від статусу
    } catch (error) {
      console.error("Помилка при запиті до сервера:", error);
    }
  };

  const handleWishlistClick = async () => {
    const method = isInWishlist ? "DELETE" : "POST"; // Вибираємо метод залежно від стану
    const url = `/api/customer/wishlist?productId=${id}`; // Передаємо ID через параметр запиту
    
    try {
      const response = await apiClient({ 
        method, 
        url 
      });

      if (response.status === 200) {
        setIsInWishlist(!isInWishlist); // Інвертуємо стан вишліста
        setHeartIcon(isInWishlist ? "/Heart.svg" : "/FilledHeart.svg"); 
      } else {
        console.error("Помилка при додаванні/видаленні товару з вишліста");
      }
    } catch (error) {
      console.error("Помилка при запиті до сервера:", error);
    }
  };

  if (loading) return <div>Завантаження...</div>;

  const { title, description, features, categories } = productData;

  return (
    <div className={styles.frame}>
      <div className={styles.frameContainer}>
        {/* Назва гри */}
        <div className={styles.nameGroup}>
          <div className={styles.nameText}>{title}</div>
          <img
            className={styles.likeIcon}
            alt="Like"
            src={heartIcon}
            onClick={handleWishlistClick} 
          />
        </div>

        
        <div className={styles.descriptionGroup}>
          <p className={styles.descriptionText}>{description}</p>
        </div>

        
        <div className={styles.categoryFrame}>
          {categories.map((category, index) => (
            <div className={styles.categoryButton} key={index}>
              <div className={styles.categoryButtonText}>#{category.title}</div>
            </div>
          ))}
        </div>

        
        </div>
        <div className={styles.functionalFrameContainer}>
          {features.map((feature, index) => (
            <div className={styles.functionalGroup} key={index}>
              <img
                className={styles.functionalIcon}
                alt="Check"
                src="/Check Square.svg"
              />
              <div className={styles.functionalText}>{feature.title}</div>
            </div>
          ))}
      </div>
    </div>
  );
}

export default ProductDetailsComponent;
