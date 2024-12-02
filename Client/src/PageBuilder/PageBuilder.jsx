import React from "react";
import ProductGroupSection from "../ProductGroupSection/ProductGroupSection"; // Секція з групами
import ProductListSection from "../ProductList/ProductList"; // Змінений імпорт
import Header from "../HeaderComponent/HeaderComponent";
import SideBarComponent from "../SideBarComponent/SideBarComponent";
import Footer from "../FooterComponent/FooterComponent";
import styles from "./PageBuilder.module.css";

const PageBuilder = ({ bannerUrl, listProductCard, productGroups, title, sortType, onSortChange }) => {
  const getSortLabel = (sortType) => {
    switch (sortType) {
      case "price-asc":
        return "Сортування за ціною (за зростанням)";
      case "price-desc":
        return "Сортування за ціною (за спаданням)";
      case "title-asc":
        return "Сортування за назвою (A-Z)";
      case "title-desc":
        return "Сортування за назвою (Z-A)";
      default:
        return "";
    }
  };

  const renderProductSections = () => {
    // Якщо переданий список продуктів (listProductCard)
    if (listProductCard && listProductCard.products) {
      return (
        <ProductListSection
          title={listProductCard.title || title} // Використовуємо title зі списку або передаємо як є
          products={listProductCard.products}
          sortType={sortType}
          onSortChange={onSortChange} // Передаємо функцію сортування в ProductListSection
        />
      );
    }

    // Якщо передані групи продуктів (productGroups)
    if (productGroups && productGroups.length > 0) {
      return productGroups.map((group, index) => (
        <div key={index} className={styles.productGroupWrapper}>
          <ProductGroupSection
            title={group.title || title} // Використовуємо title, якщо воно є
            products={group.products}
          />
        </div>
      ));
    }

    // Якщо даних немає, виводимо повідомлення
    return <div>Немає продуктів для відображення</div>;
  };

  return (
    <div className={styles.pageBuilder}>
      {/* Сайдбар */}
      <SideBarComponent />

      <div className={styles.pageContent}>
        <Header />
        {/* Якщо є URL банера, відображаємо банер */}
        {bannerUrl && (
          <div className={styles.banner}>
            <img src={`/${bannerUrl}`} alt="Банер" className={styles.bannerImage} />
          </div>
        )}

        {/* Назва сторінки */}
        <div className={styles.pageTitle}>{title}</div>

        <div className={styles.test3}>
          {/* Відображаємо секції з продуктами */}
          {renderProductSections()}
        </div>
      </div>

      {/* Футер */}
      <div className={styles.footerFrame}>
        <Footer />
      </div>
    </div>
  );
};

export default PageBuilder;
