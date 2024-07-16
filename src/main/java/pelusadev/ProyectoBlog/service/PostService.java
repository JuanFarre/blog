package pelusadev.ProyectoBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pelusadev.ProyectoBlog.model.Post;
import pelusadev.ProyectoBlog.repository.IPostRepository;

import java.util.List;

@Service
public class PostService implements IPostService {

    @Autowired
    private IPostRepository postRepository;




    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post getById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {

        postRepository.deleteById(id);

    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post, Long id) {
        Post existingPost = this.getById(id);

        if(existingPost!=null){
            existingPost.setAuthor(post.getAuthor());
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());
            return postRepository.save(existingPost);
        }


        return null;
    }
}
