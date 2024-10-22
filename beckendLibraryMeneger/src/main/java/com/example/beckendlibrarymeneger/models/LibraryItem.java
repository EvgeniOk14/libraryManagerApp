package com.example.beckendlibrarymeneger.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo; // Импорт аннотации для поддержки полиморфизма при сериализации/десериализации объектов в JSON с помощью Jackson
import jakarta.persistence.*; // Импорт всех классов и аннотаций из пакета Jakarta Persistence для работы с JPA (Java Persistence API)
import lombok.AllArgsConstructor; // Импорт аннотации AllArgsConstructor для автоматической генерации конструктора со всеми полями
import lombok.Data; // Импорт аннотации Data для автоматической генерации методов доступа и других методов
import lombok.NoArgsConstructor; // Импорт аннотации NoArgsConstructor для автоматической генерации конструктора без параметров
import java.util.ArrayList; // импорт ArrayList
import java.util.List; // импорт List

/**
 * Класс LibraryItem, представляющий общую сущность для всех предметов библиотеки
 * (для работы с десериализацией JsonObject в объект LibraryItem).
 *
 * Этот класс содержит общие атрибуты и связи для всех предметов, таких как: Book, DVD и т.д.
 *
 * @param <T> тип идентификатора предмета
 */
@Data // генерирует методы доступа (геттеры и сеттеры), методы equals(), hashCode() и toString() для класса автоматически.
@AllArgsConstructor // генерирует конструктор со всеми параметрами
@NoArgsConstructor // генерирует конструктор без параметров
@Entity // обозначает, что класс LibraryItem является сущностью JPA, и его экземпляры будут храниться в базе данных
@Table(name = "library_items") // указывает имя таблицы в базе данных, соответствующей этой сущности
@Inheritance(strategy = InheritanceType.JOINED) // указывает, что для наследников будут созданы отдельные таблицы, связанные с общей таблицей
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,             // указывает, что тип будет определяться по имени
        include = JsonTypeInfo.As.PROPERTY,    //  включает тип в JSON-объект как отдельное свойство
        property = "type"                     //   указывает имя свойства для хранения типа
)
public class LibraryItem<T> extends AbstractItem<T>
{
    //region Fields
    @Id // указывает, что это поле является идентификатором
    @GeneratedValue(strategy = GenerationType.IDENTITY) // автоматически генерирует значение для идентификатора
    private Integer id; // идентификатор предмета

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL) // указывает связь "один ко многим" с сущностью
    private List<Transaction> transactions = new ArrayList<>(); // список транзакций, связанных с предметом
    //endRegion
}

