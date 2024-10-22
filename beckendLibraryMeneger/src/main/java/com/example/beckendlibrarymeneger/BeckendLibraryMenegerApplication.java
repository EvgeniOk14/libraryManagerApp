package com.example.beckendlibrarymeneger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения "BeckendLibraryManager".
 *
 * Аннотация @SpringBootApplication объединяет в себе три другие аннотации:
 * @Configuration, @EnableAutoConfiguration и @ComponentScan, что делает его основным классом для конфигурации и запуска приложения.
 */
@SpringBootApplication // указывает, что этот класс является основным для Spring Boot приложения

public class BeckendLibraryMenegerApplication
{
    /**
     * Главный метод приложения main, который запускает Spring Boot приложение.
     *
     * @param args аргументы командной строки
     */

    public static void main(String[] args)
    {
        SpringApplication.run(BeckendLibraryMenegerApplication.class, args); // запускает Spring Boot приложение, используя указанный класс
    }
}
