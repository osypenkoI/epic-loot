import React from "react";
import apiClient from "../config/ApiClient";
import styles from "./PaymentComponent.module.css"

const PaymentComponent = ({ productId, customerId, amount, children }) => {
  const handlePayment = async () => {
    try {
      const response = await apiClient.post("/api/customer/order/createOrder", {
        productId,
        customerId,
        price: amount,
      });

      const { data, signature } = response.data;

      // Автоматичне перенаправлення на LiqPay
      const form = document.createElement("form");
      form.method = "POST";
      form.action = "https://www.liqpay.ua/api/3/checkout";

      const dataInput = document.createElement("input");
      dataInput.type = "hidden";
      dataInput.name = "data";
      dataInput.value = data;

      const signatureInput = document.createElement("input");
      signatureInput.type = "hidden";
      signatureInput.name = "signature";
      signatureInput.value = signature;

      form.appendChild(dataInput);
      form.appendChild(signatureInput);
      document.body.appendChild(form);

      form.submit();
    } catch (error) {
      console.error("Помилка створення замовлення:", error.message);
    }
  };

  return <button 
   className={styles.button} onClick={handlePayment}>{children}</button>;
};

export default PaymentComponent;
