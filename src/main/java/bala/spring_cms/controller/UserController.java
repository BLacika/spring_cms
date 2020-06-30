package bala.spring_cms.controller;

import bala.spring_cms.model.User;
import bala.spring_cms.repository.RoleRepository;
import bala.spring_cms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    /***************** GET MAPPING *****************/

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUser());

        return "admin/users";
    }

    @GetMapping("/users/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());

        return "admin/add_user";
    }

    /***************** POST MAPPING *****************/

    @PostMapping("/users/add")
    public String createUser(@ModelAttribute @Valid User newUser,
                             @RequestParam("image") MultipartFile image) {

        userService.createUser(newUser, image);

        return "redirect:/admin/users";
    }
}
