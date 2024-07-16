package pelusadev.ProyectoBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pelusadev.ProyectoBlog.model.Role;
import pelusadev.ProyectoBlog.model.UserSec;
import pelusadev.ProyectoBlog.service.IRoleService;
import pelusadev.ProyectoBlog.service.IUserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AUTHOR')")
    @GetMapping("/all")
    public ResponseEntity<List<UserSec>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AUTHOR')")
    @GetMapping("/find/{id}")
    public ResponseEntity<UserSec> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<UserSec> createUser(@RequestBody UserSec userSec) {

        Set<Role> roleList = new HashSet<Role>();
        Role readRole;

        //encriptamos contraseña
        userSec.setPassword(userService.encriptPassword(userSec.getPassword()));

        // Recuperar la Permission/s por su ID
        for (Role role : userSec.getRolesList()){
            readRole = roleService.getById(role.getId());
            if (readRole != null) {
                //si encuentro, guardo en la lista
                roleList.add(readRole);
            }
        }

        if (!roleList.isEmpty()) {
            userSec.setRolesList(roleList);

            UserSec newUser = userService.createUserSec(userSec);
            return ResponseEntity.ok(newUser);
        }
        return null;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
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
