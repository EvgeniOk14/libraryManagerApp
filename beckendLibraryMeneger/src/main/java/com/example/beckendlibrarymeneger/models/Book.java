package com.example.beckendlibrarymeneger.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*; // Импорт JPA аннотаций
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor; // Импорт аннотации для конструктора со всеми параметрами
import lombok.Data; // Импорт аннотации для автоматической генерации методов
import lombok.NoArgsConstructor; // Импорт аннотации для конструктора без параметров
import java.util.ArrayList; // Импорт класса ArrayList
import java.util.Date; // Импорт класса Date
import java.util.List; // Импорт класса List

/**
 * Класс Book представляет собой сущность книги в библиотеке.
 * Этот класс содержит специфические для книги атрибуты, такие как автор,
 * дата публикации и жанры, к которым относится книга.
 */
@Data // генерирует методы доступа, методы equals(), hashCode() и toString() для класса автоматически.
@NoArgsConstructor // конструктор без параметров
@AllArgsConstructor // конструктор со всеми параметрами
@Table(name = "book_table") // позволяет задать имя таблицы "book_table" в базе данных
@Entity // обозначает, что класс Book является сущностью JPA
public class Book extends LibraryItem<Book> // Указываем Book как тип для идентификатора
{
    //region Fields
    @Column(name = "title") // указывает, что это поле соответствует колонке "title" в базе данных
    @NotBlank(message = "Title cannot be blank") // проверка, чтобы название предмета не было пустым
    private String title;   // название предмета (например, для книги - это название книги)

    @Column(name = "book_author") // указывает имя колонки в таблице базы данных
    private String author; // поле "автор" книги. Имя автора книги

    @Column(name = "book_publicationDate") // указывает имя колонки в таблице базы данных
    private Date publicationDate; // поле "дата публикации" книги. Дата, когда книга была опубликована

    @ManyToMany  // определяет связь многие-ко-многим
    @JoinTable(
            name = "book_genre", // указывает имя промежуточной таблицы, связывающей книги и жанры
            joinColumns = @JoinColumn(name = "book_id"), // колонка для идентификатора книги
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id") // колонка для идентификатора жанра
    )
    @JsonManagedReference // указывает на то, что это "владелец" отношения
    private List<Genre> genres = new ArrayList<>(); // поле "жанры" — связь с таблицей жанров. Список жанров, к которым относится книга
    //endRegion

    @Override // переопределение метода toString
    public String toString()
    {
        return "Book{" +
                "title=" +  title +
                ", bookAuthor='" + author + '\'' +
                ", genreCount=" + (genres != null ? genres.size() : 0) + // указываем только количество жанров
                '}';
    }
}















