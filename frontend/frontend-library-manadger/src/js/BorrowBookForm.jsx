import axios from 'axios';
import React, { useState } from 'react';
import "../styles/global.css"; // Импортируем стили

const BorrowItemForm = () => {
    const [itemType, setItemType] = useState(''); // Состояние для выбора типа предмета
    const [isbn, setIsbn] = useState(''); // Для книг и других типов предметов
    const [dueDate, setDueDate] = useState(''); // Для даты возврата
    const [contactInfo, setContactInfo] = useState(''); // Для контактной информации
    const [message, setMessage] = useState(''); // Сообщение о статусе

    const onSubmit = async (e) => {
        e.preventDefault();

        let itemData = {
            type: itemType,
            contactInfo,
            dueDate,
            isbn
        };

        // Преобразуем данные в JSON-строку
        const jsonData = JSON.stringify(itemData);

        // Выводим данные в консоль перед отправкой
        console.log("Отправляемые данные:", jsonData);

        try {
            const response = await axios.post('http://localhost:8080/library/items/borrow', jsonData, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            alert(response.data);
            console.log(response.data); // Обработка успешного ответа
            setMessage(response.data);
        } catch (error) {
            alert("ошибка: " + error.response.data);
            console.error('Ошибка при заимствовании предмета:', error);
            setMessage('Ошибка: ' + (error.response ? error.response.data : 'Произошла ошибка при возврате предмета.'));
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
                        className="form-select"  // Добавляем класс для select
                    >
                        <option value="">Выберите тип</option>
                        <option value="Book">Книга</option>
                        <option value="DVD">DVD</option>
                        <option value="Magazine">Журнал</option>
                        <option value="Ebook">Электронная книга</option>
                    </select>
                </label>

                {itemType && (
                    <div className="form-item-type">
                        <label>Тип: {itemType}</label>
                    </div>
                )}

                {/* Условная отрисовка полей формы */}
                {itemType === 'Book' && (
                    <input
                        type="text"
                        placeholder="ISBN книги"
                        value={isbn}
                        onChange={(e) => setIsbn(e.target.value)}
                        required
                        className="form-input"
                    />
                )}

                {itemType === 'DVD' && (
                    <input
                        type="text"
                        placeholder="ISBN DVD"
                        value={isbn}
                        onChange={(e) => setIsbn(e.target.value)}
                        required
                        className="form-input"
                    />
                )}

                {itemType === 'Magazine' && (
                    <input
                        type="text"
                        placeholder="ID Журнала"
                        value={isbn}
                        onChange={(e) => setIsbn(e.target.value)}
                        required
                        className="form-input"
                    />
                )}

                {itemType === 'Ebook' && (
                    <input
                        type="text"
                        placeholder="ID Электронной книги"
                        value={isbn}
                        onChange={(e) => setIsbn(e.target.value)}
                        required
                        className="form-input"
                    />
                )}

                {/* Поле для выбора даты возврата */}
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
            </form>

            {message && <p className="form-message">{message}</p>}
        </div>
    );
};

export default BorrowItemForm;

// import axios from 'axios';
// import React, { useState } from 'react';
//
// const BorrowItemForm = () => {
//     const [itemType, setItemType] = useState(''); // Состояние для выбора типа предмета
//     const [isbn, setIsbn] = useState(''); // Для книг
//     const [dueDate, setDueDate] = useState('');
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
//         try
//         {
//             const response = await axios.post('http://localhost:8080/library/items/borrow', jsonData, {
//                 headers: {
//                     'Content-Type': 'application/json',
//                 },
//             });
//             console.log(response.data); // Обработка успешного ответа
//             setMessage(response.data);
//         }
//         catch (error)
//         {
//             console.error('Ошибка при заимствовании предмета:', error);
//             setMessage('Ошибка: ' + (error.response ? error.response.data : 'Произошла ошибка при возврате предмета.')); // Вывод ошибки, полученной от сервера
//             alert('Ошибка: ' + error.response.data); // Вывод ошибки, полученной от сервера
//         }
//     };
//
//     return (
//         <div>
//             <form onSubmit={onSubmit}>
//                 {/* Выпадающий список для выбора типа предмета */}
//                 <label>
//                     Тип предмета:
//                     <select value={itemType} onChange={(e) => setItemType(e.target.value)} required>
//                         <option value="">Выберите тип</option>
//                         <option value="Book">Книга</option>
//                         <option value="DVD">DVD</option>
//                         <option value="Magazine">Журнал</option>
//                         <option value="Ebook">Электронная книга</option>
//                     </select>
//                 </label>
//
//                 {/* Поле типа предмета, отображаемое только после выбора и не изменяемое пользователем */}
//                 {itemType && (
//                     <div>
//                         <label>Тип: {itemType}</label>
//                     </div>
//                 )}
//
//                 {/* Условная отрисовка полей формы в зависимости от типа предмета */}
//                 {itemType === 'Book' && (
//                     <>
//                         <input
//                             type="text"
//                             placeholder="ISBN книги"
//                             value={isbn}
//                             onChange={(e) => setIsbn(e.target.value)}
//                             required
//                         />
//                     </>
//                 )}
//
//                 {itemType === 'DVD' && (
//                     <>
//                         <input
//                             type="text"
//                             placeholder="ISBN DVD"
//                             value={isbn}
//                             onChange={(e) => setIsbn(e.target.value)}
//                             required
//                         />
//                     </>
//                 )}
//
//                 {itemType === 'Magazine' && (
//                     <>
//                         <input
//                             type="text"
//                             placeholder="ID Журнала"
//                             value={isbn}
//                             onChange={(e) => setIsbn(e.target.value)}
//                             required
//                         />
//                     </>
//                 )}
//
//                 {itemType === 'Ebook' && (
//                     <>
//                         <input
//                             type="text"
//                             placeholder="ID Электронной книги"
//                             value={isbn}
//                             onChange={(e) => setIsbn(e.target.value)}
//                             required
//                         />
//                     </>
//                 )}
//
//                 {/* Поле для выбора даты возврата */}
//                 <input
//                     type="date"
//                     value={dueDate}
//                     onChange={(e) => setDueDate(e.target.value)}
//                     required
//                 />
//
//                 <input
//                     type="text"
//                     placeholder="электронная почта пользователя"
//                     value={contactInfo}
//                     onChange={(e) => setContactInfo(e.target.value)}
//                     required
//                 />
//
//                 <button type="submit">Взять предмет</button>
//             </form>
//             {message && <p>{message}</p>} {/* Сообщение о статусе */}
//         </div>
//     );
// };
//
// export default BorrowItemForm;
//
