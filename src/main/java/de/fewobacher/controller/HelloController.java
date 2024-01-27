package de.fewobacher.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController
{
    @GetMapping("/hello")
    public ResponseEntity<String> sayHello()
    {
        return ResponseEntity.ok("QR Code Server is up and running!");
    }
}
