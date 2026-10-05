package org.timur.roadmap.tennisscoreboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // Код, не предназначенный для работы приложения, стоит удалять перед коммитом.

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World from Spring MVC without Boot!";
    }
}