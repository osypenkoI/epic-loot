import React, { useState, useEffect } from "react";
import PageBuilder from "../PageBuilder/PageBuilder"; // Импортируем PageBuilder
import SortDropdown from "../SortComponent/SortComponent"; // Импортируем компонент сортировки

const HomePage = () => {
  const [pageData, setPageData] = useState(null);
  const [sortType, setSortType] = useState("price-asc"); // Начальный тип сортировки

 useEffect(() => {
    // Получаем userId из localStorage или используем 1, если ID нет в localStorage
    const userId = localStorage.getItem("customerId") || "1";

    // Получаем базовый URL из переменной окружения
    const API_URL = 'https://epic-loot-backend-production.up.railway.app';

    // Запрос на сервер с ID пользователя
    fetch(`${API_URL}/api/public/home/${userId}`)
      .then(response => response.json())
      .then(data => setPageData(data))
      .catch(err => console.log(err));
  }, []);

  const handleSortChange = (newSortType) => {
    setSortType(newSortType);
  };

  if (!pageData) return <div>Загрузка...</div>;

  return (
    <div>
      {/* Компонент сортировки */}
      
      {/* Отображаем страницу с данными */}
      <PageBuilder
        bannerUrl={pageData.bannerUrl}  // Если URL баннера есть
        productGroups={pageData.productGroups}
        title={pageData.title} // Название страницы
        sortType={sortType}  // Передаем тип сортировки
      />
    </div>
  );
};

export default HomePage;
