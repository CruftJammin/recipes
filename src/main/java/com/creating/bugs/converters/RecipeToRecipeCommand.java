package com.creating.bugs.converters;

import com.creating.bugs.commands.CategoryCommand;
import com.creating.bugs.commands.IngredientCommand;
import com.creating.bugs.commands.RecipeCommand;
import com.creating.bugs.domain.Ingredient;
import com.creating.bugs.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by steve on 09/12/17.
 */
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private NotesToNotesCommand notesToNotesCommand;
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private CategoryToCategoryCommand categoryToCategoryCommand;

    public RecipeToRecipeCommand(NotesToNotesCommand notesToNotesCommand,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 CategoryToCategoryCommand categoryToCategoryCommand) {
        this.notesToNotesCommand = notesToNotesCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(@Nullable Recipe source) {
        if (source == null) {
            return null;
        }
        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setDifficulty(source.getDifficulty());

        recipeCommand.setNotes(notesToNotesCommand.convert(source.getNotes()));

        Set<IngredientCommand> ingredients = new HashSet<>();
        source.getIngredients().forEach(ingredient -> ingredients.add(ingredientToIngredientCommand.convert(ingredient)));
        recipeCommand.setIngredients(ingredients);

        Set<CategoryCommand> categories = new HashSet<>();
        source.getCategories().forEach(category -> categories.add(categoryToCategoryCommand.convert(category)));
        recipeCommand.setCategories(categories);

        return recipeCommand;
    }

}
