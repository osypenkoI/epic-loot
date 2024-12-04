import React, { useState, useEffect } from "react";
import styles from "./ReviewsComponent.module.css";
import apiClient from "../config/ApiClient";
import { useParams } from "react-router-dom";

function ReviewsComponent() {
  const { id: productId } = useParams(); 
  const [reviews, setReviews] = useState([]); 
  const [visibleReviews, setVisibleReviews] = useState([]); 
  const [newReview, setNewReview] = useState({
    productId: productId,
    rating: true, 
    reviewText: "",
  });
  const [loadedCount, setLoadedCount] = useState(3); 


  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const response = await apiClient.get(
          `/api/public/product/review/${productId}`
        );
        setReviews(response.data);
        setVisibleReviews(response.data.slice(0, loadedCount)); 
      } catch (error) {
        console.error("Помилка при завантаженні помилки:", error);
      }
    };

    fetchReviews();
  }, [productId, loadedCount]);

  
  const loadMoreReviews = () => {
    const newCount = loadedCount + 3;
    setVisibleReviews(reviews.slice(0, newCount));
    setLoadedCount(newCount);
  };

 
  const toggleRating = () => {
    setNewReview((prev) => ({
      ...prev,
      rating: !prev.rating, 
    }));
  };

  
  const handleCreateReview = async () => {
    try {
      await apiClient.post("/api/customer/review", newReview);
      setNewReview({ ...newReview, reviewText: "" }); 

      
      const response = await apiClient.get(
        `/api/public/product/review/${productId}`
      );
      setReviews(response.data);
      setVisibleReviews(response.data.slice(0, loadedCount)); 
    } catch (error) {
      console.error("Помилка при створенні відгука:", error);
    }
  };

  return (
    <div className={styles.parent}>
      
      <div className={styles.sectionHeader}>
        <div className={styles.sectionName}>Відгуки</div>
        </div>
        <div className={styles.reviewFrame}>
          
          <div className={styles.ratingToggle} onClick={toggleRating}>
            <img
              src={newReview.rating ? "/Like.svg" : "/Dislike.svg"}
              alt={newReview.rating ? "Позитивний" : "Негативний"}
              className={styles.ratingIcon}
            />
          </div>

          
          <input
            type="text"
            value={newReview.reviewText}
            placeholder="Напишіть свій відгук"
            onChange={(e) =>
              setNewReview({ ...newReview, reviewText: e.target.value })
            }
            className={styles.reviewInput}
          />

          
          <div className={styles.reviewButtonSection}>
          <button onClick={handleCreateReview} className={styles.reviewButton}>
           <div className={styles.reviewButtonText}> Залишити відгук </div>
          </button>
          </div>
        </div>
      

     
      <div className={styles.reviewContainer}>
        {visibleReviews.map((review, index) => (
          <div className={styles.reviewFrame} key={index}>
            <div className={styles.reviewFrameItems}>
              <div className={styles.reviewTest1}>
                <div className={styles.reviewTest2}>
                  <div className={styles.reviewTest3}>
                    <div className={styles.reviewTest4}>
                      <div className={styles.username}>{review.username}</div>
                    </div>
                    <img
                      className={styles.boldLikeLike}
                      alt="like/dislike"
                      src={
                        review.reviewDTO.rating ? "/Like.svg" : "/Dislike.svg"
                      }
                    />
                  </div>
                  <div className={styles.reviewDate}>
                  </div>
                </div>
              </div>
              <div className={styles.reviewDescriptionFrame}>
                <p className={styles.reviewDescriptionText}>
                  {review.reviewDTO.reviewText}
                </p>
              </div>
            </div>
          </div>
        ))}
      </div>

      
      {visibleReviews.length < reviews.length && (
        <div className={styles.loadMoreButton} onClick={loadMoreReviews}>
          Завантажити ще
        </div>
      )}
    </div>
  );
}

export default ReviewsComponent;
