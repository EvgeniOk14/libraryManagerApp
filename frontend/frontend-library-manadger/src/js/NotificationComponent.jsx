import React, { useState, useEffect } from 'react';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import axios from 'axios';

const NotificationComponent = () => {
    const [errorMessage, setErrorMessage] = useState(''); // Состояние для хранения ошибок
    const [stompClient, setStompClient] = useState(null); // Состояние для stompClient

    useEffect(() => {
        // Подключение через STOMP и SockJS
        const socket = new SockJS('http://localhost:8080/notifications');
        const client = Stomp.over(socket);

        client.connect({}, (frame) => {
            console.log('Connected: ' + frame);
            setStompClient(client); // Сохранение клиента в состоянии

            // Подписка на уведомления
            client.subscribe('/topic/notifications', (notification) => {
                console.log('Получено уведомление: ', notification.body);
                alert("Получено уведомление: " + notification.body);
            });

            // Вызов функции для получения ошибок
            fetchErrors();

        }, (error) => {
            console.error('STOMP error: ', error);
            setErrorMessage('Ошибка подключения к серверу уведомлений');
        });

        // Отключение при размонтировании компонента
        return () => {
            if (client && client.connected) {
                client.disconnect(() => {
                    console.log('Disconnected');
                });
            }
        };
    }, []);

    // Функция для получения ошибок через REST API
    const fetchErrors = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/errors');
            if (response.data) {
                setErrorMessage(response.data);
            }
        } catch (error) {
            console.error('Ошибка при получении ошибок:', error);
            setErrorMessage('Не удалось получить ошибки с сервера');
        }
    };

    return (
        <div>
            <h2>Выберите действие из меню:</h2>
            {errorMessage && <div className="error-message">{errorMessage}</div>}
        </div>
    );
};

export default NotificationComponent;

// import React, { useState, useEffect } from 'react';
// import SockJS from 'sockjs-client';
// import { Stomp } from '@stomp/stompjs';
// import axios from 'axios';
//
// const NotificationComponent = () => {
//     const [errorMessage, setErrorMessage] = useState(''); // Состояние для хранения ошибок
//     const [stompClient, setStompClient] = useState(null); // Добавлено состояние для stompClient
//
//     useEffect(() => {
//         // Подключение через STOMP и SockJS
//         const socket = new SockJS('http://localhost:8080/ws');
//         const client = Stomp.over(socket);
//
//         client.connect({}, (frame) => {
//             console.log('Connected: ' + frame);
//             setStompClient(client); // Сохранение клиента в состоянии
//
//             // Подписка на уведомления
//             client.subscribe('/topic/notifications', (notification) => {
//                 console.log('Получено уведомление: ', notification.body);
//                 alert("Получено уведомление: " + notification.body);
//             });
//
//             fetchErrors(); // Вызов функции для получения ошибок при подключении
//
//         }, (error) => {
//             console.error('STOMP error: ', error);
//             setErrorMessage('Ошибка подключения к серверу уведомлений');
//         });
//
//         // Отключение при размонтировании компонента
//         return () => {
//             if (client) {
//                 client.disconnect(() => {
//                     console.log('Disconnected');
//                 });
//             }
//         };
//     }, []);
//
//     // Функция для получения ошибок через REST API
//     const fetchErrors = async () => {
//         try {
//             const response = await axios.get('http://localhost:8080/api/errors');
//             if (response.data) {
//                 setErrorMessage(response.data);
//             }
//         } catch (error) {
//             console.error('Ошибка при получении ошибок:', error);
//             setErrorMessage('Не удалось получить ошибки с сервера');
//         }
//     };
//
//     return (
//         <div>
//             <h2>Выберите действие из меню:</h2>
//             {errorMessage && <div className="error-message">{errorMessage}</div>}
//         </div>
//     );
// };
//
// export default NotificationComponent;
//
