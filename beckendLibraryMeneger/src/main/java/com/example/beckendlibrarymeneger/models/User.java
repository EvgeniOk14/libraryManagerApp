package com.example.beckendlibrarymeneger.models;

import jakarta.persistence.*; // Импорт аннотаций JPA для работы с базой данных
import lombok.AllArgsConstructor; // Импорт аннотации для создания конструктора со всеми параметрами
import lombok.Data; // Импорт аннотации для автоматической генерации методов доступа
import lombok.NoArgsConstructor; // Импорт аннотации для создания конструктора без параметров
import java.util.List; // Импорт класса List для работы с коллекциями
import jakarta.validation.constraints.Email; // Импорт аннотации для валидации электронной почты
import jakarta.validation.constraints.NotBlank; // Импорт аннотации для валидации пустых строк

/**
 * Класс User, представляющий пользователя в системе.
 * Этот класс хранит информацию о пользователе, включая его имя, контактную информацию и транзакции.
 */
@Data // генерирует методы доступа (геттеры и сеттеры), методы equals(), hashCode() и toString() для класса автоматически.
@NoArgsConstructor // конструктор без параметров
@AllArgsConstructor // конструктор со всеми параметрами
@Table(name = "user_table") // указывает имя таблицы в базе данных
@Entity // обозначает, что класс User является сущностью JPA, и его экземпляры будут храниться в базе данных
public class User
{
    //region Fields
    @Id // указывает, что это поле является первичным ключом
    @GeneratedValue(strategy = GenerationType.IDENTITY) // автоматическая генерация значения при вставке записи
    private Integer id; // уникальный идентификатор пользователя

    @NotBlank(message = "Имя не может быть пустым") // валидация: имя не должно быть пустым
    @Column(name = "name") // указывает имя колонки в базе данных
    private String name; // поле - имя пользователя

    @NotBlank(message = "Контактная информация не может быть пустой") // валидация: контактная информация не должна быть пустой
    @Email(message = "Некорректный формат электронной почты") // валидация: корректный формат электронной почты
    @Column(name = "contact_info") // указывает имя колонки в базе данных
    private String contactInfo; // поле - контактная информация пользователя (электронная почта)

    @Column(name = "participant_id", unique = true) // указывает имя колонки в базе данных и уникальность значения
    private String participantId; // поле - уникальный идентификатор участника

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // связь "один ко многим" с транзакциями
    private List<Transaction> transactions; // поле - список транзакций, связанных с пользователем
    //endRegion

    /**
     * Переопределяет метод toString() для отображения информации о пользователе.
     *
     * @return строковое представление объекта User
     */
    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id + // идентификатор пользователя
                ", name='" + name + '\'' + // имя пользователя
                ", contactInfo='" + contactInfo + '\'' + // контактная информация пользователя
                ", participantId='" + participantId + '\'' + // уникальный идентификатор участника
                '}'; // возвращает строку с деталями пользователя
    }
}
