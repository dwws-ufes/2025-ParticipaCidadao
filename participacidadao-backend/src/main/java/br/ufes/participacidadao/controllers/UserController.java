package br.ufes.participacidadao.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.ufes.participacidadao.exceptions.UserNotFoundException;
import br.ufes.participacidadao.models.UserModel;
import br.ufes.participacidadao.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/new")
    public UserModel createUser(@RequestBody UserModel userModel) {
        // Encode the password before saving
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return this.userRepository.save(userModel);
    }

    // Endpoint for HTTP Basic Auth check and user info return
    @GetMapping("/auth/check")
    public UserModel authCheck(org.springframework.security.core.Authentication authentication) {
        // Find user by email (username)
        String email = authentication.getName();
        UserModel user = this.userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(-1L);
        }
        return user;
    }

    @GetMapping("/{id}")
    public UserModel getUser(@PathVariable Long id) {
        if(!this.userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        return this.userRepository.findById(id).get();
    }

    @PutMapping("/{id}")
    public UserModel updateUser(@PathVariable Long id, @RequestBody UserModel userModel) {
        // if(!this.userRepository.existsById(id)) {
        //     throw new UserNotFoundException(id);
        // }
        // userModel.setId(id);
        // // Always encode the password before saving (if not already encoded)
        // String password = userModel.getPassword();
        // if (password != null && !password.startsWith("$2a$") && !password.startsWith("$2b$") && !password.startsWith("$2y$")) {
        //     userModel.setPassword(passwordEncoder.encode(password));
        // }
        // return this.userRepository.save(userModel);

        UserModel existing = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        
        existing.setName(userModel.getName());
        existing.setEmail(userModel.getEmail());
        existing.setPassword(passwordEncoder.encode(userModel.getPassword()));
        existing.setCpf(userModel.getCpf());
        existing.setBirthDate(userModel.getBirthDate());

        return this.userRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {

        if(!this.userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        this.userRepository.deleteById(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }
}
