package com.creating.bugs.services;

import com.creating.bugs.domain.Recipe;
import com.creating.bugs.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steve on 25/11/17.
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Iterable<Recipe> getAllRecipes() {
        log.debug("Fetching all recipes from repository");
        return recipeRepository.findAll();
    }
}
