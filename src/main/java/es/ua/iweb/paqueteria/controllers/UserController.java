package es.ua.iweb.paqueteria.controllers;

import es.ua.iweb.paqueteria.dto.UserDTO;
import es.ua.iweb.paqueteria.service.UserService;
import es.ua.iweb.paqueteria.type.RoleType;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@CrossOrigin
@Hidden
public class UserController {
    @Autowired
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/role")
    public ResponseEntity<String> updateUserRole(
            @RequestParam("email") String email,
            @RequestParam("role") RoleType role
    ) {
        userService.updateUserRole(email, role);
        return ResponseEntity.ok("Role updated successfully");
    }
}
