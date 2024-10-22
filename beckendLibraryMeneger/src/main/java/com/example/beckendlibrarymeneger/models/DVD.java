package com.example.beckendlibrarymeneger.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor; // Импорт аннотации для конструктора со всеми параметрами
import lombok.Data; // Импорт аннотации для автоматической генерации методов
import lombok.NoArgsConstructor; // Импорт аннотации для конструктора без параметров
import java.util.Date; // Импорт класса Date

/**
 * Сущность, представляющая DVD в системе библиотеки.
 *
 * Этот класс содержит информацию о DVD, включая название,
 * режиссёра и дату релиза. Он используется для отображения и
 * хранения данных о DVD в базе данных.
 *
 * Класс наследует от {@link LibraryItem}, что позволяет
 * использовать общую функциональность для всех предметов библиотеки.
 */
@Data // генерирует методы доступа, методы equals(), hashCode() и toString() для класса автоматически.
@NoArgsConstructor // конструктор без параметров
@AllArgsConstructor // конструктор со всеми параметрами
@Table(name = "dvd_table") // позволяет задать имя таблицы "dvd_table" в базе данных
@Entity // обозначает, что класс Book является сущностью JPA
public class DVD  extends LibraryItem<DVD>
{
    //region Fields
    @Column(name = "title") // указывает, что это поле соответствует колонке "title" в базе данных
    @NotBlank(message = "Title cannot be blank") // проверка, чтобы название предмета не было пустым
    private String title;   // название предмета (например, для книги - это название книги)

    @Column(name = "film_director") // указывает, что это поле соответствует колонке "director" в базе данных
    private String director; // режиссёр фильма

    @Column(name = "film_releaseDate") // указывает, что это поле соответствует колонке "releaseDate" в базе данных
    private Date releaseDate; // дата релиза фильма
    //endRegion
}

