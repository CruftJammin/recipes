package com.creating.bugs.services;

import com.creating.bugs.domain.Recipe;

import java.util.ArrayList;

/**
 * Created by steve on 25/11/17.
 */
public interface RecipeService {
    Iterable<Recipe> getAllRecipes();
}
