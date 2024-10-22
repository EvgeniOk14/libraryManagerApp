package com.example.beckendlibrarymeneger.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity; // Импорт аннотации Entity, обозначающей, что класс является сущностью JPA
import jakarta.persistence.Table; // Импорт аннотации Table для определения имени таблицы в базе данных
import jakarta.persistence.Id; // Импорт аннотации Id для указания первичного ключа
import jakarta.persistence.GeneratedValue; // Импорт аннотации GeneratedValue для автоматической генерации значений первичного ключа
import jakarta.persistence.GenerationType; // Импорт GenerationType для определения стратегии генерации идентификаторов
import jakarta.persistence.Column; // Импорт аннотации Column для настройки столбцов таблицы
import jakarta.persistence.ManyToMany; // Импорт аннотации ManyToMany для установки связи "многие ко многим"
import lombok.AllArgsConstructor; // Импорт аннотации AllArgsConstructor для автоматической генерации конструктора со всеми полями
import lombok.Data; // Импорт аннотации Data для автоматической генерации методов доступа и других методов
import lombok.NoArgsConstructor; // Импорт аннотации NoArgsConstructor для автоматической генерации конструктора без параметров
import java.util.ArrayList; // Импорт класса ArrayList для создания списка жанров
import java.util.List; // Импорт интерфейса List для работы со списками

/**
 * Класс Genre, представляющий жанр книги в библиотечной системе.
 *
 * Этот класс содержит атрибуты жанра и связь с книгами.
 *
 */
@Data // генерирует методы доступа (геттеры и сеттеры), методы equals(), hashCode() и toString() для класса автоматически.
@NoArgsConstructor // генерирует конструктор без параметров
@AllArgsConstructor // генерирует конструктор со всеми параметрами
@Table(name = "genre_table") // позволяет задать имя таблицы "genre_table" в базе данных
@Entity // обозначает, что класс Genre является сущностью JPA
public class Genre
{
    //region Fields
    @Id // указывает, что это поле является первичным ключом
    @GeneratedValue(strategy = GenerationType.IDENTITY) // автоматическая генерация значений первичного ключа
    private Integer id; // идентификатор жанра

    @Column(name = "name", unique = true) // указывает, что это поле соответствует колонке "name" в базе данных и должно быть уникальным
    private String name; // название жанра

    @ManyToMany(mappedBy = "genres") // обратная связь с книгами, где "genres" является полем в классе Book
    @JsonBackReference // указывает на то, что это "обратная" сторона отношения
    private List<Book> books = new ArrayList<>(); // список книг, относящихся к данному жанру
    //endRegion
}
