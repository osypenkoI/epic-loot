import React, { useState, useEffect } from "react";
import PageBuilder from "../PageBuilder/PageBuilder"; 
import apiClient from "../config/ApiClient";

const WishlistPage = () => {
  const [pageData, setPageData] = useState(null);
  const [sortType, setSortType] = useState("price-asc"); 
  const [isLoggedIn, setIsLoggedIn] = useState(false); 

  useEffect(() => {

    apiClient.get('/api/customer/wishlist') 
      .then((response) => {
        setPageData(response.data);
        setIsLoggedIn(true);
      })
      .catch((err) => {
        console.error("Помилка при отриманні вішліста", err);
        setIsLoggedIn(false);
      });
  }, []); 

  const handleSortChange = (newSortType) => {
    setSortType(newSortType);
  };

  if (!isLoggedIn) {
    return <div>Будь ласка </div>; 
  }

  if (!pageData) {
    return <div>Загрузка...</div>; 
  }

  return (
    <div>
      <PageBuilder
        bannerUrl={pageData.bannerUrl} 
        listProductCard={pageData.listProductCard} 
        productGroups={pageData.productGroups} 
        title={pageData.title} 
        sortType={sortType}
        onSortChange={handleSortChange} 
      />
    </div>
  );
};

export default WishlistPage;
