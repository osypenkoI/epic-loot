import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";  // Хук для получения параметров из URL
import PageBuilder from "../PageBuilder/PageBuilder";
import apiClient from "../config/ApiClient";

const CategoryPage = () => {
  const { categoryId } = useParams(); // Получаем categoryId из URL
  const [pageData, setPageData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCategoryData = async () => {
      try {
        const response = await apiClient.get(`/api/public/category/${categoryId}`);
        setPageData(response.data);
      } catch (err) {
        setError("Не вдалося завантажити дані категорії.");
      } finally {
        setLoading(false);
      }
    };

    fetchCategoryData();
  }, [categoryId]); // Запрашиваем данные при изменении categoryId

  if (loading) {
    return <div>Загрузка...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <div>
      <PageBuilder 
        bannerUrl={pageData.bannerUrl}
        productGroups={pageData.productGroups}
        title={pageData.title}
      />
    </div>
  );
};

export default CategoryPage;
