import axios from 'axios';
import React, { useState, useEffect } from 'react';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import "../styles/global.css"; // Импортируем стили

const BorrowItemForm = () => {
    const [itemType, setItemType] = useState(''); // Состояние для выбора типа предмета
    const [isbn, setIsbn] = useState(''); // Для книг и других типов предметов
    const [dueDate, setDueDate] = useState(''); // Для даты возврата
    const [contactInfo, setContactInfo] = useState(''); // Для контактной информации
    const [message, setMessage] = useState(''); // Сообщение о статусе
    const [notification, setNotification] = useState(''); // Для WebSocket уведомлений
    const [stompClient, setStompClient] = useState(null); // Состояние для stompClient

    useEffect(() => {
        // Подключение через STOMP и SockJS
        //const socket = new SockJS('http://localhost:8080/notifications');
        const socket = new WebSocket('ws://localhost:8080/notifications');
        const client = Stomp.over(() => new WebSocket('ws://localhost:8080/notifications'));
        //const client = Stomp.over(socket);

        client.connect({}, (frame) => {
            console.log('Connected: ' + frame);
            setStompClient(client); // Сохраняем stompClient в состоянии

            // Подписываемся на уведомления о заимствовании предметов
            client.subscribe('/topic/borrow-notifications', (notification) => {
                console.log('Получено уведомление: ', notification.body);
                setNotification(notification.body); // Устанавливаем уведомление в состоянии
            });

        }, (error) => {
            console.error('Ошибка подключения к WebSocket: ', error);
        });

        // Отключение WebSocket при размонтировании компонента
        return () => {
            if (client) {
                client.disconnect(() => {
                    console.log('Disconnected');
                });
            }
        };
    }, []);

    const onSubmit = async (e) => {
        e.preventDefault();

        let itemData = {
            type: itemType,
            contactInfo,
            dueDate,
            isbn
        };

        const jsonData = JSON.stringify(itemData);

        try {
            const response = await axios.post('http://localhost:8080/library/items/borrow', jsonData, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            alert(response.data);
            setMessage(response.data);
        } catch (error) {
            alert("Ошибка: " + (error.response?.data || 'Произошла ошибка.'));
            setMessage('Ошибка: ' + (error.response ? error.response.data : 'Произошла ошибка при заимствовании предмета.'));
        }
    };

    return (
        <div className="form-container"> {/* Контейнер для формы */}
            <form onSubmit={onSubmit} className="borrow-form"> {/* Добавляем класс для формы */}
                <label className="form-label"> {/* Добавляем класс для лейблов */}
                    Тип предмета:
                    <select
                        value={itemType}
                        onChange={(e) => setItemType(e.target.value)}
                        required
                        className="form-select"
                    >
                        <option value="">Выберите тип</option>
                        <option value="Book">Книга</option>
                        <option value="DVD">DVD</option>
                        <option value="Magazine">Журнал</option>
                        <option value="Ebook">Электронная книга</option>
                    </select>
                </label>

                {/* Условная отрисовка полей формы */}
                {itemType && (
                    <>
                        <input
                            type="text"
                            placeholder={itemType === 'Book' ? "ISBN книги" : itemType === 'DVD' ? "ISBN DVD" : itemType === 'Magazine' ? "ID Журнала" : "ID Электронной книги"}
                            value={isbn}
                            onChange={(e) => setIsbn(e.target.value)}
                            required
                            className="form-input"
                        />
                        <input
                            type="date"
                            value={dueDate}
                            onChange={(e) => setDueDate(e.target.value)}
                            required
                            className="form-input"
                        />
                        <input
                            type="text"
                            placeholder="Электронная почта пользователя"
                            value={contactInfo}
                            onChange={(e) => setContactInfo(e.target.value)}
                            required
                            className="form-input"
                        />
                        <button type="submit" className="form-button">
                            Взять предмет
                        </button>
                    </>
                )}
            </form>

            {/* Отображаем сообщение и уведомления */}
            {message && <p className="form-message">{message}</p>}
            {notification && <p className="form-notification">Уведомление: {notification}</p>}
        </div>
    );
};

export default BorrowItemForm;













// import axios from 'axios';
// import React, { useState } from 'react';
// import "../styles/global.css"; // Импортируем стили
//
// const BorrowItemForm = () => {
//     const [itemType, setItemType] = useState(''); // Состояние для выбора типа предмета
//     const [isbn, setIsbn] = useState(''); // Для книг и других типов предметов
//     const [dueDate, setDueDate] = useState(''); // Для даты возврата
//     const [contactInfo, setContactInfo] = useState(''); // Для контактной информации
//     const [message, setMessage] = useState(''); // Сообщение о статусе
//
//     const onSubmit = async (e) => {
//         e.preventDefault();
//
//         let itemData = {
//             type: itemType,
//             contactInfo,
//             dueDate,
//             isbn
//         };
//
//         // Преобразуем данные в JSON-строку
//         const jsonData = JSON.stringify(itemData);
//
//         // Выводим данные в консоль перед отправкой
//         console.log("Отправляемые данные:", jsonData);
//
//         try {
//             const response = await axios.post('http://localhost:8080/library/items/borrow', jsonData, {
//                 headers: {
//                     'Content-Type': 'application/json',
//                 },
//             });
//             alert(response.data);
//             console.log(response.data); // Обработка успешного ответа
//             setMessage(response.data);
//         } catch (error) {
//             alert("ошибка: " + error.response.data);
//             console.error('Ошибка при заимствовании предмета:', error);
//             setMessage('Ошибка: ' + (error.response ? error.response.data : 'Произошла ошибка при возврате предмета.'));
//         }
//     };
//
//     return (
//         <div className="form-container"> {/* Контейнер для формы */}
//             <form onSubmit={onSubmit} className="borrow-form"> {/* Добавляем класс для формы */}
//                 <label className="form-label"> {/* Добавляем класс для лейблов */}
//                     Тип предмета:
//                     <select
//                         value={itemType}
//                         onChange={(e) => setItemType(e.target.value)}
//                         required
//                         className="form-select"  // Добавляем класс для select
//                     >
//                         <option value="">Выберите тип</option>
//                         <option value="Book">Книга</option>
//                         <option value="DVD">DVD</option>
//                         <option value="Magazine">Журнал</option>
//                         <option value="Ebook">Электронная книга</option>
//                     </select>
//                 </label>
//
//                 {itemType && (
//                     <div className="form-item-type">
//                         <label>Тип: {itemType}</label>
//                     </div>
//                 )}
//
//                 {/* Условная отрисовка полей формы */}
//                 {itemType === 'Book' && (
//                     <input
//                         type="text"
//                         placeholder="ISBN книги"
//                         value={isbn}
//                         onChange={(e) => setIsbn(e.target.value)}
//                         required
//                         className="form-input"
//                     />
//                 )}
//
//                 {itemType === 'DVD' && (
//                     <input
//                         type="text"
//                         placeholder="ISBN DVD"
//                         value={isbn}
//                         onChange={(e) => setIsbn(e.target.value)}
//                         required
//                         className="form-input"
//                     />
//                 )}
//
//                 {itemType === 'Magazine' && (
//                     <input
//                         type="text"
//                         placeholder="ID Журнала"
//                         value={isbn}
//                         onChange={(e) => setIsbn(e.target.value)}
//                         required
//                         className="form-input"
//                     />
//                 )}
//
//                 {itemType === 'Ebook' && (
//                     <input
//                         type="text"
//                         placeholder="ID Электронной книги"
//                         value={isbn}
//                         onChange={(e) => setIsbn(e.target.value)}
//                         required
//                         className="form-input"
//                     />
//                 )}
//
//                 {/* Поле для выбора даты возврата */}
//                 <input
//                     type="date"
//                     value={dueDate}
//                     onChange={(e) => setDueDate(e.target.value)}
//                     required
//                     className="form-input"
//                 />
//
//                 <input
//                     type="text"
//                     placeholder="Электронная почта пользователя"
//                     value={contactInfo}
//                     onChange={(e) => setContactInfo(e.target.value)}
//                     required
//                     className="form-input"
//                 />
//
//                 <button type="submit" className="form-button">
//                     Взять предмет
//                 </button>
//             </form>
//
//             {message && <p className="form-message">{message}</p>}
//         </div>
//     );
// };
//
// export default BorrowItemForm;
//
