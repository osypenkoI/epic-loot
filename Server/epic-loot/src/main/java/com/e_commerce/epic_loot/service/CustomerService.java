package com.e_commerce.epic_loot.service;

import com.e_commerce.epic_loot.api.DTO.Auth.RegisterRequest;
import com.e_commerce.epic_loot.api.DTO.Page.ProductListDTO;
import com.e_commerce.epic_loot.api.DTO.Product.ReviewDTO;
import com.e_commerce.epic_loot.enumEntity.AuthorityRole;
import com.e_commerce.epic_loot.model.Customer;
import com.e_commerce.epic_loot.model.Product;
import com.e_commerce.epic_loot.model.Review;
import com.e_commerce.epic_loot.model.repository.CustomerRepository;
import com.e_commerce.epic_loot.model.repository.ProductRepository;
import com.e_commerce.epic_loot.model.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Преобразуем роли в authorities
        List<GrantedAuthority> authorities = customer.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())) // Преобразуем роли
                .collect(Collectors.toList()); // Собираем в список

        return org.springframework.security.core.userdetails.User
                .withUsername(customer.getUsername())
                .password(customer.getPassword())
                .authorities(authorities) // Устанавливаем authorities
                .build();
    }

    public Integer getCustomerId(String username) {
        return customerRepository.findByUsername(username).get().getId();
    }

    /**
     * Проверка валидности пароля.
     */
    private boolean isPassValid(String password) {
        return password.length() >= 8 &&
                password.matches(".*\\d.*") && // Должна быть хотя бы одна цифра
                password.matches(".*[A-Z].*"); // Должна быть хотя бы одна заглавная буква
    }

    /**
     * Регистрация нового пользователя.
     */
    public ResponseEntity<?> registerCustomer(RegisterRequest request) {
        if (!isPassValid(request.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body("Пароль должен содержать не менее 8 символов, включать цифры и заглавные буквы.");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body("Пароли не совпадают.");
        }

        // Проверка уникальности пользователя
        if (customerRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Пользователь с таким именем уже существует.");
        }

        Customer customer = new Customer();
        customer.setUsername(request.getUsername());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setPassword(passwordEncoder.encode(request.getPassword())); // Хэшируем пароль
        customer.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        // Устанавливаем роль "CUSTOMER"
        customer.setRoles(Collections.singletonList(AuthorityRole.CUSTOMER));

        customerRepository.save(customer);

        return ResponseEntity
                .status(201)
                .body("Пользователь успешно зарегистрирован!");
    }

    /**
     * Получение всех продуктов.
     */
    public List<Product> getCustomerRepository() {
        return productRepository.findAll();
    }


    public AuthorityRole getRoleByUsername(String username) {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return customer.getRoles().stream().findFirst().orElse(AuthorityRole.VIEWER);
    }

    public List<Product> getProductsFromWishlist(Integer customerId) {
        // Получаем список ID продуктов из вишлиста
        List<Integer> productIds = customerRepository.findByIdWishList(customerId);

        // Ищем соответствующие объекты Product
        return productIds.stream()
                .map(productId -> productRepository.findById(productId).orElse(null)) // Находим продукт по ID
                .filter(Objects::nonNull) // Убираем null-значения, если продукт не найден
                .toList(); // Преобразуем в список
    }

    public List<Product> getProductFromPurchasedGames(Integer customerId){

        List<Integer> productIds = customerRepository.findByIdPurchasedGames(customerId);

        // Ищем соответствующие объекты Product
        return productIds.stream()
                .map(productId -> productRepository.findById(productId).orElse(null)) // Находим продукт по ID
                .filter(Objects::nonNull) // Убираем null-значения, если продукт не найден
                .toList(); // Преобразуем в список
    }


    public void addProductToWishList(Integer customerId, Integer productId) {
        // Находим пользователя по его id
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (!customer.getWishlist().contains(productId)) {
            customer.getWishlist().add(productId);
            customerRepository.save(customer);
        }

        // Сохраняем изменения в базе данных
        customerRepository.save(customer);
    }

    public void deleteProductFromWishList(Integer customerId, Integer productId) {
        // Получаем вишлист пользователя
        List<Integer> wishlist = customerRepository.findByIdWishList(customerId);

        // Проверяем, если продукт есть в вишлисте
        if (wishlist.contains(productId)) {
            // Удаляем продукт из вишлиста
            wishlist.remove(productId);

            // Обновляем вишлист в базе данных
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found with id " + customerId));

            // Обновляем вишлист
            customer.setWishlist(wishlist);
            customerRepository.save(customer);
        }
    }

    public Boolean isProductInWishlist(Integer productId, Integer customerId) {
        List<Integer> wishlist = customerRepository.findByIdWishList(customerId);
       return wishlist.contains(productId);
    }

    public void createReview(ReviewDTO reviewDTO, Integer customerId) {
        Review review = new Review();
        // Проверяем, существует ли пользователь
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Проверяем, существует ли продукт
        Product product = productRepository.findById(reviewDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Проверяем, купил ли пользователь этот продукт
        if (!customer.getPurchasedGames().contains(reviewDTO.getProductId())) {
            throw new IllegalArgumentException("Customer has not purchased this product");
        }

        // Проверяем, оставил ли пользователь уже ревью на этот продукт
        if (reviewRepository.existsByCustomerAndProduct(customer, product)) {
            throw new IllegalArgumentException("Review already exists for this product");
        }
        review.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        review.setReviewText(reviewDTO.getReviewText());
        // Устанавливаем связи
        review.setCustomer(customer);
        review.setProduct(product);
        reviewRepository.save(review);

    }


    //TODO Восстановление пароля

}
