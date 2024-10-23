package com.example.beckendlibrarymeneger.controllers;

import com.example.beckendlibrarymeneger.customExcepptions.ItemNotFoundException;
import com.example.beckendlibrarymeneger.customExcepptions.ItemNotReturnedException;
import com.example.beckendlibrarymeneger.customExcepptions.TransactionNotFoundException;
import com.example.beckendlibrarymeneger.customExcepptions.UserNotFoundException;
import com.example.beckendlibrarymeneger.interfaces.AbstractRepository;
import com.example.beckendlibrarymeneger.interfaces.TransactionRepository;
import com.example.beckendlibrarymeneger.models.*;
import com.example.beckendlibrarymeneger.service.*;
import com.example.beckendlibrarymeneger.socketManager.NotificationWebSocket;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Класс ItemController - обрабатывает методы работы с объектом класса LibraryItem:
 *  borrowItem - заём предмета
 *  returnItem - возврат предмета
 */
@RestController // указывает, что данный класс является контроллером
@RequestMapping("/library/items") // указываем путь по которому будут доступны все методы этого контроллера
@CrossOrigin(origins = "http://localhost:5173")  // разрешаем запросы с фронтенда
public class ItemController
{
    //region Field
    private final AbstractItemService abstractItemService; // сервис для работы с любыми предметами
    private final TransactionService transactionService; // сервис для работы с транзакциями
    private final UserService userService; // сервис для работы с пользователями
    private final AbstractRepository abstractRepository; // репозиторий для работы с предметами
    private final TransactionRepository transactionRepository; // репозиторий для работы с транзакциями
    private final BorrowService borrowService; // сервис для заёма предметов из библиотеки
    private final GsonService gsonService;  // сервис для работы с Gson
    private final EmailService emailService; // сервис для уведомления пользователей по email
    //endRegion

    //region Constructor
    public ItemController(AbstractItemService abstractItemService, TransactionService transactionService,
                          UserService userService, AbstractRepository abstractRepository,
                          TransactionRepository transactionRepository,
                          BorrowService borrowService, GsonService gsonService,
                          EmailService emailService)
    {
        this.abstractItemService = abstractItemService;
        this.transactionService = transactionService;
        this.userService = userService;
        this.abstractRepository = abstractRepository;
        this.transactionRepository = transactionRepository;
        this.borrowService = borrowService;
        this.gsonService = gsonService;
        this.emailService = emailService;
    }
    //endRegion

    /**
     * Метод borrowItem - обрабатывает запрос на заём предмета.
     *
     * @param json строка в формате JSON, содержащая информацию о транзакции и пользователе.
     * @return ResponseEntity<String> объект, представляющий результат выполнения операции.
     * Возвращает:
     * - 200 OK с информацией о сохранённой транзакции, если операция прошла успешно.
     * - 400 BAD REQUEST, если произошла ошибка синтаксиса JSON.
     * - 404 NOT FOUND, если пользователь или предмет не найдены.
     * - 500 INTERNAL SERVER ERROR, если произошла другая ошибка во время обработки запроса.
     */
    @Transactional(rollbackFor = Exception.class)  // указывает, что метод должен выполняться в транзакции; при возникновении исключений транзакция будет откатана
    @PostMapping("/borrow")
    public ResponseEntity<String> borrowItem(@RequestBody String json)
    {
        try
        {
            JsonObject jsonObject = gsonService.parseJson(json); // вызов сервиса GsonService для десериализации строки в JsonObject

            String isbn = jsonObject.get("isbn").getAsString(); // получаем идентификатор предмета ISBN
            String contactInfo = jsonObject.get("contactInfo").getAsString(); // получаем электронную почту пользователя
            Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.get("dueDate").getAsString()); // получаем дату возврата предмета
            String type = jsonObject.get("type").getAsString(); // получаем тип предмета type

            Transaction existingTransaction = transactionService.findTransactionByItemIsbn(isbn); // получаем транзакцию по isbn - идентификатору предмета

            if (existingTransaction != null) // если транзакция найдена, то:
            {
                if (!existingTransaction.getIs_returned()) // если у транзакции статус is_returned равен false (книга не возвращена в библиотеку), то:
                {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Выбранный предмет находится на руках у другого пользователя."); // уведомляем пользователя
                }

                User user = userService.getUserByContactInfo(contactInfo) ; // получаем пользователя через userService и userRepository

                if (user == null) // если пользователь равен null, то:
                {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Вы ввели некорректный email пользователя, либо зарегистрируйтесь в системе и повторите ввод!"); // уведомляем пользователя что он ввёл некорректный email, или не зарегистрировался в системе
                }

                // устанавливаем поля в транзакцию:
                existingTransaction.setIs_returned(false);  // устанавливаем статус как "взята" (false)
                existingTransaction.setDueDate(dueDate);   // устанавливаем дату возврата
                existingTransaction.setBorrowDate(new Date());  // устанавливаем дату заёма
                existingTransaction.setUser(user);  // связываем с пользователем

                // Сохраняем обновленную транзакцию:
                Transaction updatedTransaction = transactionService.saveTransaction(existingTransaction); // сохраняем транзакцию в базе данных

                borrowService.getItem(isbn, type, updatedTransaction); // устанавливаем предмету новую транзакцию

                // Отправка уведомления через WebSocket
                NotificationWebSocket.sendNotification("Предмет успешно взят в аренду: " + isbn + ". Пользователь: " + contactInfo); // отправка сообщения через web-Socket

                return ResponseEntity.status(HttpStatus.OK).body("Предмет: " + type + updatedTransaction.getItem() + " успешно получен срок: " + updatedTransaction.getDueDate() + " приятного пользования!"); // возвращаем успешный результат о сохранённой транзакции
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Транзакция не найдена."); // выводим уведомление пользователю о том, что "Транзакция не найдена."
        }
        catch (JsonSyntaxException ex) // обработка ошибок при работе с Json
        {
            throw ex; // позволяем обработать исключение JsonSyntaxException глобально
        }
        catch (UserNotFoundException | ItemNotFoundException | ItemNotReturnedException  | TransactionNotFoundException ex) // обрабатываем перечисленные исключения
        {
            throw ex; // позволяем обработать соответствующие исключения глобально
        }
        catch (Exception e) // обрабатываем остальные ошибки
        {
            e.printStackTrace(); // для отладки
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка: " + e.getMessage()); // уведомляем об ошибке
        }
    }



    /**
     * Метод returnItem - обрабатывает запрос на возврат предмета.
     *
     * @param json строка в формате JSON, содержащая информацию о предмете и пользователе.
     * @return ResponseEntity<String> объект, представляющий результат выполнения операции.
     * Возвращает:
     * - 200 OK с информацией о возвращённом предмете, если операция прошла успешно.
     * - 400 BAD REQUEST, если произошла ошибка синтаксиса JSON.
     * - 404 NOT FOUND, если пользователь или предмет не найдены.
     * - 500 INTERNAL SERVER ERROR, если произошла другая ошибка во время обработки запроса.
     */
    @Transactional(rollbackFor = Exception.class) // Указывает, что метод должен выполняться в транзакции; при возникновении исключений транзакция будет откатана
    @PostMapping("/return")
    public ResponseEntity<String> returnItem(@RequestBody String json)
    {
        try
        {
            JsonObject jsonObject = gsonService.parseJson(json); // Вызов сервиса GsonService для десериализации строки в JsonObject

            String isbn = jsonObject.get("isbn").getAsString();  // получение из jsonObject номера предмета - isbn

            String contactInfo = jsonObject.get("contactInfo").getAsString(); // получение из jsonObject email пользователя

            String type = jsonObject.get("type").getAsString(); // получаем у объекта тип type

            User user = userService.getUserByContactInfo(contactInfo) ; // получаем пользователя через userService и userRepository по contactInfo

            if (user == null) // если пользователь равен null, то:
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Вы ввели некорректный email пользователя, либо зарегистрируйтесь в системе и повторите ввод!"); // уведомляем пользователя об ошибке
            }


            Transaction findTransaction = transactionService.findTransactionByItemIsbn(isbn); // получаем транзакцию по isbn предмета:


            if (findTransaction == null) // если транзакция не найдена, то:
            {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Транзакция по isbn: " + isbn + " не найдена!"); // посылаем уведомление об этом
            }

            String resultMessage = borrowService.returnItem(findTransaction); // используем borrowService и его метод returnItem, для возврата предмета и получения сообщений об успешном или не успешном возврате предмета

            if (resultMessage.equals("Предмет успешно возвращён!")) // если сообщение об успешном возврате, то:
            {
                NotificationWebSocket.sendNotification("Предмет успешно возвращен: " + isbn + ". Пользователь: " + contactInfo); // Отправка уведомления через WebSocket

                return ResponseEntity.status(HttpStatus.OK).body("Предмет: " + type + " успешно возвращён в срок! Благодарим Вас за своевременный возврат!"); // уведомляем пользователя об успешном возврате
            }
            else // если сообщение о неуспешном возврате, то:
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMessage); // уведомляем пользователя об этом
            }
        }
        catch (JsonSyntaxException ex) // обрабатываем работу с Json
        {
            throw ex; // позволяем обработать исключение JsonSyntaxException глобально
        }
        catch (UserNotFoundException | ItemNotFoundException | ItemNotReturnedException ex) //обрабатываем перечисленные исключения
        {
            throw ex; // позволяем обработать исключение UserNotFoundException | ItemNotFoundException | ItemNotReturnedException  глобально
        }
        catch (Exception e) // обрабатываем остальные ошибки
        {
            e.printStackTrace(); // для отладки
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка: " + e.getMessage()); // уведомляем об ошибке
        }
    }



    /**
     * Метод getAllItems - получает список всех предметов из системы.
     *
     * @return ResponseEntity, содержащий список предметов и статус HTTP 200 (OK).
     * Если предметы отсутствуют, возвращается пустой список.
     */
    @GetMapping("/all")
    public ResponseEntity<List<LibraryItem<?>>> getAllItems()
    {
        List<LibraryItem<?>> items = abstractItemService.getAllItems();
        return ResponseEntity.ok(items);
    }
}

