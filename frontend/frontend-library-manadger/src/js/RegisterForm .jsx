import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../styles/RegisterForm.css';

const RegisterForm = () => {
    const [name, setName] = useState('');
    const [contactInfo, setContactInfo] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        const userData = {
            name,
            contactInfo,
        };

        try
        {
            const response = await axios.post('http://localhost:8080/users/register', userData);
            alert(`Успешно зарегистрировано: ${JSON.stringify(response.data)}`);
            setMessage(`Успешно зарегистрировано: ${JSON.stringify(response.data)}`);
            navigate('/add-item');
        }
        catch (error)
        {
            alert("ошибка: " + error.response.data);
            console.error('Ошибка при регистрации: ', error);
            setMessage('Ошибка при регистрации');
        }
    };

    return (
        <div className="register-form">
            <h2 className="register-form__title">Регистрация пользователя</h2>
            <form className="register-form__form" onSubmit={handleSubmit}>
                <input
                    className="register-form__input"
                    type="text"
                    placeholder="Имя"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />
                <input
                    className="register-form__input"
                    type="email"
                    placeholder="Контактная информация (email)"
                    value={contactInfo}
                    onChange={(e) => setContactInfo(e.target.value)}
                    required
                />
                <button className="register-form__button" type="submit">Зарегистрироваться</button>
            </form>
            {message && <p className="register-form__message">{message}</p>}
        </div>
    );
};

export default RegisterForm;




// import React, { useState } from 'react';
// import axios from 'axios';
// import { useNavigate } from 'react-router-dom';
// import '../styles/RegisterForm.css'
//
// const RegisterForm = () => {
//     const [name, setName] = useState('');
//     const [contactInfo, setContactInfo] = useState('');
//     //const [participantId, setParticipantId] = useState('');
//     const [message, setMessage] = useState('');
//     const navigate = useNavigate();
//
//     const handleSubmit = async (e) => {
//         e.preventDefault();
//         const userData = {
//             name,
//             contactInfo,
//             //participantId,
//         };
//
//         try
//         {
//             const response = await axios.post('http://localhost:8080/users/register', userData);
//             setMessage(`Успешно зарегистрировано: ${JSON.stringify(response.data)}`);
//             navigate('/add-item');
//         }
//         catch (error)
//         {
//             console.error('Ошибка при регистрации: ', error);
//             setMessage('Ошибка при регистрации');
//         }
//     };
//
//     return (
//         <div>
//             <h2>Регистрация пользователя</h2>
//             <form onSubmit={handleSubmit}>
//                 <input
//                     type="text"
//                     placeholder="Имя"
//                     value={name}
//                     onChange={(e) => setName(e.target.value)}
//                     required
//                 />
//                 <input
//                     type="email"
//                     placeholder="Контактная информация (email)"
//                     value={contactInfo}
//                     onChange={(e) => setContactInfo(e.target.value)}
//                     required
//                 />
//
//                 <button type="submit">Зарегистрироваться</button>
//             </form>
//             {message && <p>{message}</p>}
//         </div>
//     );
// };
//
// export default RegisterForm;
