import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import axios from 'axios';
import "../styles/global.css";

const AddItemForm = () => {
    const { register, handleSubmit, reset } = useForm();
    const [type, setType] = useState('');
    const [responseData, setResponseData] = useState(null);

    const onSubmit = async (data) => {
        const newItem = {
            type,
            ...data,
        };

        if (data.genres) {
            newItem.genres = data.genres.split(',').map(genre => ({ name: genre.trim() }));
        }

        try {
            alert("Создан объект: " + JSON.stringify(newItem));
            const response = await axios.post('http://localhost:8080/library/addItem', newItem);
            setResponseData(response.data);
            alert(`Полученный объект: ${JSON.stringify(response.data)}`);
        } catch (error) {
            console.error('Ошибка: ', error);
            alert('Ошибка при добавлении элемента: ' + error.response.data);
        }
    };

    const handleTypeChange = (e) => {
        setType(e.target.value);
        reset();
    };

    const handleReturn = () => {
        setResponseData(null);
    };

    return (
        <div className="add-item-form">
            {!responseData ? (
                <form className="add-item-form__form" onSubmit={handleSubmit(onSubmit)}>
                    <label className="add-item-form__label">
                        Тип:
                        <select className="add-item-form__select" value={type} onChange={handleTypeChange} required>
                            <option value="">Выберите тип</option>
                            <option value="Book">Книга</option>
                            <option value="DVD">DVD</option>
                            <option value="Magazine">Журнал</option>
                            <option value="Ebook">Электронная книга</option>
                        </select>
                    </label>

                    {type === 'Book' && (
                        <>
                            <input className="add-item-form__input" type="text" {...register('title')} placeholder="Название книги" required />
                            <input className="add-item-form__input" type="text" {...register('author')} placeholder="Автор" required />
                            <input className="add-item-form__input" type="date" {...register('publicationDate')} placeholder="Дата публикации" required />
                            <input className="add-item-form__input" type="text" {...register('genres')} placeholder="Жанры книги (через запятую)" required />
                        </>
                    )}

                    {type === 'DVD' && (
                        <>
                            <input className="add-item-form__input" {...register('title')} placeholder="Название DVD" required />
                            <input className="add-item-form__input" {...register('director')} placeholder="Режиссёр" required />
                            <input className="add-item-form__input" type="date" {...register('releaseDate')} placeholder="Дата выпуска" />
                        </>
                    )}

                    {type === 'Magazine' && (
                        <>
                            <input className="add-item-form__input" {...register('title')} placeholder="Название журнала" required />
                            <input className="add-item-form__input" {...register('publisher')} placeholder="Издатель" required />
                            <input className="add-item-form__input" type="date" {...register('releasePublication')} placeholder="Дата публикации" />
                        </>
                    )}

                    {type === 'Ebook' && (
                        <>
                            <input className="add-item-form__input" {...register('title')} placeholder="Название электронной книги" required />
                            <input className="add-item-form__input" {...register('eBookAuthor')} placeholder="Автор электронной книги" required />
                            <input className="add-item-form__input" type="date" {...register('eBookPublicationDate')} placeholder="Дата публикации" />
                        </>
                    )}

                    <button className="add-item-form__button" type="submit">Добавить</button>
                </form>
            ) : (
                <div className="add-item-form__response">
                    <h2>Полученный объект:</h2>
                    <pre>{JSON.stringify(responseData, null, 2)}</pre>
                    <button className="add-item-form__button" onClick={handleReturn}>Вернуться к добавлению вещей</button>
                </div>
            )}
        </div>
    );
};

export default AddItemForm;

// import React, { useState } from 'react';
// import { useForm } from 'react-hook-form';
// import axios from 'axios';
// import "../styles/style.css"
//
// const AddItemForm = () =>
// {
//     const { register, handleSubmit, reset } = useForm();
//     const [type, setType] = useState(''); // Состояние для выбора типа элемента
//     const [responseData, setResponseData] = useState(null); // Состояние для ответа
//
//     const onSubmit = async (data) => {
//         const newItem = {
//             type,
//             ...data, // Объединяем тип и данные формы
//         };
//
//         // Проверяем, есть ли жанры, и только в этом случае обрабатываем их
//         if (data.genres)
//         {
//             //newItem.genres = data.genres.split(',').map(genre => genre.trim()); // Преобразуем строку в массив
//             newItem.genres = data.genres.split(',').map(genre => ({name: genre.trim(),})); // Преобразуем каждую строку в объект с полем name
//         }
//
//         try
//         {
//             alert("Создан объект: " + JSON.stringify(newItem));
//             const response = await axios.post('http://localhost:8080/library/addItem', newItem);
//             setResponseData(response.data); // Сохраняем ответ в состоянии
//             alert(`Полученный объект: ${JSON.stringify(response.data)}`); // Отображаем объект в алёрте
//         }
//         catch (error)
//         {
//             console.error('Ошибка: ', error);
//             alert('Ошибка при добавлении элемента: ' + error.response.data); // сервер возвращает сообщение об ошибке
//         }
//     };
//
//
//     const handleTypeChange = (e) =>
//     {
//         setType(e.target.value); // Устанавливаем тип элемента
//         reset(); // Сбрасываем поля формы при смене типа
//     };
//
//     const handleReturn = () =>
//     {
//         setResponseData(null); // Сбрасываем состояние ответа
//     };
//
//     return (
//         <div>
//             {!responseData ? ( // Если ответа нет, показываем форму
//                 <form onSubmit={handleSubmit(onSubmit)}>
//                     {/* Выпадающий список для выбора типа */}
//                     <label>
//                         Тип:
//                         <select value={type} onChange={handleTypeChange} required>
//                             <option value="">Выберите тип</option>
//                             <option value="Book">Книга</option>
//                             <option value="DVD">DVD</option>
//                             <option value="Magazine">Журнал</option>
//                             <option value="Ebook">Электронная книга</option>
//                             {/* Добавляйте другие типы по необходимости */}
//                         </select>
//                     </label>
//
//                     {/* Условная отрисовка полей формы в зависимости от выбранного типа */}
//                     {type === 'Book' && (
//                         <>
//                             <input type="text" {...register('title')} placeholder="Название книги" required />
//                             <input type="text" {...register('author')} placeholder="Автор" required />
//                             <input type="date" {...register('publicationDate')} placeholder="Дата публикации" required />
//                             <input type="text" {...register('genres')} placeholder="Жанры книги (через запятую)" required />
//                         </>
//                     )}
//
//                     {type === 'DVD' && (
//                         <>
//                             <input {...register('title')} placeholder="Название DVD" required />
//                             <input {...register('director')} placeholder="Режиссёр" required />
//                             <input type="date" {...register('releaseDate')} placeholder="Дата выпуска" />
//                         </>
//                     )}
//
//                     {type === 'Magazine' && (
//                         <>
//                             <input {...register('title')} placeholder="Название журнала" required />
//                             <input {...register('publisher')} placeholder="Издатель" required />
//                             <input type="date" {...register('releasePublication')} placeholder="Дата публикации" />
//                         </>
//                     )}
//
//                     {type === 'Ebook' && (
//                         <>
//                             <input {...register('title')} placeholder="Название электронной книги" required />
//                             <input {...register('eBookAuthor')} placeholder="Автор элетронной книги" required />
//                             <input type="date" {...register('eBookPublicationDate')} placeholder="Дата публикации" />
//                         </>
//                     )}
//
//                     <button type="submit">Добавить</button>
//                 </form>
//             ) : (
//                 <div>
//                     <h2>Полученный объект:</h2>
//                     <pre>{JSON.stringify(responseData, null, 2)}</pre> {/* Отображаем ответ в формате JSON */}
//                     <button onClick={handleReturn}>Вернуться к добавлению вещей</button> {/* Кнопка для возврата */}
//                 </div>
//             )}
//         </div>
//     );
// };
//
// export default AddItemForm;
//
