package com.israr.israr_zaslavskaya_inga_voting_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/main-menu")
    public String mainMenu() {
        return "main-menu";
    }

    @GetMapping("/menu-admin")
    public String adminMenu() {
        return "menu-admin";
    }

}
