import React from "react";
import ProductCard from "../ProductCard/ProductCard"; 
import styles from "./ProductGroupSection.module.css";

const ProductGroupSection = ({ title, products, customerId }) => {
  return (
    <div className={styles.groupSection}>
      <h2 className={styles.groupTitle}>{title}</h2>
      <div className={styles.cardsContainer}>
        {products.map((product) => (
          <ProductCard key={product.productId} game={product} customerId={customerId} />
        ))}
      </div>
    </div>
  );
};

export default ProductGroupSection;