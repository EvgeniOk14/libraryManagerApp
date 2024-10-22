package com.example.beckendlibrarymeneger.models;

import jakarta.persistence.*; // Импорт всех классов из пакета jakarta.persistence, который содержит аннотации для работы с JPA (Java Persistence API) и управления сущностями базы данных
import jakarta.validation.constraints.NotBlank; // Импорт конкретного класса NotBlank
import lombok.AllArgsConstructor; // Импорт конкретного класса AllArgsConstructor
import lombok.Data; // Импорт конкретного класса Data
import lombok.NoArgsConstructor; // Импорт конкретного класса NoArgsConstructor
import java.util.Date; // Импорт класса Date

/**
 * Абстрактный класс AbstractItem представляет общую сущность для предметов библиотеки.
 * Этот класс содержит общий идентификатор, название предмета, дату добавления,
 * тип предмета, ISBN и список транзакций, связанных с предметом.
 * Он также предоставляет метод для проверки, был ли предмет возвращен.
 * @param <T> тип идентификатора предмета
 */
@MappedSuperclass // указывает, что этот класс является суперклассом для других классов, но не будет иметь своей таблицы в базе данных
@Data // генерирует геттеры, сеттеры, equals, hashCode и toString
@AllArgsConstructor // генерирует конструктор со всеми полями
@NoArgsConstructor // генерирует конструктор без параметров
public abstract class AbstractItem<T>
{
    //region Fields
    @Column(name = "addedDate") // указывает, что это поле соответствует колонке "addedDate" в базе данных
    @NotBlank(message = "addedDate cannot be blank") // проверка, чтобы дата добавления не была пустой
    private Date addedDate;  // дата добавления предмета в библиотеку

    @Column(name = "type") // указывает, что это поле соответствует колонке "type" в базе данных
    @NotBlank(message = "Type cannot be blank") // проверка, чтобы тип не был пустым
    private String type; // тип предмета (например, книга, DVD и прочее)

    @Column(name = "ISBN", unique = true) // указывает, что это поле соответствует колонке "ISBN" в базе данных и должно быть уникальным
    @NotBlank(message = "ISBN cannot be blank") // проверка, чтобы ISBN не был пустым
    private String ISBN;  // ISBN предмета
    //endRegion
}
