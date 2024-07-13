package pelusadev.ProyectoBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pelusadev.ProyectoBlog.model.Permission;
import pelusadev.ProyectoBlog.service.IPermissionService;
import pelusadev.ProyectoBlog.service.PermissionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;


    @GetMapping("/all")
    public ResponseEntity<List<Permission>> getAllPermissions(){
        return ResponseEntity.ok(permissionService.findAll());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id){

        Optional<Permission> permission = permissionService.findById(id);
        return permission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission){

        return ResponseEntity.ok(permissionService.createPermission(permission));

    }

    @DeleteMapping("/delete/{id}")
    public void deletePermission(@PathVariable Long id){

        permissionService.deleteById(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Permission> updatePermission(@RequestBody Permission permission,
                                                       @PathVariable Long id) {
        Optional<Permission> existingPermission = permissionService.findById(id);

        if (existingPermission.isPresent()) {
            Permission updatedPermission = existingPermission.get();
            updatedPermission.setPermissionName(permission.getPermissionName());
            return ResponseEntity.ok(permissionService.updatePermission(updatedPermission, id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
