package com.creating.bugs.controllers;

import com.creating.bugs.domain.Category;
import com.creating.bugs.domain.Recipe;
import com.creating.bugs.domain.UnitOfMeasure;
import com.creating.bugs.repositories.CategoryRepository;
import com.creating.bugs.repositories.UnitOfMeasureRepository;
import com.creating.bugs.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * Created by steve on 12/11/17.
 */
@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeService recipeService;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeService recipeService) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "index"})
    public String getIndexPage(Model model) {

        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        Iterable<Recipe> recipeList = recipeService.getAllRecipes();

        model.addAttribute("recipeList", recipeList);
        return "index";
    }
}
