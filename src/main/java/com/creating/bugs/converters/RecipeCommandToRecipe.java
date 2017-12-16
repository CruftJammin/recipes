package com.creating.bugs.converters;

import com.creating.bugs.commands.RecipeCommand;
import com.creating.bugs.domain.Category;
import com.creating.bugs.domain.Ingredient;
import com.creating.bugs.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by steve on 10/12/17.
 */
@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private NotesCommandToNotes notesCommandToNotes;
    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private CategoryCommandToCategory categoryCommandToCategory;

    public RecipeCommandToRecipe(NotesCommandToNotes notesCommandToNotes,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 CategoryCommandToCategory categoryCommandToCategory) {
        this.notesCommandToNotes = notesCommandToNotes;
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(@Nullable RecipeCommand source) {
        if (source == null) {
            return null;
        }
        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setServings(source.getServings());
        recipe.setCookTime(source.getCookTime());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setDirections(source.getDirections());
        recipe.setDifficulty(source.getDifficulty());

        recipe.setNotes(notesCommandToNotes.convert(source.getNotes()));

        Set<Ingredient> ingredients = new HashSet<>();
        source.getIngredients().forEach(ingredientCommand -> ingredients.add(ingredientCommandToIngredient.convert(ingredientCommand)));
        recipe.setIngredients(ingredients);

        Set<Category> categories = new HashSet<>();
        source.getCategories().forEach(categoryCommand -> categories.add(categoryCommandToCategory.convert(categoryCommand)));
        recipe.setCategories(categories);

        return recipe;
    }
}
