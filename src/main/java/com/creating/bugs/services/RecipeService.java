package com.creating.bugs.services;

import com.creating.bugs.domain.Recipe;

/**
 * Created by steve on 25/11/17.
 */
public interface RecipeService {
    public Iterable<Recipe> getAllRecipes();
}
