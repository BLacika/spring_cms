package bala.spring_cms.service;

import bala.spring_cms.model.Category;
import bala.spring_cms.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    /**
     * Get all categories shorted by id.
     * @return List of Category
     */
    public List<Category> categoriesShortedById() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    /**
     * Get all Categories shorted by name.
     */
    public List<Category> categoriesShortedByName() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "title"));
    }

    /**
     * Create a new Category
     * @param category
     */
    public void createCategory(Category category) {
        category.setCreatedAt(new Date());
        category.setUpdatedAt(new Date());

        repository.save(category);
    }

    /**
     * Get a category by id
     * @param id
     * @return Category
     */
    public Category getOne(Long id) {
        var category = repository.findById(id);
        return category.get();
    }

    /**
     * Update a category with id
     * @param id
     * @param categoryToUpdate
     */
    public void updateCategory(Long id, Category categoryToUpdate) {
        repository.findById(id)
                .map(category -> {
                    category.setTitle(categoryToUpdate.getTitle());
                    category.setUpdatedAt(new Date());
                    return repository.save(category);
                });
    }

    /**
     * Deleting a Category
     * @param id
     */
    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }
}
