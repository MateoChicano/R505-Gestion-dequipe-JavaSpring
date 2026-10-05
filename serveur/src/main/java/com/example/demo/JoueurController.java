package com.example.demo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/joueurs")
public class JoueurController {
    
    @GetMapping("/bonjour")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
      return String.format("Hello %s!", name);
    }

    @GetMapping
    public String getAllJoueurs() {
        return String.format("Liste de joueurs de malade");    
    }
}
