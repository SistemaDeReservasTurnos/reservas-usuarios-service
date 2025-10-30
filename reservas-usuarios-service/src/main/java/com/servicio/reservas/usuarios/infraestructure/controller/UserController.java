package com.servicio.reservas.usuarios.infraestructure.controller;

import com.servicio.reservas.usuarios.aplication.dto.UserRequest;
import com.servicio.reservas.usuarios.aplication.dto.UserResponse;
import com.servicio.reservas.usuarios.aplication.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        try {
            UserResponse userResponse = userService.createuser(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateUser(@PathVariable Long id) {
        try {
            userService.deactivateUser(id);
            return ResponseEntity.ok("Usuario desactivado correctamente");
        } catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
