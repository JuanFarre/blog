package pelusadev.ProyectoBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pelusadev.ProyectoBlog.model.Permission;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission,Long> {
}
