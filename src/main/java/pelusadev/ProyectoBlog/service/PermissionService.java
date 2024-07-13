package pelusadev.ProyectoBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pelusadev.ProyectoBlog.model.Permission;
import pelusadev.ProyectoBlog.repository.IPermissionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService implements IPermissionService{

    @Autowired
    private IPermissionRepository permissionRepository;

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return permissionRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {

        permissionRepository.deleteById(id);

    }

    @Override
    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Permission updatePermission(Permission permission, Long id) {
        Permission newPermi =this.findById(id).orElse(null);

        newPermi = permission;

        return permissionRepository.save(newPermi);
    }
}
