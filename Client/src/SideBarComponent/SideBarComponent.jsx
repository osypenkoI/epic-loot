import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"; // Хук для навигации
import styles from "./SideBarComponent.module.css";
import apiClient from "../config/ApiClient";

const SideBarComponent = () => {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();  

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await apiClient.get("api/public/category/categoryList");
        setCategories(response.data);
      } catch (err) {
        setError("Не вдалося завантажити категорії. Спробуйте ще раз.");
      } finally {
        setLoading(false);
      }
    };
    fetchCategories();
  }, []);

  const handleCategoryClick = (categoryId) => {
    
    navigate(`/category/${categoryId}`);
  };

  const handleSubCategoryClick = (subCategoryId) => {
   
    navigate(`/subcategory/${subCategoryId}`);
  };

  const handleLogoClick = () => {
   
    navigate("/");
  };

  if (loading) {
    return <div>Завантаження...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <div className={styles.test1}>
      <div className={styles.frameParent}>
        <div className={styles.test2}>
          
          <img 
            className={styles.test3} 
            src={"/Frame 3946.svg"} 
            alt="Logo"
            onClick={handleLogoClick}  
          />
        </div>
        {categories.map((category) => (
          <div key={category.id} className={styles.parent}>
            <div className={styles.div} onClick={() => handleCategoryClick(category.id)}>
              {category.title}
            </div>
            <div className={styles.group}>
              {category.subCategoryTitles.map((subCategory) => (
                <div 
                  key={subCategory.id} 
                  className={styles.mmorpg} 
                  onClick={() => handleSubCategoryClick(subCategory.id)}
                >
                  {subCategory.title}
                </div>
              ))}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default SideBarComponent;
