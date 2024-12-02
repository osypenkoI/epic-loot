import React, { useState, useEffect } from "react";
import PageBuilder from "../PageBuilder/PageBuilder"; // Імпортуємо PageBuilder
import apiClient from "../config/ApiClient"; // Імпортуємо apiClient

const PurchasedGamesPage = () => {
  const [pageData, setPageData] = useState(null);
  const [sortType, setSortType] = useState("price-asc"); // Початковий тип сортування
  const [isLoggedIn, setIsLoggedIn] = useState(false); // Прапорець для перевірки, чи авторизований користувач

  useEffect(() => {
    // Робимо запит на сервер для отримання даних придбаних ігор
    apiClient.get('/api/customer/purchasedGames') // Запит без передачі userId
      .then((response) => {
        setPageData(response.data);
        setIsLoggedIn(true);
      })
      .catch((err) => {
        console.error("Помилка при отриманні даних про придбані ігри", err);
        setIsLoggedIn(false);
      });
  }, []); // Ефект із порожнім масивом залежностей, щоб запросити дані один раз при монтуванні компонента

  const handleSortChange = (newSortType) => {
    setSortType(newSortType);
  };

  if (!isLoggedIn) {
    return <div>Будь ласка, увійдіть у свій обліковий запис для перегляду придбаних ігор.</div>; // Якщо не авторизований
  }

  if (!pageData) {
    return <div>Завантаження...</div>; // Поки триває завантаження
  }

  return (
    <div>
      <PageBuilder
        bannerUrl={pageData.bannerUrl} // Якщо URL банера є
        listProductCard={pageData.listProductCard} // Передаємо список продуктів
        productGroups={pageData.productGroups} // Передаємо групи продуктів
        title={pageData.title} // Назва сторінки
        sortType={sortType} // Передаємо тип сортування
        onSortChange={handleSortChange} // Передаємо функцію сортування в PageBuilder
      />
    </div>
  );
};

export default PurchasedGamesPage;
