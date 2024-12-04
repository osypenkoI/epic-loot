import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'process.env.REACT_APP_BACKEND_URL',
    withCredentials: true, // Включаем куки
});

// Добавляем интерсептор для проверки истечения токена
apiClient.interceptors.response.use(
    (response) => {
        console.log("Успішний запит:", response.config.url, response.data);
        return response;
    },
    async (error) => {
        console.error("Помилка запиту:", error.response?.status, error.message);
        const originalRequest = error.config;

        if (error.response?.status === 403 && !originalRequest.__isRetry) {
            originalRequest.__isRetry = true;
            console.log("Починаємо оновлення токена...");

            try {
                const refreshResponse = await axios.post(
                    'process.env.REACT_APP_BACKEND_URL/api/auth/refresh',
                    {},
                    { withCredentials: true }
                );

                console.log("Токен оновлено:", refreshResponse.data);

                return apiClient(originalRequest); // Повторний запит
            } catch (refreshError) {
                console.error("Помилка при оновленні токена:", refreshError);
                return Promise.reject(refreshError);
            }
        }

        return Promise.reject(error);
    }
);

export default apiClient;
