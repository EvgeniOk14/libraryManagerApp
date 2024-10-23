package com.example.beckendlibrarymeneger.service;

import com.example.beckendlibrarymeneger.customExcepptions.UserNotFoundException; // Импорт исключения, если пользователь не найден
import com.example.beckendlibrarymeneger.interfaces.UserRepository; // Импорт интерфейса репозитория пользователей
import com.example.beckendlibrarymeneger.models.User; // Импорт модели пользователя
import org.springframework.stereotype.Service; // Импорт аннотации для определения сервиса в Spring
import java.util.UUID;

/**
 * Класс UserService отвечает за бизнес-логику пользователей в системе.
 */
@Service // обозначает класс как сервисный компонент в Spring
public class UserService
{
    //region Fields
    private final UserRepository userRepository; // репозиторий для работы с пользователями
    //endRegion

    //region Constructor
    /**
     * Конструктор для инициализации UserService с заданным репозиторием.
     *
     * @param userRepository репозиторий для пользователей
     */
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository; // инициализация репозитория пользователей
    }
    //endRegion

    /**
     * Метод для сохранения пользователя в системе.
     *
     * @param user пользователь, который нужно сохранить
     * @return сохраненный пользователь
     */
    public User save(User user)
    {
        return userRepository.save(user); // сохраняем пользователя в репозитории
    }

    /**
     * Метод для получения пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return найденный пользователь
     * @throws RuntimeException если пользователь не найден
     */
    public User getUserById(Integer id)
    {
        // ищем пользователя по ID
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id)); // выбрасываем исключение, если не найден
    }


    /**
     * Метод генерирует случайный номер UUID для книги.
     *
     * @return возвращает случайный UUID как строку
     */
    public String generateISBN()
    {
        return UUID.randomUUID().toString(); // генерируем случайный UUID и возвращаем его в виде строки
    }


    /**
     * Метод getUserByContactInfo - получения пользователя по его контактной информации.
     *
     * @param contactInfo контактная информация пользователя (например, номер телефона или email)
     * @return возвращает объект User, соответствующий переданной контактной информации
     * @throws UserNotFoundException если пользователь с указанной контактной информацией не найден
     */
    public User getUserByContactInfo(String contactInfo)
    {
        try
        {
            User user = userRepository.findByContactInfo(contactInfo); // находим пользователя в базе данных по контактной информации
            return user; // возвращаем найденного пользователя
        }
        catch (UserNotFoundException ex) // ловим исключение UserNotFoundException, если пользователь не найден
        {
            throw ex; // позволяем обработать исключение UserNotFoundException на глобальном уровне
        }
    }

    /**
     * Метод для проверки наличия пользователя с указанной контактной информацией в базе данных.
     *
     * Метод использует репозиторий для оптимизированной проверки, существует ли пользователь с заданной контактной информацией.
     * Если пользователь с таким контактным данным найден, метод вернет false, иначе вернет true.
     *
     * @param contactInfo контактная информация (например, email), которую необходимо проверить
     * @return true, если пользователя с такой контактной информацией нет в базе, иначе false
     * @throws RuntimeException если произошла ошибка при выполнении проверки
     */
    public boolean checkContactInfo(String contactInfo)
    {
        try
        {
            return !userRepository.existsByContactInfo(contactInfo); // оптимизированная проверка существования пользователя с такой контактной информацией
        }
        catch (Exception ex)  // обработка исключения
        {
            throw new RuntimeException("Ошибка при проверке контактной информации", ex); // обработка исключения
        }
    }

}