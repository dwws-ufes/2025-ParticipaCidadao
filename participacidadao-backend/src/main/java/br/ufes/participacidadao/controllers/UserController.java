package br.ufes.participacidadao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufes.participacidadao.models.UserModel;
import br.ufes.participacidadao.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/new")
    public UserModel registerUser(@RequestBody UserModel userModel) {
        return this.userRepository.save(userModel);
    }

    @GetMapping("/new")
    public String registerUser() {
        return "Gugu";
    }
}
