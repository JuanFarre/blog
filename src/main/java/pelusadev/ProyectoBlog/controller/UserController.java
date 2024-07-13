package pelusadev.ProyectoBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pelusadev.ProyectoBlog.model.UserSec;
import pelusadev.ProyectoBlog.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserSec>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserSec> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<UserSec> createUser(@RequestBody UserSec userSec) {
        return ResponseEntity.ok(userService.createUserSec(userSec));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<UserSec> updateUser(@RequestBody UserSec userSec, @PathVariable Long id) {
        UserSec existingUser = userService.getById(id);

        if (existingUser != null) {
            // Actualizar el usuario con los datos recibidos
            existingUser.setUsername(userSec.getUsername());
            existingUser.setPassword(userSec.getPassword());
            existingUser.setEnabled(userSec.isEnabled());
            existingUser.setAccountNotExpired(userSec.isAccountNotExpired());
            existingUser.setAccountNotLocked(userSec.isAccountNotLocked());
            existingUser.setCredentialNotExpired(userSec.isCredentialNotExpired());

            // Actualizar los roles del usuario llamando al servicio
            existingUser.setRolesList(userSec.getRolesList());

            // Guardar el usuario actualizado
            UserSec updatedUser = userService.updateUserSec(existingUser, id);

            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser); // Devolver respuesta exitosa con el usuario actualizado
            } else {
                return ResponseEntity.notFound().build(); // Manejar caso donde no se encuentra el usuario
            }
        } else {
            return ResponseEntity.notFound().build(); // Manejar caso donde no se encuentra el usuario
        }
    }
}
