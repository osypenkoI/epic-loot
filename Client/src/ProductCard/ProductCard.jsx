import React from "react";
import { useNavigate } from "react-router-dom"; // Импортируем useNavigate для навигации
import PaymentComponent from "../PaymentComponent/PaymentComponent"; 
import styles from "./ProductCard.module.css"; 

const ProductCard = ({ game, customerId }) => {
  const navigate = useNavigate(); // Хук для навигации
  const isDiscounted = game.discountPrice && game.discountPrice < game.price;

  const handleCardClick = () => {
    // Переход на страницу с деталями продукта по его id
    navigate(`/productDetails/${game.productId}`);
  };

  return (
    <div className={styles.card} onClick={handleCardClick}> {/* Добавляем обработчик клика на карточку */}
      <img className={styles.cardImage} alt={game.title} src={`/${game.imageUrl}`} />
      <div className={styles.cardContent}>
        <div className={styles.cardTitle}>{game.title}</div>
        
        <div className={styles.discountPriceWrapper}>
          {isDiscounted ? (
            <>
              <div className={styles.vectorParent}>
                <img className={styles.groupChild} alt="" src="/Vector 1.svg" />
                <div className={styles.originalPrice}>{game.price}₴</div>
              </div>
              
              <div className={styles.discountedPrice}>{game.discountPrice}₴</div>
            </>
          ) : (
            <div className={styles.price}>{game.price}₴</div>
          )}

          <PaymentComponent
            productId={game.productId}
            customerId={customerId}
            amount={isDiscounted ? game.discountPrice : game.price} 
            description={`Придбання гри: ${game.title}`}
          >
            <img className={styles.cartIcon} src="/Cart Large Minimalistic.svg" alt="Купити" />
          </PaymentComponent>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
