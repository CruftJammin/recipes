package com.creating.bugs.services;

import com.creating.bugs.commands.IngredientCommand;

/**
 * Created by steve on 14/12/17.
 */
public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
