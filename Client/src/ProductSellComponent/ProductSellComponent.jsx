import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import styles from "./ProductSellComponent.module.css";
import PaymentComponent from "../PaymentComponent/PaymentComponent"; // Підключаємо PaymentComponent

function ProductSellComponent({ productData, customerId }) {
  const [loading, setLoading] = useState(true);
  const { id } = useParams();
  const [isDiscounted, setIsDiscounted] = useState(false); // Прапорець для перевірки знижки

  // Ініціалізація даних при отриманні через пропси
  useEffect(() => {
    if (productData) {
      setLoading(false);
      setIsDiscounted(!!productData.discountPrice); // Перевірка на наявність знижки
    }
  }, [productData]);

  if (loading) return <div>Завантаження...</div>;

  // Дані з пропсів
  const { title, price, discountPrice, productId } = productData;

  // Ціна з урахуванням знижки
  const finalPrice = isDiscounted ? discountPrice : price;

  return (
    <div className={styles.parent}>
      {/* Заголовок та ціна */}
      <div className={styles.frameContainer}>
        <div className={styles.nameText}>{title}</div>
        <div className={styles.frameGroup}>
          {/* Відображаємо ціну зі знижкою або звичайну */}
          <div className={styles.priceText}>
                <span className={styles.originalPrice}>{finalPrice}₴</span>
          </div>

          {/* Кнопка для оплати - замість кошика */}
          <PaymentComponent
            productId={id}
            customerId={customerId}
            amount={finalPrice} // Ціна з урахуванням знижки
            description={`Покупка гри: ${title}`}
          >
            <div className={styles.buyButton}>
              <div className={styles.buyButtonText}>
                Купити
              </div>
            </div>
          </PaymentComponent>
        </div>
      </div>
    </div>
  );
}

export default ProductSellComponent;
