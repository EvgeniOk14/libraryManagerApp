package com.example.beckendlibrarymeneger.service;

import com.google.gson.Gson; // Импорт класса Gson для работы с JSON
import com.google.gson.JsonElement; // Импорт класса JsonElement для представления JSON элементов
import com.google.gson.JsonObject; // Импорт класса JsonObject для представления JSON объектов
import com.google.gson.JsonSyntaxException; // Импорт класса для обработки ошибок синтаксиса JSON
import org.springframework.stereotype.Service; // Импорт аннотации для обозначения класса как сервиса

/**
 * Класс GsonService - отвечает за десериализацию строки в JsonObject
 * и дальнейшее преобразование в нужный объект (Класс).
 */
@Service // обозначает класс как сервисный компонент в Spring
public class GsonService
{
    //region Fields
    private final Gson gson = new Gson(); // инициализация объекта Gson для работы с JSON
    //endregion

    /**
     * Метод - преобразует строку JSON в объект JsonObject.
     *
     * @param json строка JSON, которую нужно разобрать.
     * @return JsonObject, полученный из строки JSON.
     * @throws JsonSyntaxException если строка JSON имеет неверный синтаксис.
     */
    public JsonObject parseJson(String json) throws JsonSyntaxException
    {
        JsonElement jsonElement = gson.fromJson(json, JsonElement.class); // преобразование строки JSON в JsonElement
        return jsonElement.getAsJsonObject(); // преобразование JsonElement в JsonObject и возврат его
    }

    /**
     * Метод - десериализует строку JSON в объект указанного типа.
     *
     * @param json строка JSON, которую нужно десериализовать.
     * @param classOfT класс объекта, в который будет десериализован JSON.
     * @param <T> тип объекта.
     * @return объект типа T, полученный из строки JSON.
     * @throws JsonSyntaxException если строка JSON имеет неверный синтаксис.
     */
    public <T> T deserialize(String json, Class<T> classOfT) throws JsonSyntaxException
    {
        return gson.fromJson(json, classOfT); // десериализация строки JSON в объект указанного типа
    }
}
