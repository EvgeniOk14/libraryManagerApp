package com.example.beckendlibrarymeneger.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor; // Импорт аннотации для конструктора со всеми параметрами
import lombok.Data; // Импорт аннотации для автоматической генерации методов
import lombok.NoArgsConstructor; // Импорт аннотации для конструктора без параметров
import java.util.Date; // Импорт класса Date

/**
 * Сущность, представляющая электронную книгу в системе библиотеки.
 *
 * Этот класс содержит информацию о электронной книге, включая
 * название, автора и дату публикации. Он используется для отображения
 * и хранения данных об электронных книгах в базе данных.
 *
 * Класс наследует от {@link LibraryItem}, что позволяет использовать
 * общую функциональность для всех предметов библиотеки.
 */
@Data    // генерирует методы доступа, методы equals(), hashCode() и toString() для класса автоматически.
@NoArgsConstructor                // конструктор без параметров
@AllArgsConstructor              // конструктор со всеми параметрами
@Table(name = "ebook_table")    //  позволяет задать имя таблицы "ebook_table" в базе данных
@Entity                        // обозначает, что класс Book является сущностью JPA
public class Ebook extends LibraryItem<Ebook>
{
    //region Fields
    @Column(name = "title") // указывает, что это поле соответствует колонке "title" в базе данных
    @NotBlank(message = "Title cannot be blank") // проверка, чтобы название предмета не было пустым
    private String title;   // название предмета (например, для книги - это название книги)

    @Column(name = "eBook_Author")   // указывает, что это поле соответствует колонке "eBookAuthor" в базе данных
    private String eBookAuthor;    // автор электронной книги

    @Column(name = "eBook_PublicationDate") // указывает, что это поле соответствует колонке "eBookPublicationDate" в базе данных
    private Date eBookPublicationDate;    // дата публикации электронной книги
    //endRegion
}



