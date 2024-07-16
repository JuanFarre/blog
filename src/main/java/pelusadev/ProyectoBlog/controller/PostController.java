package pelusadev.ProyectoBlog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pelusadev.ProyectoBlog.model.Post;
import pelusadev.ProyectoBlog.model.UserSec;
import pelusadev.ProyectoBlog.service.IPostService;
import pelusadev.ProyectoBlog.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private IPostService postService;

    @Autowired
    private IUserService userService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AUTHOR')")
    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllList(){
        return ResponseEntity.ok(postService.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AUTHOR')")
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id){
        return ResponseEntity.ok(postService.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR')")
    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        UserSec userSec = userService.getById(post.getAuthor().getId());
        post.setAuthor(userSec);
        return ResponseEntity.ok(postService.createPost(post));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (postService.getById(id) != null) {
            postService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id,
                                           @RequestBody Post post){
        Post newPost = postService.getById(id);
        UserSec userSec = userService.getById(post.getAuthor().getId());
        post.setAuthor(userSec);

        if(newPost!=null){
            newPost.setTitle(post.getTitle());
            newPost.setAuthor(post.getAuthor());
            newPost.setContent(post.getContent());

            return  ResponseEntity.ok(postService.updatePost(post, id));

        } else {
            return ResponseEntity.notFound().build();
        }


    }



}
