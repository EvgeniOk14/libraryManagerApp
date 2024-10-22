package com.example.beckendlibrarymeneger.models;

import jakarta.persistence.*; // Импорт аннотаций JPA для работы с базой данных
import lombok.AllArgsConstructor; // Импорт аннотации для создания конструктора со всеми параметрами
import lombok.Data; // Импорт аннотации для автоматической генерации методов доступа
import lombok.NoArgsConstructor; // Импорт аннотации для создания конструктора без параметров
import java.util.Date; // Импорт класса Date для работы с датами

/**
 * Класс Transaction, представляющий транзакцию проката вещи в библиотеке.
 *
 * Этот класс хранит информацию о транзакциях, включая дату транзакции,
 * дату взятия, срок, пользователя, который взял вещь, и саму вещь.
 *
 */
@Data // генерирует методы доступа (геттеры и сеттеры), методы equals(), hashCode() и toString() для класса автоматически.
@NoArgsConstructor // конструктор без параметров
@AllArgsConstructor // конструктор со всеми параметрами
@Table(name = "transaction_table") // позволяет задать имя таблице "transaction_table"
@Entity // обозначает, что класс Transaction является сущностью JPA, и его экземпляры будут храниться в базе данных
public class Transaction
{
    //region Fields
    @Id // указывает, что это поле является первичным ключом
    @GeneratedValue(strategy = GenerationType.IDENTITY) // автоматическая генерация значения при вставке записи
    private Integer id;  // порядковый номер транзакции (создаётся автоматически)

    @Column(name = "transaction_date") // указывает имя колонки в базе данных
    private Date transactionDate; // дата транзакции (создаём текущую дату)

    @Column(name = "borrow_date") // указывает имя колонки в базе данных
    private Date borrowDate;  // дата взятия вещи на прокат (создаём текущую дату)

    @Column(name = "due_date") // указывает имя колонки в базе данных
    private Date dueDate; // срок взятия вещи на прокат, берём с фронтенда с формы, отправляет пользователь

    @ManyToOne(fetch = FetchType.EAGER) // указывает на связь "многие к одному"
    @JoinColumn(name = "user_Id", referencedColumnName = "id") // создаёт связь через ссылку id из таблицы пользователей
    private User user; // пользователь, который берёт вещь на прокат (получаем user по его id)

    @ManyToOne(fetch = FetchType.EAGER) // указывает на связь "многие к одному"
    @JoinColumn(name = "item_id", referencedColumnName = "id") // создаёт связь через ссылку на id из таблицы вещей
    private LibraryItem<?> item; // вещь, которую берёт пользователь на прокат (получаем item по его ISBN)

    @Column(name = "is_returned") // указывает имя колонки в базе данных
    private Boolean is_returned = true; // флаг, указывающий, была ли вещь возвращена
    //endregion

    /**
     * Переопределяет метод toString() для отображения информации о транзакции.
     *
     * @return строковое представление объекта Transaction
     */
    @Override
    public String toString()
    {
        return "Transaction{" +
                "id=" + id +
                ", transactionDate=" + transactionDate +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", is_returned=" + is_returned +
                '}'; // Возвращает строку с деталями транзакции
    }
}
