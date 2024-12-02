import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HomePage from "./HomePage/HomePage";
import SubCategoryPage from "./SubcategoryPage/SubCategoryPage";  // Страница подкатегории
import CategoryPage from "./CategoryPage/CategoryPage";  // Страница категории
import WishListPage from "./WishListPage/WishListPage";
import PurchasedGamesPage from "./PurchasedGamesPage/PurchasedGamesPage";
import SearchComponent from "./SearchComponent/SearchComponent";
import ProductPageComponent from "./ProductPageComponent/ProductPageComponent";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage/>} />
        <Route path="/category/:categoryId" element={<CategoryPage />} />
        <Route path="/subcategory/:subCategoryId" element={<SubCategoryPage />} />
        <Route path="/wishlist" element={<WishListPage/>}/>
        <Route path="/purchasedGames" element={<PurchasedGamesPage/>}/>;  
        <Route path="/productDetails/:id" element={<ProductPageComponent/>}/>
      </Routes>
      </Router>
   );
}


export default App;