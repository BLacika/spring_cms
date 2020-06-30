package bala.spring_cms.controller;

import bala.spring_cms.model.Role;
import bala.spring_cms.model.User;
import bala.spring_cms.repository.RoleRepository;
import bala.spring_cms.service.SecurityService;
import bala.spring_cms.service.UserService;
import bala.spring_cms.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.HashSet;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User newUser) {
        var role = roleRepository.findByName("USER");
        newUser.getRoles().add(role);

        userService.createUser(newUser, null);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
}
