package com.example.beckendlibrarymeneger.interfaces;

import com.example.beckendlibrarymeneger.models.User; // Импорт класса User
import org.springframework.data.jpa.repository.JpaRepository; // Импорт интерфейса JpaRepository
import org.springframework.stereotype.Repository; // Импорт аннотации Repository
import java.util.List;

/**
 * Интерфейс UserRepository, который предоставляет методы для работы с
 * сущностями User в базе данных.
 * <p>
 * Это расширение интерфейса JpaRepository, которое предоставляет
 * базовые операции для работы с User, включая сохранение,
 * удаление и поиск пользователей.
 * </p>
 */
@Repository // Аннотация указывает, что это интерфейс репозитория для доступа к данным
public interface UserRepository extends JpaRepository<User, Integer> // Интерфейс, расширяющий JpaRepository
{
    /**
     * Находит пользователя по уникальному идентификатору участника.
     *
     * @param participantId Уникальный идентификатор участника, по которому необходимо найти пользователя.
     * @return Пользователь с указанным идентификатором участника, или null, если не найден.
     */
    User findByParticipantId(String participantId); // Метод поиска пользователя по его номеру participantId


    /**
     * Находит пользователя по информации о контакте (email).
     *
     * @param contactInfo строка, содержащая информацию о контакте пользователя его email
     * @return экземпляр класса User, соответствующий указанной информации о контакте,
     *         или null, если пользователь не найден
     */
    User findByContactInfo(String contactInfo); // метод поиска пользователя по его электронной почте contactInfo


    /**
     * Находит всех пользователей в базе данных
     *
     * @return список экземпляров класса User, или null, если пользователь не найден
     */
    List<User> findAll(); // метод получает всех пользователей из базы данных


    /**
     * Метод для проверки существования пользователя по контактной информации.
     *
     * Этот метод автоматически генерируется Spring Data JPA.
     * Он выполняет запрос к базе данных для проверки, существует ли пользователь с указанной контактной информацией.
     * Возвращает true, если такой пользователь найден, иначе false.
     *
     * @param contactInfo контактная информация (например, email) для проверки
     * @return true, если пользователь с такой контактной информацией существует, иначе false
     */
    boolean existsByContactInfo(String contactInfo);  // проверяет, существует ли пользователь с указанной контактной информацией

}
