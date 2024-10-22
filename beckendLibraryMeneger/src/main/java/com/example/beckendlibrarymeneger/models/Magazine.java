package com.example.beckendlibrarymeneger.models;

import jakarta.persistence.*; // импорт всех классов и аннотаций из пакета Jakarta Persistence для работы с JPA (Java Persistence API)
import jakarta.validation.constraints.NotBlank; // импорт валидации полей (не должны быть пустыми)
import lombok.AllArgsConstructor; // импорт аннотации для конструктора со всеми параметрами
import lombok.Data; // импорт аннотации для автоматической генерации методов
import lombok.NoArgsConstructor; // импорт аннотации для конструктора без параметров
import java.util.Date; // импорт класса Date

/**
 * Класс Magazine представляет сущность журнала, который расширяет класс LibraryItem.
 * Содержит поля для хранения информации о журнале, такие как название, издатель и дата выпуска.
 * Аннотации используются для настройки соответствия полей класса с колонками базы данных.
 * Автоматически генерирует методы доступа, equals(), hashCode() и toString() с помощью аннотации Lombok.
 */
@Data // генерирует методы доступа, методы equals(), hashCode() и toString() для класса автоматически.
@NoArgsConstructor // конструктор без параметров
@AllArgsConstructor // конструктор со всеми параметрами
@Table(name = "magazine_table") // позволяет задать имя таблице "magazine_table" в базе данных
@Entity // обозначает, что класс Book является сущностью JPA
public class Magazine extends LibraryItem<Magazine>
{
    //region Field
    @Column(name = "title") // указывает, что это поле соответствует колонке "title" в базе данных
    @NotBlank(message = "Title cannot be blank") // проверка, чтобы название предмета не было пустым
    private String title;   // название предмета (например, для книги - это название книги)

    @Column(name = "magazine_publisher") // указывает, что это поле соответствует колонке "publisher" в базе данных
    private String publisher; // издатель журнала

    @Column(name = "magazine_releasePublication") // указывает, что это поле соответствует колонке "releasePublication" в базе данных
    private Date releasePublication; // дата выпуска журнала
    //endRegion
}
