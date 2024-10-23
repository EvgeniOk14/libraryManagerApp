package com.example.beckendlibrarymeneger.controllers;

import com.example.beckendlibrarymeneger.customExcepptions.UserNotSaveException;
import com.example.beckendlibrarymeneger.models.User; // Импорт модели пользователя
import com.example.beckendlibrarymeneger.service.UserService; // Импорт сервиса для работы с пользователями
import jakarta.validation.Valid; // Импорт аннотации для валидации объектов
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity; // Импорт класса для формирования HTTP-ответов
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*; // Импорт аннотаций для работы с контроллерами
import java.util.ArrayList; // импорт списка ArrayList
import java.util.Optional; // Импорт класса для работы с возможностью отсутствия значения

/**
 * Контроллер для управления пользователями в библиотечной системе.
 * Обрабатывает HTTP-запросы для регистрации пользователей и получения информации о них.
 */
@RestController // аннотация указывает, что класс является контроллером REST
@CrossOrigin(origins = "http://localhost:5173") // разрешает CORS для указанного источника
@RequestMapping("/users") // определяет базовый путь для всех методов в этом контроллере
public class UserController
{
    //region Fields
    private final UserService userService; // сервис для работы с пользователями
    //endRegion

    //region Constructor
    /**
     * Конструктор контроллера, который использует UserService для работы с пользователями.
     *
     * @param userService сервис для обработки операций с пользователями.
     */
    public UserController(UserService userService)
    {
        this.userService = userService; // инициализация сервиса
    }
    //endRegion

    /**
     * Метод registerUser - Регистрация нового пользователя.
     * Обрабатывает HTTP POST запросы по пути "/users/register".
     *
     * @param user объект пользователя, переданный в теле запроса, который должен быть валидным.
     * @return ResponseEntity либо об успешном сохранении пользователя, либо о том что нужно ввести другой email.
     */
    @Transactional(rollbackFor = Exception.class) // Указывает, что метод должен выполняться в транзакции; при возникновении исключений транзакция будет откатана
    @CrossOrigin(origins = "http://localhost:5173") // разрешает CORS для данного метода
    @PostMapping("/register") // аннотация для обработки POST-запроса по указанному пути
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user)
    {
        String contactInfo = user.getContactInfo(); // получаем контактную информацию у пользователя
        boolean isUnique = userService.checkContactInfo(contactInfo); // сравниваем контактную информацию на уникальность с другими пользователями

        if (isUnique) // если email пользователя уникальный, то:
        {
            try
            {
                user.setParticipantId(userService.generateISBN()); // устанавливаем пользователю сгенерированный номер
                user.setTransactions(new ArrayList<>()); // устанавливаем пользователю список транзакций
                User savedUser = userService.save(user); // сохранение пользователя через сервис
                return ResponseEntity.status(HttpStatus.OK).body("Пользователь успешно сохранён в базе данных! " + savedUser); // возврат успешного ответа с зарегистрированным пользователем
            }
            catch (UserNotSaveException ex)
            {
                throw ex; // позволяем обработать исключение UserNotSaveException на глобальном уровне
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Такая электронная почта: " + contactInfo + " email уже существует! Введите другой email!");

        }
    }

    /**
     * Метод getUserById - Получение информации о пользователе по его идентификатору.
     * Обрабатывает HTTP GET запросы по пути "/users/{id}".
     *
     * @param id идентификатор пользователя, который требуется получить.
     * @return ResponseEntity с найденным пользователем и статусом 200 OK,
     * или статус 404 NOT FOUND, если пользователь не найден.
     */
    @CrossOrigin(origins = "http://localhost:5173") // разрешает CORS для данного метода
    @GetMapping("/{id}") // аннотация для обработки GET-запроса по указанному пути
    public ResponseEntity<User> getUserById(@PathVariable Integer id)
    {
        Optional<User> user = Optional.ofNullable(userService.getUserById(id)); // получение пользователя по идентификатору
        return user.map(ResponseEntity::ok) // если пользователь найден, возвращаем его
                .orElseGet(() -> ResponseEntity.notFound().build()); // если не найден, возвращаем статус 404
    }
}
