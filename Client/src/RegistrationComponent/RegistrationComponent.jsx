import React, { useState } from "react";
import styles from "./RegistrationComponent.module.css";

const RegistrationComponent = ({ onClose }) => {
    const [formData, setFormData] = useState({
        username: "",
        email: "",
        password: "",
        confirmPassword: "",
    });

    const [agreedToTerms, setAgreedToTerms] = useState(false);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const toggleAgreement = () => {
        setAgreedToTerms(!agreedToTerms);
    };

    const handleRegister = async (e) => {
        e.preventDefault();

        if (!agreedToTerms) {
            setError("Ви повинні погодитися з умовами використання.");
            return;
        }

        if (formData.password !== formData.confirmPassword) {
            setError("Паролі не співпадають.");
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/auth/register`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    username: formData.username,
                    email: formData.email,
                    password: formData.password,
                    confirmPassword: formData.confirmPassword,
                }),
            });

            if (!response.ok) {
                throw new Error(`Помилка HTTP: ${response.status}`);
            }

            setSuccess(true);
            setError(null);
        } catch (err) {
            console.error("Помилка реєстрації:", err.message);
            setError("Реєстрація не вдалася. Спробуйте ще раз.");
        }
    };

    const handleOverlayClick = (e) => {
        if (e.target === e.currentTarget) {
            onClose();
        }
    };

    return (
        <div className={styles.overlay} onClick={handleOverlayClick}>
            <div className={styles.modal}>
                <div className={styles.container}>
                    <div className={styles.headerGroup}>
                        <h1 className={styles.headerText}>Реєстрація</h1>
                        <img
                            className={styles.closeIcon}
                            alt="Закрити"
                            src="/close-icon.svg"
                            onClick={onClose}
                        />
                    </div>
                    <div className={styles.registrationGroup}>
                        <div className={styles.inputLineContainer}>
                            <div className={styles.inputLineGroup}>
                                <label className={styles.inputLineItemHeader}>
                                    Ім'я користувача
                                </label>
                                <div className={styles.inputButtonGroup}>
                                    <input
                                        type="text"
                                        name="username"
                                        placeholder="Введіть ім'я користувача"
                                        value={formData.username}
                                        onChange={handleInputChange}
                                        className={styles.inputButtonText}
                                    />
                                </div>
                            </div>
                            <div className={styles.inputLineGroup}>
                                <label className={styles.inputLineItemHeader}>
                                    Email
                                </label>
                                <div className={styles.inputButtonGroup}>
                                    <input
                                        type="email"
                                        name="email"
                                        placeholder="Введіть email"
                                        value={formData.email}
                                        onChange={handleInputChange}
                                        className={styles.inputButtonText}
                                    />
                                </div>
                            </div>
                            <div className={styles.inputLineGroup}>
                                <label className={styles.inputLineItemHeader}>
                                    Пароль
                                </label>
                                <div className={styles.inputButtonGroup}>
                                    <input
                                        type="password"
                                        name="password"
                                        placeholder="Введіть пароль"
                                        value={formData.password}
                                        onChange={handleInputChange}
                                        className={styles.inputButtonText}
                                    />
                                </div>
                            </div>
                            <div className={styles.inputLineGroup}>
                                <label className={styles.inputLineItemHeader}>
                                    Підтвердьте пароль
                                </label>
                                <div className={styles.inputButtonGroup}>
                                    <input
                                        type="password"
                                        name="confirmPassword"
                                        placeholder="Повторіть пароль"
                                        value={formData.confirmPassword}
                                        onChange={handleInputChange}
                                        className={styles.inputButtonText}
                                    />
                                </div>
                            </div>
                        </div>
                        <div className={styles.confirmGoup}>
                            <div className={styles.confirmButton} onClick={toggleAgreement}>
                                <img
                                    src={agreedToTerms ? "/ri_checkbox-fill.svg" : "/carbon_checkbox.svg"}
                                    alt="checkbox"
                                    className={styles.confirmIcon}
                                />
                                <label className={styles.confirmText}>
                                    Я даю згоду на обробку персональних даних
                                </label>
                            </div>
                            <div className={styles.confirmButton} onClick={toggleAgreement}>
                                <img
                                    src={agreedToTerms ? "/ri_checkbox-fill.svg" : "/carbon_checkbox.svg"}
                                    alt="checkbox"
                                    className={styles.confirmIcon}
                                />
                                <label className={styles.confirmText}>
                                    Я бажаю отримувати розсилку
                                </label>
                            </div>
                        </div>
                    </div>
                    {error && <div className={styles.errorMessage}>{error}</div>}
                    {success && <div className={styles.successMessage}>Реєстрація успішна!</div>}
                    <div className={styles.registerButtonFrame}>
                        <button className={styles.registerButton} onClick={handleRegister}>
                            Зареєструватися
                        </button>
                        <div className={styles.licenseConfirmContainer}>
                            <div className={styles.licenseConfirmGroup}>
                                <div className={styles.licenseConfirm}>
                                    <div className={styles.licenseConfirmSimpleText}>
                                        Реєструючись, Ви погоджуєтесь з
                                    </div>
                                    <div className={styles.licenseConfirmLinkText}>
                                        Угодою користувача та Політикою конфіденційності.
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default RegistrationComponent;
