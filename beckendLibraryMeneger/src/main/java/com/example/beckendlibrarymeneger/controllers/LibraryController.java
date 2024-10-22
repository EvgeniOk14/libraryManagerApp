package com.example.beckendlibrarymeneger.controllers;

import com.example.beckendlibrarymeneger.customExcepptions.*;
import com.example.beckendlibrarymeneger.interfaces.*;
import com.example.beckendlibrarymeneger.models.*;
import com.example.beckendlibrarymeneger.service.AbstractItemService;
import com.example.beckendlibrarymeneger.service.BookService;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Класс LibraryController предоставляет API для работы с элементами библиотеки и транзакциями.
 */
@RestController
@RequestMapping("/library")
public class LibraryController
{
    //region Fields
    @Autowired
    private TransactionRepository transactionRepository; // репозиторий для работы с транзакциями

    @Autowired
    private AbstractRepository abstractRepository; // репозиторий для сохранения элементов библиотеки

    @Autowired
    private BookService bookService; // сервис для работы с книгами (генерация ISBN и другие функции)

    @Autowired
    private AbstractItemService abstractItemService; // сервис для работы с вещами (генерация ISBN и другие функции)

    @Autowired
    private GenreRepository genreRepository; // репозиторий для работы с Genre (жанрами)

    @Autowired
    private BookRepository bookRepository; // репозиторий для работы с книгой Book

    @Autowired
    private DVDRepository dvdRepository; // репозиторий для работы с фильмом DVD

    @Autowired
    private MagazineRepository magazineRepository; // репозиторий для работы с журналом Magazine

    @Autowired
    private EbookRepository ebookRepository; // репозиторий для работы с электронной книгой Ebook

    private  User user; // объявляем переменную user
    //endRegion

    /**
     * Метод getAddItemInfo - возвращает информацию о том, как использовать метод добавления элемента.
     *
     * @return Строка с инструкцией о том, что необходимо использовать POST для добавления элемента.
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/addItem")
    public ResponseEntity<String> getAddItemInfo()
    {
        return ResponseEntity.ok("Этот метод предназначен для добавления элемента.");
    }

    /**
     * Метод обрабатывает POST-запрос для добавления элемента в библиотеку.
     * Он использует JSON для получения данных элемента и сохраняет его в базе данных.
     *
     * @param json - JSON строка, представляющая элемент для добавления (книга, журнал, DVD и т.д.).
     * @return ResponseEntity с добавленным элементом или ошибкой, если возникла проблема.
     */
    @Transactional(rollbackFor = Exception.class) // Указывает, что метод должен выполняться в транзакции; при возникновении исключений транзакция будет откатана
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/addItem")
    public ResponseEntity<LibraryItem<?>> testAddItem(@RequestBody String json)
    {
        Gson gson = new Gson(); // инициализируем Gson
        JsonObject jsonObject; // объявляем переменную jsonObject класса JsonObject
        LibraryItem<?> item; // объявляем переменную item класса LibraryItem<?>


        try // пытаемся диссериализовать строку в JsonObject и затем диссериализовать в объекты разных классов и сохранить их в базе данных
        {
            JsonElement jsonElement = gson.fromJson(json, JsonElement.class); // преобразуем строку JSON в объект JsonElement с использованием Gson
            jsonObject = jsonElement.getAsJsonObject(); // получаем JsonObject из JsonElement
            String type = jsonObject.get("type").getAsString(); // получаем поле type из jsonObject

            switch (type) // случаи, чему равен type:
            {
                case "Book":
                    item = gson.fromJson(jsonObject, Book.class); // получаем экземпляр класса Book из джейсон объекта
                    item.setAddedDate(new Date()); // устанавливает дату добавления книги в библиотеку
                    String ISBN = bookService.generateISBN(); // в bookService генерируем номер книги по ISBN
                    item.setISBN(ISBN); // устанавливаем книге сгенерированный номер ISBN

                    // Работа с жанрами:
                    JsonArray genresArray = jsonObject.getAsJsonArray("genres"); // получаем жанр как массив
                    List<Genre> genres = new ArrayList<>(); // создаём список жанра
                    for (int i = 0; i < genresArray.size(); i++) // идём по списку жанров
                    {
                        JsonObject genreObject = genresArray.get(i).getAsJsonObject(); // получаем i элемент массива жанра
                        Genre genre = gson.fromJson(genreObject, Genre.class); // дессирилизуем его в класс Genre
                        genres.add(genre); // добавляем объект класса Genre в список жанров
                    }

                    for (Genre genre : genres)  // проходим по списку жанров
                    {
                        Optional<Genre> existingGenre = genreRepository.findByName(genre.getName()); // получаем конкретный жанр по его имени

                        if (existingGenre.isPresent()) // если такой найден, то:
                        {
                            genres.set(genres.indexOf(genre), existingGenre.get()); // устанавливаем найденный жанр в список жанров
                        }
                        else // если такой жанр не найден, то:
                        {
                            genreRepository.save(genre); // сохраняем жанр в базе данных
                        }
                    }

                    ((Book) item).setGenres(genres); // устанавливаем список жанров книге

                    bookRepository.save((Book) item); // сохраняем книгу в базе данных в таблице book_table
                    break; // выход

                case "DVD":
                    item = gson.fromJson(jsonObject, DVD.class); // получаем экземпляр класса DVD из джейсон объекта
                    item.setAddedDate(new Date()); // устанавливает дату добавления фильма DVD в библиотеку
                    String ISBNDVD = abstractItemService.generateISBN(); // в abstractItemService генерируем номер DVD по ISBN
                    item.setISBN(ISBNDVD); // устанавливаем DVD сгенерированный номер ISBN

                    dvdRepository.save((DVD) item); // сохраняем DVD в базе данных в таблице dvd_table
                    break; // выход

                case "Magazine":
                    item = gson.fromJson(jsonObject, Magazine.class); // получаем экземпляр класса Magazine из джейсон объекта
                    item.setAddedDate(new Date());  // устанавливает дату добавления фильма Magazine в библиотеку
                    String ISBNMagazine = abstractItemService.generateISBN(); // в abstractItemService генерируем номер Magazine по ISBN
                    item.setISBN(ISBNMagazine); // устанавливаем Magazine сгенерированный номер ISBN

                    magazineRepository.save((Magazine) item); // сохраняем журнал в базе данных в таблице magazine_table
                    break; // выход

                case "Ebook":
                    item = gson.fromJson(jsonObject, Ebook.class); // получаем экземпляр класса Ebook из джейсон объекта
                    item.setAddedDate(new Date()); // устанавливает дату добавления фильма Ebook в библиотеку
                    String ISBNEbook = abstractItemService.generateISBN(); // в abstractItemService генерируем номер Ebook по ISBN
                    item.setISBN(ISBNEbook); // устанавливаем Ebook сгенерированный номер ISBN

                    ebookRepository.save((Ebook) item); // сохраняем электронную книгу в базе данных в таблице Ebook_table
                    break; // выход

                default:
                    return ResponseEntity.badRequest().body(null); // в случае если тип не соответствует заданным, уведомляем пользователя об этом
            }
        }
        catch (JsonSyntaxException ex) // исключение при работе с Gson
        {
            throw ex; // позволяем обработать исключение JsonSyntaxException глобально
        }
        catch (BookNotSaveExeption ex) // исключение при сохранение объекта "книга" Book в базе данных
        {
            throw ex; // позволяем обработать исключение BookNotSaveExeption глобально
        }
        catch (DVDNotSaveExeption ex) // исключение при сохранение объекта "фильм" DVD в базе данных
        {
            throw ex; // позволяем обработать исключение DVDNotSaveExeption глобально
        }
        catch (MagazineNotSaveExeption ex) // исключение при сохранение объекта "журнал" Magazin в базе данных
        {
            throw ex; // позволяем обработать исключение  глобально
        }
        catch (EbookNotSaveExeption ex) // исключение при сохранение объекта "электронная книга" Ebook в базе данных
        {
            throw ex; // позволяем обработать исключение MagazineNotSaveExeption глобально
        }
        catch (Exception e) // выбрасываем ошибку, иную от кастомных
        {
            e.printStackTrace(); // логируем ошибку
            return ResponseEntity.badRequest().body(null); // уведомляем пользователя об ошибке
        }

        try
        {
            LibraryItem<?> savedItem = transactionItem(item, user); // сохранение вещи в базе данных, в таблице library_items
            return ResponseEntity.ok(savedItem); // возвращаем ответ об успешном сохранении вещи в базе данных
        }
        catch (Exception e) // обрабатываем ошибки
        {
            e.printStackTrace(); // логируем ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // возвращаем ответ об ошибке
        }
    }


    /**
     * Метод transactionItem -  создает и сохраняет транзакцию для переданного элемента.
     *
     * @param item - элемент библиотеки, для которого создаётся транзакция.
     * @return сохранённый элемент после создания транзакции.
     */
    public LibraryItem<?> transactionItem(LibraryItem<?> item, User user)
    {
        LibraryItem<?> savedItem; // объявляем переменную savedItem (сохранённый предмет)
        try
        {
             savedItem = abstractRepository.save(item); // Сохраняем элемент в базе данных

            // Создаем транзакцию для элемента:
            Transaction transaction = new Transaction(); // Создаём новую транзакцию
            transaction.setItem(savedItem); // Привязываем сохранённый элемент к транзакции
            transaction.setTransactionDate(new Date()); // Устанавливаем текущую дату транзакции
            transaction.setUser(user); // устанавливаем транзакции пользователя

            // Сохраняем транзакцию в базе данных:
            transactionRepository.save(transaction); // Сохраняем транзакцию

            return savedItem; // Возвращаем сохранённый элемент
        }
        catch (ItemNotSaveExeption ex) // выбрасываем исключение, ошибка - вещь не сохранена
        {
            throw ex; // позволяем обработать исключение ItemNotSaveExeption глобально
        }
        catch (TransactionNotSaveExeption ex) // выбрасываем исключение, ошибка - транзакция не сохранена
        {
            throw ex; // позволяем обработать исключение TransactionNotSaveExeption глобально
        }
    }
}


