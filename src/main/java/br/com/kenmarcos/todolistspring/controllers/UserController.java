package br.com.kenmarcos.todolistspring.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kenmarcos.todolistspring.models.UserModel;
import br.com.kenmarcos.todolistspring.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {

        var newUser = userService.create(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}