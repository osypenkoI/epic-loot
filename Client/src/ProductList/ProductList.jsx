import React, { useState, useEffect } from 'react';
import ProductCard from '../ProductCard/ProductCard';
import SortDropdown from '../SortComponent/SortComponent'; // Импортируем компонент сортировки
import styles from './ProductList.module.css';

const ProductListSection = ({ title, products, sortType, onSortChange }) => {
  const [sortedProducts, setSortedProducts] = useState(products); // Стейт для отсортированных продуктов
  const [currentSortType, setCurrentSortType] = useState(sortType); // Храним текущий тип сортировки

  // Эффект для сортировки продуктов
  useEffect(() => {
    // Если products изменились, сбрасываем сортировку
    let sortedArray = [...products]; // Делаем копию списка продуктов

    // Сортируем список продуктов в зависимости от типа сортировки
    switch (currentSortType) {
      case 'price-asc':
        sortedArray.sort((a, b) => a.price - b.price); // Сортировка по цене (возрастающе)
        break;
      case 'price-desc':
        sortedArray.sort((a, b) => b.price - a.price); // Сортировка по цене (убывающе)
        break;
      case 'title-asc':
        sortedArray.sort((a, b) => a.title.localeCompare(b.title)); // Сортировка по названию (A-Z)
        break;
      case 'title-desc':
        sortedArray.sort((a, b) => b.title.localeCompare(a.title)); // Сортировка по названию (Z-A)
        break;
      default:
        break;
    }

    setSortedProducts(sortedArray); // Обновляем список отсортированных продуктов
  }, [products, currentSortType]); // Зависимости: сортировка и список продуктов

  const handleSortChange = (newSortType) => {
    setCurrentSortType(newSortType); // Обновляем текущий тип сортировки
    if (onSortChange) onSortChange(newSortType); // Вызываем коллбек, если он передан
  };

  return (
    <div className={styles.productListContainer}>
      <div className={styles.productListHeader}>
        {/* Заголовок с сортировкой рядом */}
        <h1 className={styles.productListTitle}>{title}</h1>
        {onSortChange && (
          <SortDropdown
            currentSortType={currentSortType} // Передаем текущий тип сортировки
            onSortChange={handleSortChange} // Обработчик изменения сортировки
          />
        )}
      </div>

      <div className={styles.productList}>
        {sortedProducts.map((product) => (
          <ProductCard key={product.productId} game={product} />
        ))}
      </div>
    </div>
  );
};

export default ProductListSection;
