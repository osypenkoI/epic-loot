import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";  // Импортируем useParams для получения параметра из URL
import PageBuilder from "../PageBuilder/PageBuilder"; // Импортируем PageBuilder

const SubCategoryPage = () => {
  const { subCategoryId } = useParams();  // Получаем ID подкатегории из URL
  const [pageData, setPageData] = useState(null);
  const [sortType, setSortType] = useState("price-asc"); // Начальный тип сортировки

  useEffect(() => {
    // Запрос на сервер для получения данных подкатегории с использованием subCategoryId
    fetch(`/api/public/subcategory/${subCategoryId}`) // Используем ID из URL
      .then(response => response.json())
      .then(data => setPageData(data))
      .catch(err => console.log(err));
  }, [subCategoryId]);  // Зависимость от subCategoryId, чтобы перезапросить при изменении

  const handleSortChange = (newSortType) => {
    setSortType(newSortType);
  };

  if (!pageData) return <div>Загрузка...</div>;

  return (
    <div>
      {/* Отображаем страницу с данными подкатегории */}
      <PageBuilder
        bannerUrl={pageData.bannerUrl}  // Если URL баннера есть
        listProductCard={pageData.listProductCard} // Передаем список продуктов, если он есть
        productGroups={pageData.productGroups} // Передаем группы продуктов, если они есть
        title={pageData.title} // Название страницы подкатегории
        sortType={sortType}  // Передаем тип сортировки
        onSortChange={handleSortChange} // Передаем функцию сортировки в PageBuilder
      />
    </div>
  );
};

export default SubCategoryPage;
