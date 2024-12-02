import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./LoginComponent.module.css";
import apiClient from "../config/ApiClient"; // Імпортуємо API клієнт

const LoginComponent = ({ onClose, onSwitchToRegister }) => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);
    const [checked, setChecked] = useState(false);
    const navigate = useNavigate();

    const toggleCheckbox = () => {
        setChecked(!checked);
    };

    const handleRegisterClick = () => {
        console.log("Клік на Зареєструватися"); // Перевірка кліка
        onSwitchToRegister();
    };

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await apiClient.post("/api/auth/login", { username, password }); // Використовуємо API клієнт

            // Якщо відповідь успішна
            if (response.data.customerId) {
                localStorage.setItem("customerId", response.data.customerId);
            }

            navigate("/"); // Перенаправляємо користувача
        } catch (err) {
            console.error("Помилка входу:", err.message);
            setError("Невірні облікові дані. Спробуйте ще раз.");
        }
    };

    const handleOverlayClick = (e) => {
        if (e.target === e.currentTarget) {
            onClose(); // Закриваємо вікно
        }
    };

    return (
        <div className={styles.overlay} onClick={handleOverlayClick}>
            <div className={styles.modal}>
                <div className={styles.frameContainer}>
                    {/* Заголовок з кнопкою виходу */}
                    <div className={styles.groupHeader}>
                        <h1 className={styles.headerText}>Вхід</h1>
                        <img
                            className={styles.exitIcon}
                            alt="Закрити"
                            src="/close-icon.svg"
                            onClick={onClose} // Закриваємо вікно при натисканні на іконку
                        />
                    </div>

                    {/* Поля вводу */}
                    <div className={styles.groupContainer}>
                        <div className={styles.groupInputLine}>
                            <div className={styles.inputLineItem}>
                                <label className={styles.inputLineHeaderText}>
                                    Ім'я користувача
                                </label>
                                <div className={styles.inputLine}>
                                    <div className={styles.inputLineChild}>
                                        <input
                                            type="text"
                                            placeholder="Введіть ім'я користувача"
                                            value={username}
                                            onChange={(e) => setUsername(e.target.value)}
                                            className={styles.inputLineChildText}
                                        />
                                    </div>
                                </div>
                            </div>
                            <div className={styles.inputLineItem}>
                                <label className={styles.inputLineHeaderText}>
                                    Пароль
                                </label>
                                <div className={styles.inputLine}>
                                    <div className={styles.inputLineChild}>
                                        <input
                                            type="password"
                                            placeholder="Введіть пароль"
                                            value={password}
                                            onChange={(e) => setPassword(e.target.value)}
                                            className={styles.inputLineChildText}
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>

                        {/* Чекбокс "Запам'ятати мене" */}
                        <div className={styles.chechBox} onClick={toggleCheckbox}>
                            <div className={styles.checkBoxIconWrapper}>
                                <img
                                    src={checked ? "/ri_checkbox-fill.svg" : "/carbon_checkbox.svg"}
                                    alt="checkbox"
                                    className={styles.checkBoxIcon}
                                />
                            </div>
                            <label htmlFor="rememberMe" className={styles.chechBoxText}>
                                Запам'ятати мене
                            </label>
                        </div>
                    </div>

                    {/* Кнопка входу */}
                    <button className={styles.loginButton} onClick={handleLogin}>
                        <span className={styles.loginButtonText}>Увійти</span>
                    </button>

                    {/* Відновлення пароля */}
                    <div className={styles.passRecoverButton}>
                        <span className={styles.passRecoverButtonText}>
                            Забули пароль?
                        </span>
                    </div>

                    {/* Роздільник "або" */}
                    <div className={styles.orText}>або</div>

                    {/* Кнопка перемикання на реєстрацію */}
                    <div className={styles.registerButton} onClick={onSwitchToRegister}>
                        <span className={styles.registerButtonText}>Зареєструватися</span>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LoginComponent;
