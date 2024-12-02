import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom"; 
import styles from './SearchComponent.module.css';

const SearchComponent = () => {
    const [query, setQuery] = useState("");
    const [products, setProducts] = useState([]);
    const navigate = useNavigate(); 

    useEffect(() => {
        if (query.length >= 3) { 
            fetch(`/api/public/product/search?query=${query}`)
                .then((response) => response.json())
                .then((data) => setProducts(data))
                .catch((error) => console.error(error));
        } else {
            setProducts([]); 
        }
    }, [query]);

    const handleCardClick = (id) => {
       
        navigate(`/productDetails/${id}`);
    };

    return (
        <div className={styles.searchWrapper}>
            <div className={styles.instanceParent}>
                <div className={styles.linearSearchMagniferWrapper}>
                    <img className={styles.linearSearchMagnifer} alt="search" src="/Magnifer.svg" />
                </div>
                <input
                    type="text"
                    className={styles.searchInput}
                    placeholder="Поиск по продуктам"
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                />
            </div>

           
            <div className={styles.searchResults}>
                {products.length > 0 ? (
                    products.map((product) => (
                        <div 
                            key={product.id} 
                            className={styles.productCard} 
                            onClick={() => handleCardClick(product.productId)} 
                        >
                            <div className={styles.productCardGroup}>
                                <div className={styles.productCardImage}>
                                    <img className={styles.productCardImageRes} src={product.imageUrl} alt={product.title} />
                                </div>
                                <div className={styles.productCardText}>
                                    <div className={styles.productTitle}>
                                        {product.title}
                                    </div>
                                    <div className={styles.productPrice}>
                                        {product.price} ₴
                                    </div>
                                </div>
                            </div>
                        </div>
                    ))
                ) : (
                    <div></div>
                )}
            </div>
        </div>
    );
};

export default SearchComponent;
