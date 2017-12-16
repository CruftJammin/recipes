package com.creating.bugs.services;

import com.creating.bugs.commands.RecipeCommand;
import com.creating.bugs.domain.Recipe;

import java.util.Set;

/**
 * Created by steve on 25/11/17.
 */
public interface RecipeService {
    Set<Recipe> getAllRecipes();

    Recipe findById(Long l);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(Long l);

    public void deleteById(Long l);
}
