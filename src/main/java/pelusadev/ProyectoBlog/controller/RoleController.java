package pelusadev.ProyectoBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pelusadev.ProyectoBlog.model.Permission;
import pelusadev.ProyectoBlog.model.Role;
import pelusadev.ProyectoBlog.service.IPermissionService;
import pelusadev.ProyectoBlog.service.IRoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/find/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getById(id));
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Set<Permission> permiList = new HashSet<Permission>();
        Permission readPermission;

        // Recuperar la Permission/s por su ID
        for (Permission per : role.getPermissionsList()) {
            readPermission = permissionService.findById(per.getId()).orElse(null);
            if (readPermission != null) {
                //si encuentro, guardo en la lista
                permiList.add(readPermission);
            }
        }

        role.setPermissionsList(permiList);
        Role newRole = roleService.createRole(role);
        return ResponseEntity.ok(newRole);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        try {
            roleService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // Log the exception
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<Role> updateRole(@RequestBody Role role, @PathVariable Long id) {
        Role existingRole = roleService.getById(id);

        if (existingRole != null) {
            existingRole = role;
            // Guardamos el role
            return ResponseEntity.ok(roleService.updateRole(existingRole, id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
