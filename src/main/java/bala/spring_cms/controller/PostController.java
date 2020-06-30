package bala.spring_cms.controller;

import bala.spring_cms.model.Post;
import bala.spring_cms.service.CategoryService;
import bala.spring_cms.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private CategoryService categoryService;

    /***************** GET MAPPING *****************/

    @GetMapping("/posts")
    public String posts(Model model) {
        var posts = postService.getAllPost();

        model.addAttribute("posts", posts);

        return "admin/posts";
    }

    @GetMapping("/posts/add")
    public String getPostForm(Model model) {
        var categories = categoryService.categoriesShortedByName();

        model.addAttribute("categories", categories);

        return "admin/add_post";
    }

    @GetMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable Long id, Model model) {
        postService.deletePost(id);

        return posts(model);
    }

    @GetMapping("/posts/{id}/edit")
    public String getEditPostForm(@PathVariable("id") Long id, Model model) {
        var post = postService.getOne(id);
        var categories = categoryService.categoriesShortedByName();

        model.addAttribute("post", post);
        model.addAttribute("categories", categories);

        return "admin/edit_post";
    }

    /***************** POST MAPPING *****************/

    @PostMapping("/posts/add")
    public String addPost(@ModelAttribute Post post, @RequestParam("image") MultipartFile image, Model model) {
        postService.createPost(post, image);

        return posts(model);
    }

    @PostMapping("/posts")
    public String updatePost(@RequestParam("id") Long id,
                             @ModelAttribute Post post,
                             @RequestParam("image") MultipartFile image,
                             Model model) {
        postService.updatePost(id, post, image);

        model.addAttribute("posts", postService.getAllPost());
        return "admin/posts";
    }
}
