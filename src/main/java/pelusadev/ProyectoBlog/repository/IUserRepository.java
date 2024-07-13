package pelusadev.ProyectoBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pelusadev.ProyectoBlog.model.UserSec;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserSec,Long> {
    Optional<UserSec> findUserEntityByUsername(String username);
}
