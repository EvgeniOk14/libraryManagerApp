import './App.css';
import RegisterForm from "./js/RegisterForm .jsx";
import AddItemForm from './js/AddItemForm.jsx';
import BorrowBookForm from './js/BorrowBookForm.jsx';
import NotificationComponent from "./js/NotificationComponent.jsx";
import ReturnItemForm from "./js/ReturnItemForm.jsx";
import './styles/global.css'
import { BrowserRouter as Router, Route, Routes, Link, Navigate } from 'react-router-dom';

const App = () => {
    const token = localStorage.getItem('token');

    return (
        <Router>
            <div className="app-container">
                <h1 className="app-container__title">Система управление библиотекой:</h1>

                {/* Компонент уведомлений */}
                <NotificationComponent /> {/* Добавляем компонент уведомлений здесь */}

                {/* Навигационное меню для выбора между добавлением и заимствованием */}
                <nav className="app-container__nav">
                    <ul className="nav-list">
                        <li className="nav-list__item">
                            <Link to="/add-item" className="nav-list__link">Добавить предмет в библиотеку</Link>
                        </li>
                        <li className="nav-list__item">
                            <Link to="/borrow-book" className="nav-list__link">Взять предмет на прокат из библиотеки</Link>
                        </li>
                        <li className="nav-list__item">
                            <Link to="/register" className="nav-list__link">Регистрация пользователя в библиотеке</Link>
                        </li>
                        <li className="nav-list__item">
                            <Link to="/return-book" className="nav-list__link">Возврат предмета в библиотеку</Link>
                        </li>
                    </ul>
                </nav>

                <Routes>
                    {/* Перенаправление на страницу добавления элемента или регистрации в зависимости от токена */}
                    <Route path="/" element={token ? <Navigate to="/add-item" /> : <Navigate to="/register" />} />

                    {/* Маршрут для страницы регистрации */}
                    <Route path="/register" element={<RegisterForm />} />

                    {/* Маршрут для добавления элементов */}
                    <Route path="/add-item" element={<AddItemForm />} />

                    {/* Маршрут для взятия книги на прокат */}
                    <Route path="/borrow-book" element={<BorrowBookForm />} />

                    {/* Маршрут для возврата книги */}
                    <Route path="/return-book" element={<ReturnItemForm />} />

                </Routes>
            </div>
        </Router>
    );
};

export default App;

