package bala.spring_cms.controller;

import bala.spring_cms.model.Comment;
import bala.spring_cms.service.CategoryService;
import bala.spring_cms.service.CommentService;
import bala.spring_cms.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class BlogController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    /*************** GET MAPPING ***************/

    @GetMapping("")
    public String index(Model model) {

        showAllCategories(model);
        showAllPost(model);

        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/posts")
    public String showPostByCategory(@RequestParam("cat_id") Long id, Model model) {
        var filteredPosts = postService.getAllPost().stream()
                .filter(post -> post.getCategory().getId().equals(id))
                .collect(Collectors.toList());

        model.addAttribute("posts", filteredPosts);
        showAllCategories(model);

        return "index";
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable("id") Long id, Model model) {
        var post = postService.getOne(id);
        var comments = commentService.getAllComment().stream()
                .filter(comment -> comment.getPost().getId().equals(id))
                .collect(Collectors.toList());

        // Adding new blank comment for placeholder
        var newComment = new Comment();
        newComment.setPost(post);
        newComment.setStatus("unapproved");

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("comment", newComment);

        showAllCategories(model);

        return "post";
    }

    /*************** POST MAPPING ***************/

    @PostMapping("/")
    public String searchPost(@RequestParam(value = "tags", required = false) String tags, Model model) {

        var filteredPosts = postService.getAllPost().stream()
                .filter(post -> post.getTags().contains(tags))
                .collect(Collectors.toList());

        model.addAttribute("posts", filteredPosts);
        showAllCategories(model);

        return "index";
    }

    @PostMapping("/post/add_comment")
    public String addComment(@ModelAttribute Comment comment) {

       commentService.createComment(comment);

        return "redirect:/post/" + comment.getPost().getId();
    }

    /*************** OTHER METHODS ***************/

    private void showAllPost(Model model) {
        var posts = postService.getAllPostShorted();

        model.addAttribute("posts", posts);
    }

    private void showAllCategories(Model model) {
        var orderedCategories = categoryService.categoriesShortedByName();

        model.addAttribute("categories", orderedCategories);
    }
}
