package bala.spring_cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Date;

@Controller
@RequestMapping("/admin/**")
public class AdminController {

    @GetMapping("")
    public String admin(Model model) {

        model.addAttribute("standardDate", new Date());

        return "admin/index";
    }
}
