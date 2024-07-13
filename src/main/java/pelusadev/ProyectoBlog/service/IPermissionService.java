package pelusadev.ProyectoBlog.service;

import pelusadev.ProyectoBlog.model.Permission;

import java.util.List;
import java.util.Optional;

public interface IPermissionService {

    public List<Permission> findAll();

    Optional<Permission> findById(Long id);

    public void deleteById(Long id);

    public Permission createPermission(Permission permission);

    public Permission updatePermission(Permission permission, Long id);


}
