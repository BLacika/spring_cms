package bala.spring_cms.service;

import bala.spring_cms.model.Post;
import bala.spring_cms.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    /**
     * Get all Post
     */
    public List<Post> getAllPost() {
        return repository.findAll();
    }

    /**
     * Get all Post shorted by updated on desc
     */
    public List<Post> getAllPostShorted() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt"));
    }

    /**
     * Get one Post by Id
     */
    public Post getOne(Long id) {
        var post = repository.findById(id);
        return post.get();
    }

    /**
     * Create a new Post
     */
    public void createPost(Post post, MultipartFile image) {
        post.setCreatedAt(new Date());
        post.setUpdatedAt(new Date());

        byte[] file;
        try {
             file = image.getBytes();
             post.setFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        repository.save(post);
    }

    /**
     * Update a Post
     */
    public void updatePost(Long id, Post updatedPost, MultipartFile image) {
        var postToUpdate = repository.findById(id);

        updatedPost.setUpdatedAt(new Date());

        if (image.isEmpty() || image == null) {
            updatedPost.setFile(postToUpdate.get().getFileInBytes());

            repository.save(updatedPost);
        } else {
            try {
                updatedPost.setFile(image.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            repository.save(updatedPost);
        }
    }

    /**
     * Delete a Post
     */
    public void deletePost(Long id) {
        repository.deleteById(id);
    }
}
