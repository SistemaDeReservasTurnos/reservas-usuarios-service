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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('SCOPE_SCOPE_INTERNAL_SERVICE') or hasAuthority('ROLE_ADMINISTRADOR')")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createuser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
    
    @GetMapping("/role/{role}")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public ResponseEntity<List<UserResponse>> getAllByRole(@PathVariable Role role) {
        List<UserResponse> users = userService.getAllByRole(role.name().toLowerCase());
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAuthority('SCOPE_SCOPE_INTERNAL_SERVICE') or hasAuthority('ROLE_ADMINISTRADOR') or authentication.name == #email")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email){
            UserResponse user = userService.getUserByEmail(email);
            return ResponseEntity.ok().body(user);
    }

    @PutMapping("/deactivate/{email}")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public ResponseEntity<String> deactivateUser(@PathVariable String email) {
            userService.deactivateUser(email);
            return ResponseEntity.ok("User successfully deactivated");
    }

    @PutMapping("/update/{email}/{column}/{value}")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or authentication.name == #email")
    public ResponseEntity<String> updateUser(
             @PathVariable String email,
             @PathVariable String column,
             @PathVariable String value) {
            userService.updateUser(email, column, value);
            return ResponseEntity.ok("User successfully updated");
    }

    @PutMapping("/update-password/{email}")
    @PreAuthorize("authentication.name == #email")
    public ResponseEntity<String> updatePassword(
            @PathVariable String email,
            @Valid @RequestBody UpdatePasswordRequest request) {
            userService.updatePassword(email, request);
            return ResponseEntity.ok("Password updated correctly");
    }

    @PutMapping("/update-email/{email}")
    @PreAuthorize("authentication.name == #email")
    public ResponseEntity<String> updateEmail(
            @PathVariable String email,
            @Valid @RequestBody UpdateEmailRequest request) {
        userService.updateEmail(email, request);
        return ResponseEntity.ok("Email updated correctly");
    }
}
