package pelusadev.ProyectoBlog.service;

import pelusadev.ProyectoBlog.model.Post;

import java.util.List;

public interface IPostService {

    public List<Post> findAll();

    public Post getById(Long id);

    public void deleteById(Long id);

    public Post createPost(Post post);

    public Post updatePost(Post post, Long id);

}
