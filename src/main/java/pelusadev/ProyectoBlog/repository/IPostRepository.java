package pelusadev.ProyectoBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pelusadev.ProyectoBlog.model.Post;

@Repository
public interface IPostRepository extends JpaRepository<Post,Long> {
}
