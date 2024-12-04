package com.e_commerce.epic_loot.service;

import com.e_commerce.epic_loot.api.DTO.PurchaseOrder.PurchaseOrderDTO;
import com.e_commerce.epic_loot.enumEntity.OrderStatus;
import com.e_commerce.epic_loot.model.Customer;
import com.e_commerce.epic_loot.model.PurchaseOrder;
import com.e_commerce.epic_loot.model.repository.CustomerRepository;
import com.e_commerce.epic_loot.model.repository.OrderRepository;
import com.e_commerce.epic_loot.model.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.e_commerce.epic_loot.enumEntity.OrderStatus.AWAITING_PAYMENT;
@Service
public class PurchaseOrderService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Value("${liqpay.public_key}")
    private String publicKey;

    @Value("${liqpay.private_key}")
    private String privateKey;

    public Map<String, String> createPurchaseOrderAndPayment(PurchaseOrderDTO purchaseOrderDTO) throws Exception {
        // 1. Створення замовлення
        PurchaseOrder po = new PurchaseOrder();
        po.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        po.setOrderStatus(OrderStatus.AWAITING_PAYMENT);
        po.setProduct(productRepository.findById(purchaseOrderDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Продукт не знайдено")));
        po.setCustomer(customerRepository.findById(purchaseOrderDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Користувач не знайдений")));
        po.setAmount(purchaseOrderDTO.getPrice());

        // Генерація унікального liqpayOrderId
        String liqpayOrderId = "order_" + System.currentTimeMillis();
        po.setLiqpayOrderId(liqpayOrderId);

        PurchaseOrder savedOrder = orderRepository.save(po);

        // 2. Підготовка даних для LiqPay
        Map<String, String> params = new HashMap<>();
        params.put("version", "3");
        params.put("public_key", publicKey);
        params.put("action", "pay");
        params.put("amount", purchaseOrderDTO.getPrice().toString());
        params.put("currency", "UAH");
        params.put("description", "Замовлення №" + savedOrder.getId());
        params.put("order_id", savedOrder.getLiqpayOrderId()); // Використовуємо liqpayOrderId
        params.put("server_url", "https://epic-loot-backend-production.up.railway.app/api/customer/order/callback");

        // 3. Генерація даних для форми LiqPay
        String data = Base64.getEncoder()
                .encodeToString(new ObjectMapper().writeValueAsString(params).getBytes());
        String signature = createSignature(privateKey + data + privateKey);

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("data", data);
        paymentData.put("signature", signature);

        return paymentData;
    }
    public void updateOrderStatus(String liqpayOrderId, OrderStatus status) {
        PurchaseOrder order = orderRepository.findByLiqpayOrderId(liqpayOrderId)
                .orElseThrow(() -> new RuntimeException("Замовлення не знайдено: " + liqpayOrderId));
        order.setOrderStatus(status);
        orderRepository.save(order);
    }

    private String createSignature(String data) throws Exception {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-1");
        byte[] hash = md.digest(data.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    public void addGameToCustomerLibrary(String orderId) {
        // Знаходимо замовлення за liqpayOrderId
        PurchaseOrder po = orderRepository.findByLiqpayOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Замовлення з ID " + orderId + " не знайдено"));

        // Отримуємо користувача
        Customer customer = po.getCustomer();
        if (customer == null) {
            throw new RuntimeException("Користувач для замовлення не знайдений.");
        }

        // Отримуємо продукт
        Integer productId = po.getProduct().getId();
        if (productId == null) {
            throw new RuntimeException("Продукт для замовлення не знайдений.");
        }

        // Додаємо продукт до бібліотеки користувача, якщо його ще немає
        if (!customer.getPurchasedGames().contains(productId)) {
            customer.getPurchasedGames().add(productId);
            customerRepository.save(customer); // Зберігаємо оновленого користувача в базі
            System.out.println("Гра ID " + productId + " успішно додана до бібліотеки користувача ID " + customer.getId());
        } else {
            System.out.println("Гра ID " + productId + " вже є у бібліотеці користувача ID " + customer.getId());
        }
    }


}




    //TODO

    //TODO

    //TODO

    /*
    * 1. нажимается кнопка купить
    * 2. Выполняется запрос на создание ордера, с телом айди пользователя айди продукта, ценой
    * 3. Запрос создает ордер, создает пеймент
    *
    *
    * */





