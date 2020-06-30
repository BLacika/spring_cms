package bala.spring_cms.service;

import bala.spring_cms.model.Comment;
import bala.spring_cms.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;
    @Autowired
    private PostService postService;

    /**
     * Get All Comment
     */
    public List<Comment> getAllComment() {
        return repository.findAll();
    }

    /**
     * Get One Comment
     */
    public Comment getOne(Long id) {
        var comment = repository.findById(id);
        return comment.get();
    }

    /**
     * Create Comment
     */
    public void createComment(Comment newComment) {
        newComment.setDate(new Date());

        repository.save(newComment);
    }

    /**
     * Update Comment
     */
    public void updateComment(Long id, Comment updatedComment) {
        repository.findById(id).stream()
                .map(comment -> {
                    comment.setAuthor(updatedComment.getAuthor());
                    comment.setEmail(updatedComment.getEmail());
                    comment.setContent(updatedComment.getContent());
                    comment.setStatus(updatedComment.getStatus());
                    comment.setDate(new Date());
                    comment.setPost(updatedComment.getPost());

                    return repository.save(comment);
                });
    }

    /**
     * Delete Comment
     */
    public void deleteComment(Long id) {
        repository.deleteById(id);
    }
}
