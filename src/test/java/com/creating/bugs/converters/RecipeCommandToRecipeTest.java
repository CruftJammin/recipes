package com.creating.bugs.converters;

import com.creating.bugs.commands.CategoryCommand;
import com.creating.bugs.commands.IngredientCommand;
import com.creating.bugs.commands.NotesCommand;
import com.creating.bugs.commands.RecipeCommand;
import com.creating.bugs.domain.Difficulty;
import com.creating.bugs.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;

/**
 * Created by steve on 10/12/17.
 */
public class RecipeCommandToRecipeTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "recipe description";
    private static final Integer PREP_TIME = 2;
    private static final Integer SERVINGS = 3;
    private static final Integer COOK_TIME = 4;
    private static final String SOURCE = "source";
    private static final String URL = "url";
    private static final String DIRECTIONS = "directions";
    private static final Difficulty DIFFICULTY = Difficulty.MODERATE;

    private static final Long NOTES_ID = 2L;
    private static final String NOTES_RECIPE_NOTES = "recipe notes";

    private static final Long CATEGORY_1_ID = 3L;
    private static final String CATEGORY_1_DESCRIPTION = "category description";
    private static final Long CATEGORY_2_ID = 4L;
    private static final String CATEGORY_2_DESCRIPTION = "category 2 description";

    private static final Long INGREDIENT_1_ID = 5L;
    private static final String INGREDIENT_1_DESCRIPTION = "ingredient 1 description";
    private static final Long INGREDIENT_2_ID = 6L;
    private static final String INGREDIENT_2_DESCRIPTION = "ingredient 2 description";

    RecipeCommandToRecipe converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeCommandToRecipe(new NotesCommandToNotes(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new CategoryCommandToCategory());
    }

    @Test
    public void testWithNullParameter() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testWithEmptyObject() throws Exception {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void testWithNullIngredietnsAndCategoriesAndNotes() {
        //given
        RecipeCommand recipeCommandToConvert = new RecipeCommand();
        recipeCommandToConvert.setId(ID);
        recipeCommandToConvert.setDescription(DESCRIPTION);
        recipeCommandToConvert.setPrepTime(PREP_TIME);
        recipeCommandToConvert.setServings(SERVINGS);
        recipeCommandToConvert.setCookTime(COOK_TIME);
        recipeCommandToConvert.setSource(SOURCE);
        recipeCommandToConvert.setUrl(URL);
        recipeCommandToConvert.setDirections(DIRECTIONS);
        recipeCommandToConvert.setDifficulty(DIFFICULTY);

        //when
        Recipe convertedRecipe = converter.convert(recipeCommandToConvert);

        //then
        assertNotNull(convertedRecipe);
        assertEquals(ID, convertedRecipe.getId());
        assertEquals(DESCRIPTION, convertedRecipe.getDescription());
        assertEquals(PREP_TIME, convertedRecipe.getPrepTime());
        assertEquals(SERVINGS, convertedRecipe.getServings());
        assertEquals(COOK_TIME, convertedRecipe.getCookTime());
        assertEquals(SOURCE, convertedRecipe.getSource());
        assertEquals(URL, convertedRecipe.getUrl());
        assertEquals(DIRECTIONS, convertedRecipe.getDirections());
        assertEquals(DIFFICULTY, convertedRecipe.getDifficulty());

        assertNull(convertedRecipe.getNotes());
        assertTrue(convertedRecipe.getCategories().isEmpty());
        assertTrue(convertedRecipe.getIngredients().isEmpty());
    }

    @Test
    public void testWithIngrtedientsAndNotesAndCategories() {
        //given
        RecipeCommand recipeCommandToConvert = new RecipeCommand();
        recipeCommandToConvert.setId(ID);
        recipeCommandToConvert.setDescription(DESCRIPTION);
        recipeCommandToConvert.setPrepTime(PREP_TIME);
        recipeCommandToConvert.setServings(SERVINGS);
        recipeCommandToConvert.setCookTime(COOK_TIME);
        recipeCommandToConvert.setSource(SOURCE);
        recipeCommandToConvert.setUrl(URL);
        recipeCommandToConvert.setDirections(DIRECTIONS);
        recipeCommandToConvert.setDifficulty(DIFFICULTY);

        Set<IngredientCommand> setOfIngredientCommandsToConvert = new HashSet<>();
        IngredientCommand ingredient1 = new IngredientCommand();
        ingredient1.setId(INGREDIENT_1_ID);
        ingredient1.setDescription(INGREDIENT_1_DESCRIPTION);
        setOfIngredientCommandsToConvert.add(ingredient1);
        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(INGREDIENT_2_ID);
        ingredient2.setDescription(INGREDIENT_2_DESCRIPTION);
        setOfIngredientCommandsToConvert.add(ingredient2);
        recipeCommandToConvert.setIngredients(setOfIngredientCommandsToConvert);

        Set<CategoryCommand> setOfCategoryCommandsToConvert = new HashSet<>();
        CategoryCommand category1 = new CategoryCommand();
        category1.setId(CATEGORY_1_ID);
        category1.setDescription(CATEGORY_1_DESCRIPTION);
        setOfCategoryCommandsToConvert.add(category1);
        CategoryCommand category2 = new CategoryCommand();
        category2.setId(CATEGORY_2_ID);
        category2.setDescription(CATEGORY_2_DESCRIPTION);
        setOfCategoryCommandsToConvert.add(category2);
        recipeCommandToConvert.setCategories(setOfCategoryCommandsToConvert);

        NotesCommand notesCommandToConvert = new NotesCommand();
        notesCommandToConvert.setId(NOTES_ID);
        notesCommandToConvert.setRecipeNotes(NOTES_RECIPE_NOTES);
        recipeCommandToConvert.setNotes(notesCommandToConvert);

        //when
        Recipe convertedRecipe = converter.convert(recipeCommandToConvert);

        //then
        assertNotNull(convertedRecipe);
        assertEquals(ID, convertedRecipe.getId());
        assertEquals(DESCRIPTION, convertedRecipe.getDescription());
        assertEquals(PREP_TIME, convertedRecipe.getPrepTime());
        assertEquals(SERVINGS, convertedRecipe.getServings());
        assertEquals(COOK_TIME, convertedRecipe.getCookTime());
        assertEquals(SOURCE, convertedRecipe.getSource());
        assertEquals(URL, convertedRecipe.getUrl());
        assertEquals(DIRECTIONS, convertedRecipe.getDirections());
        assertEquals(DIFFICULTY, convertedRecipe.getDifficulty());

        assertNotNull(convertedRecipe.getIngredients());
        assertThat(convertedRecipe.getIngredients(), hasSize(2));

        assertNotNull(convertedRecipe.getCategories());
        assertThat(convertedRecipe.getCategories(), hasSize(2));

        assertNotNull(convertedRecipe.getNotes());
        assertEquals(NOTES_ID, convertedRecipe.getNotes().getId());
        assertEquals(NOTES_RECIPE_NOTES, convertedRecipe.getNotes().getRecipeNotes());

    }

}