package pelusadev.ProyectoBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pelusadev.ProyectoBlog.model.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Long> {
}
