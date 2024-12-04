import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";  // Импортируем useParams для получения параметра из URL
import PageBuilder from "../PageBuilder/PageBuilder"; // Импортируем PageBuilder

const SubCategoryPage = () => {
  const { subCategoryId } = useParams();  // Получаем ID подкатегории из URL
  const [pageData, setPageData] = useState(null);
  const [sortType, setSortType] = useState("price-asc"); // Начальный тип сортировки

  useEffect(() => {
    const fetchSubCategoryData = async () => {
      try {
        const response = await apiClient.get(`/api/public/subcategory/${subCategoryId}`); // Запит через apiClient
        setPageData(response.data); // Зберігаємо отримані дані
      } catch (err) {
        console.error("Не вдалося завантажити дані підкатегорії:", err);
      }
    };
  
    fetchSubCategoryData();
  }, [subCategoryId]); // Перезапуск ефекту при зміні subCategoryId
  
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
