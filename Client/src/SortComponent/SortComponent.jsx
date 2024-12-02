import React from 'react';
import styles from './SortComponent.module.css';

const SortComponent = ({ currentSortType, onSortChange }) => {
  const handleSortChange = (event) => {
    const newSortType = event.target.value;
    if (onSortChange) {
      onSortChange(newSortType);
    }
  };

  return (
    <div className={styles.sortContainer}>
      <select
        id="sort"
        value={currentSortType}
        onChange={handleSortChange}
        className={styles.sortSelect}
      >
        <option className={styles.sortSelectOption} value="price-asc">
          По ціні (зростаюче)
        </option>
        <option className={styles.sortSelectOption} value="price-desc">
          По ціні (спадне)
        </option>
        <option className={styles.sortSelectOption} value="title-asc">
          По назві (A-Z)
        </option>
        <option className={styles.sortSelectOption} value="title-desc">
          По назві (Z-A)
        </option>
      </select>
    </div>
  );
};

export default SortComponent;
