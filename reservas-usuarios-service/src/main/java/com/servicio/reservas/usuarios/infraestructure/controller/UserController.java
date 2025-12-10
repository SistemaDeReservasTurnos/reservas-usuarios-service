package com.servicio.reservas.usuarios.infraestructure.controller;

import com.servicio.reservas.usuarios.aplication.dto.*;
import com.servicio.reservas.usuarios.aplication.services.IUserService;
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
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('SCOPE_INTERNAL_SERVICE') or hasAuthority('ROLE_ADMINISTRADOR')")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createuser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
    
    @GetMapping("/role/{role}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserResponse>> getAllByRole(@PathVariable Role role) {
        List<UserResponse> users = userService.getAllByRole(role.name().toLowerCase());
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAuthority('SCOPE_INTERNAL_SERVICE') or hasAuthority('ROLE_ADMINISTRADOR') or authentication.name == #email")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email){
            UserResponse user = userService.getUserByEmail(email);
            return ResponseEntity.ok().body(user);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('SCOPE_INTERNAL_SERVICE')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PutMapping("/deactivate/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
            userService.deactivateUser(id);
            return ResponseEntity.ok("User successfully deactivated");
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or authentication.name == #email")
    public ResponseEntity<String> updateUser(
             @RequestBody UpdateUserRequest request
) {
            userService.updateUser(request);
            return ResponseEntity.ok("User successfully updated");
    }

    @PutMapping("/update-password/{email}")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or authentication.name == #email")
    public ResponseEntity<String> updatePassword(
            @PathVariable String email,
            @Valid @RequestBody UpdatePasswordRequest request) {
            userService.updatePassword(email, request);
            return ResponseEntity.ok("Password updated correctly");
    }

    @PutMapping("/update-email/{email}")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or authentication.name == #email")
    public ResponseEntity<String> updateEmail(
            @PathVariable String email,
            @Valid @RequestBody UpdateEmailRequest request) {
        userService.updateEmail(email, request);
        return ResponseEntity.ok("Email updated correctly");
    }
}
