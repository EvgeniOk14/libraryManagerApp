import axios from 'axios';
import React, { useState } from 'react';
import '../styles/global.css'; // Импортируем файл стилей

const ReturnItemForm = () => {
    const [itemType, setItemType] = useState(''); // Состояние для выбора типа предмета
    const [isbn, setIsbn] = useState(''); // Для книг
    const [message, setMessage] = useState(''); // Сообщение о статусе
    const [contactInfo, setContactInfo] = useState('');

    const onSubmit = async (e) => {
        e.preventDefault();

        let itemData = {
            type: itemType,
            contactInfo,
            isbn,
        };

        const jsonData = JSON.stringify(itemData);
        console.log("Отправляемые данные:", jsonData);

        try {
            const response = await axios.post('http://localhost:8080/library/items/return', jsonData, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            alert(response.data);
            console.log(response.data);
            setMessage(response.data);
        } catch (error) {
            console.error('Ошибка при возврате предмета:', error);
            setMessage('Ошибка: ' + (error.response ? error.response.data : 'Произошла ошибка при возврате предмета.'));
            alert('Ошибка: ' + error.response.data);
        }
    };

    return (
        <div className="form-container">
            <h2>Возврат предмета</h2>
            <form className="borrow-form" onSubmit={onSubmit}>
                <label className="form-label">
                    Тип предмета:
                    <select
                        className="form-select"
                        value={itemType}
                        onChange={(e) => setItemType(e.target.value)}
                        required>
                        <option value="">Выберите тип</option>
                        <option value="Book">Книга</option>
                        <option value="DVD">DVD</option>
                        <option value="Magazine">Журнал</option>
                        <option value="Ebook">Электронная книга</option>
                    </select>
                </label>

                {itemType && (
                    <div>
                        <label className="form-label">Тип: {itemType}</label>
                    </div>
                )}

                {itemType && (
                    <input
                        className="form-input"
                        type="text"
                        placeholder={`ISBN ${itemType === 'Ebook' ? 'Электронной книги' : itemType}`}
                        value={isbn}
                        onChange={(e) => setIsbn(e.target.value)}
                        required
                    />
                )}

                <input
                    className="form-input"
                    type="text"
                    placeholder="email пользователя"
                    value={contactInfo}
                    onChange={(e) => setContactInfo(e.target.value)}
                    required
                />

                <button className="form-button" type="submit">Вернуть предмет</button>
            </form>
            {message && <p className="form-message">{message}</p>}
        </div>
    );
};

export default ReturnItemForm;

