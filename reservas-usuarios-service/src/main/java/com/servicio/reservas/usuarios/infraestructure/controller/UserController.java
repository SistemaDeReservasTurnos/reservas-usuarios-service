package com.servicio.reservas.usuarios.infraestructure.controller;

import com.servicio.reservas.usuarios.aplication.dto.UpdateEmailRequest;
import com.servicio.reservas.usuarios.aplication.dto.UserRequest;
import com.servicio.reservas.usuarios.aplication.dto.UserResponse;
import com.servicio.reservas.usuarios.aplication.dto.UpdatePasswordRequest;
import com.servicio.reservas.usuarios.aplication.services.UserService;
import com.servicio.reservas.usuarios.domain.entities.Role;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createuser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
    
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponse>> getAllByRole(@PathVariable Role role) {
        List<UserResponse> users = userService.getAllByRole(role.name().toLowerCase());
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email){
            UserResponse user = userService.getUserByEmail(email);
            return ResponseEntity.ok().body(user);
    }

    @PutMapping("/deactivate/{email}")
    public ResponseEntity<String> deactivateUser(@PathVariable String email) {
            userService.deactivateUser(email);
            return ResponseEntity.ok("User successfully deactivated");
    }

    @PutMapping("/update/{email}/{column}/{value}")
    public ResponseEntity<String> updateUser(
             @PathVariable String email,
             @PathVariable String column,
             @PathVariable String value) {
            userService.updateUser(email, column, value);
            return ResponseEntity.ok("User successfully updated");
    }

    @PutMapping("/update-password/{email}")
    public ResponseEntity<String> updatePassword(
            @PathVariable String email,
            @Valid @RequestBody UpdatePasswordRequest request) {
            userService.updatePassword(email, request);
            return ResponseEntity.ok("Password updated correctly");
    }

    @PutMapping("/update-email/{email}")
    public ResponseEntity<String> updateEmail(
            @PathVariable String email,
            @Valid @RequestBody UpdateEmailRequest request) {
        userService.updateEmail(email, request);
        return ResponseEntity.ok("Email updated correctly");
    }
}
