package pelusadev.ProyectoBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pelusadev.ProyectoBlog.model.Role;
import pelusadev.ProyectoBlog.repository.IRoleRepository;

import java.util.List;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;


    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontró el rol con ID: " + id));

        // Eliminar referencias en la tabla intermedia
        role.getPermissionsList().clear();
        roleRepository.save(role);

        // Eliminar el rol
        roleRepository.deleteById(id);
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Role role, Long id) {
        Role existingRole = this.getById(id);

        if (existingRole != null) {
            existingRole.setRole(role.getRole());
            existingRole.setPermissionsList(role.getPermissionsList());
            return roleRepository.save(existingRole);
        } else {
            return null;
        }
    }
}
