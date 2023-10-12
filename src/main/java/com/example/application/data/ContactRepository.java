package com.example.application.data;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("select c from Contact c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))") //1
    List<Contact> search(@Param("searchTerm") String searchTerm); //2
}
 /*
 В этом примере аннотация @Query используется для определения пользовательского запроса (см. аннотацию 1).
 В этом случае он проверяет, соответствует ли строка имени или фамилии, и игнорирует регистр.
 В запросе используется язык запросов Java Persistence (JPQL),
 который представляет собой SQL-подобный язык для запросов к базам данных, управляемым JPA.
 */

/*
1 Этот запрос указывает на выполнение поиска контактов по имени (firstName) или по фамилии (lastName)
в таблице Contact. Запрос использует параметр searchTerm для указания поискового термина.

Здесь более подробно расшифрован запрос:

SELECT c FROM Contact c
WHERE lower(c.firstName) LIKE lower(concat('%', :searchTerm, '%'))
   OR lower(c.lastName) LIKE lower(concat('%', :searchTerm, '%'))

- SELECT c FROM Contact c: Выбирает все объекты Contact из таблицы Contact.

- WHERE lower(c.firstName) LIKE lower(concat('%', :searchTerm, '%')):
Условие, которое проверяет, соответствует ли имя контакта (firstName) поисковому термину (searchTerm) или содержит его.
 LOWER приводит имя к нижнему регистру, а CONCAT объединяет поисковый термин со знаком % с обеих сторон,
 чтобы найти подстроку.

- OR lower(c.lastName) LIKE lower(concat('%', :searchTerm, '%')):
Условие, которое проверяет, соответствует ли фамилия контакта (lastName) поисковому термину (searchTerm)
или содержит его. Аналогично, LOWER и CONCAT используются для приведения к нижнему регистру и поиска по подстроке фамилии.

Таким образом, этот запрос позволяет искать контакты по имени или фамилии, используя поисковый термин.
Он будет возвращать все контакты, удовлетворяющие условиям, которые соответствуют или содержат заданный поисковый термин.

*/