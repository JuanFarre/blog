package pelusadev.ProyectoBlog.service;

import pelusadev.ProyectoBlog.model.Role;

import java.util.List;

public interface IRoleService {

    public List<Role> findAll();

    public Role getById(Long id);

    public void deleteById(Long id);

    public Role createRole(Role role);

    public Role updateRole(Role role, Long id);

}
