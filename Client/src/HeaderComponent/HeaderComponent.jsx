import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; // Імпортуємо useNavigate для навігації
import LoginComponent from "../LoginComponent/LoginComponent"; // Імпорт компонента для логіну
import RegistrationComponent from "../RegistrationComponent/RegistrationComponent"; // Імпорт компонента для реєстрації
import SearchComponent from "../SearchComponent/SearchComponent";
import styles from "./HeaderComponent.module.css"; // Імпорт стилів

const HeaderComponent = () => {
  const [modalType, setModalType] = useState(null); // Тип модального вікна (логін або реєстрація)
  const navigate = useNavigate(); // Хук для навігації

  // Відкрити вікно входу
  const openLoginModal = () => setModalType("login");

  // Відкрити вікно реєстрації
  const openRegisterModal = () => setModalType("register");

  // Закрити модальне вікно
  const closeModal = () => setModalType(null);

  // Закрити вікно при кліку на фон
  const handleOverlayClick = (e) => {
    if (e.target === e.currentTarget) {
      closeModal();
    }
  };

  // Обробник кліка по сердечку (перенаправлення на /wishlist)
  const handleWishlistClick = () => {
    navigate("/wishlist"); // Перенаправлення на сторінку списку бажаного
  };

  const handleLibraryClick = () => {
    navigate("/purchasedGames"); // Перенаправлення на сторінку бібліотеки
  };

  return (
    <div className={styles.frameParent}>
      {modalType && (
        <div className={styles.overlay} onClick={handleOverlayClick}>
          {modalType === "login" && (
            <LoginComponent
              onClose={closeModal}
              onSwitchToRegister={openRegisterModal} // Передаємо функцію для переключення на реєстрацію
            />
          )}
          {modalType === "register" && (
            <RegistrationComponent onClose={closeModal} />
          )}
        </div>
      )}

      <SearchComponent/>

      <div className={styles.linearLikeHeartParent}>
        {/* При кліку на іконку сердечка, викликаємо handleWishlistClick */}
        <img
          className={styles.linearLikeHeart}
          alt="Список бажаного"
          src="/Heart.svg"
          onClick={handleWishlistClick} // Додаємо обробник кліку
        />
        <img className={styles.linearLikeHeart} alt="" src="/Library.svg" onClick={handleLibraryClick}/>
        <img
          className={styles.linearUsersUser}
          alt="Користувач"
          src="/User.svg"
          onClick={openLoginModal} // Відкриваємо модальне вікно при кліку на іконку користувача
        />
      </div>
    </div>
  );
};

export default HeaderComponent;
