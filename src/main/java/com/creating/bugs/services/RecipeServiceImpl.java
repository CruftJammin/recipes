package com.creating.bugs.services;

import com.creating.bugs.domain.Recipe;
import com.creating.bugs.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

/**
 * Created by steve on 25/11/17.
 */
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Iterable<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }
}
