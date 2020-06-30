package bala.spring_cms.controller;

import bala.spring_cms.model.Category;
import bala.spring_cms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /*************** GET MAPPING ***************/

    @GetMapping("/categories")
    public String categories(Model model) {
        var orderedCategories = categoryService.categoriesShortedById();
        var newCategory = new Category();

        model.addAttribute("categories", orderedCategories);
        model.addAttribute("category", newCategory);

        return "admin/categories";
    }

    @GetMapping("/categories/{id}/edit")
    public String editCategory(@PathVariable("id") Long id, Model model) {
        var category = categoryService.getOne(id);

        model.addAttribute("id", category.getId());
        model.addAttribute("category", category);

        return "admin/edit_category";
    }

    @GetMapping("/categories/{id}/delete")
    public String deleteCategory(@PathVariable Long id, Model model) {

        categoryService.deleteCategory(id);

        return categories(model);
    }

    /*************** POST MAPPING ***************/

    @PostMapping("/categories")
    public String addCategory(@ModelAttribute @Valid Category category, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {

            model.addAttribute("categories", categoryService.categoriesShortedById());
            return "admin/categories";
        }

        categoryService.createCategory(category);

        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryService.categoriesShortedById());

        return "admin/categories";
    }

    @PostMapping("/categories/{id}/edit")
    public String updateCategory(@PathVariable("id") Long id,
                                 @ModelAttribute Category updatedCategory, Model model) {
        categoryService.updateCategory(id, updatedCategory);

        model.addAttribute("categories", categoryService.categoriesShortedById());
        return "admin/categories";
    }
}
