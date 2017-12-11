package com.creating.bugs.converters;

import com.creating.bugs.commands.RecipeCommand;
import com.creating.bugs.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by steve on 09/12/17.
 */
public class RecipeToRecipeCommandTest {

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


    RecipeToRecipeCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeToRecipeCommand(new NotesToNotesCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new CategoryToCategoryCommand());
    }

    @Test
    public void testWithNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testWithEmptyObject() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    public void testWithNullIngredientsAndCategoriesAndNotes() {
        //given
        Recipe recipeToConvert = new Recipe();
        recipeToConvert.setId(ID);
        recipeToConvert.setDescription(DESCRIPTION);
        recipeToConvert.setPrepTime(PREP_TIME);
        recipeToConvert.setServings(SERVINGS);
        recipeToConvert.setCookTime(COOK_TIME);
        recipeToConvert.setSource(SOURCE);
        recipeToConvert.setUrl(URL);
        recipeToConvert.setDirections(DIRECTIONS);
        recipeToConvert.setDifficulty(DIFFICULTY);

        //when
        RecipeCommand convertedRecipeCommand = converter.convert(recipeToConvert);

        //then
        assertNotNull(convertedRecipeCommand);
        assertEquals(ID, convertedRecipeCommand.getId());
        assertEquals(DESCRIPTION, convertedRecipeCommand.getDescription());
        assertEquals(PREP_TIME, convertedRecipeCommand.getPrepTime());
        assertEquals(SERVINGS, convertedRecipeCommand.getServings());
        assertEquals(COOK_TIME, convertedRecipeCommand.getCookTime());
        assertEquals(SOURCE, convertedRecipeCommand.getSource());
        assertEquals(URL, convertedRecipeCommand.getUrl());
        assertEquals(DIRECTIONS, convertedRecipeCommand.getDirections());
        assertEquals(DIFFICULTY, convertedRecipeCommand.getDifficulty());

        assertNull(convertedRecipeCommand.getNotes());
        assertTrue(convertedRecipeCommand.getCategories().isEmpty());
        assertTrue(convertedRecipeCommand.getIngredients().isEmpty());
    }

    @Test
    public void testWithNotesButNullIngredientsAndCategories() {
        //given
        Recipe recipeToConvert = new Recipe();
        recipeToConvert.setId(ID);
        recipeToConvert.setDescription(DESCRIPTION);
        recipeToConvert.setPrepTime(PREP_TIME);
        recipeToConvert.setServings(SERVINGS);
        recipeToConvert.setCookTime(COOK_TIME);
        recipeToConvert.setSource(SOURCE);
        recipeToConvert.setUrl(URL);
        recipeToConvert.setDirections(DIRECTIONS);
        recipeToConvert.setDifficulty(DIFFICULTY);

        Notes recipeNotesToConvert = new Notes();
        recipeNotesToConvert.setId(NOTES_ID);
        recipeNotesToConvert.setRecipeNotes(NOTES_RECIPE_NOTES);

        recipeToConvert.setNotes(recipeNotesToConvert);

        //when
        RecipeCommand convertedRecipeCommand = converter.convert(recipeToConvert);

        //then
        assertNotNull(convertedRecipeCommand);
        assertEquals(ID, convertedRecipeCommand.getId());
        assertEquals(DESCRIPTION, convertedRecipeCommand.getDescription());
        assertEquals(PREP_TIME, convertedRecipeCommand.getPrepTime());
        assertEquals(SERVINGS, convertedRecipeCommand.getServings());
        assertEquals(COOK_TIME, convertedRecipeCommand.getCookTime());
        assertEquals(SOURCE, convertedRecipeCommand.getSource());
        assertEquals(URL, convertedRecipeCommand.getUrl());
        assertEquals(DIRECTIONS, convertedRecipeCommand.getDirections());
        assertEquals(DIFFICULTY, convertedRecipeCommand.getDifficulty());

        assertNotNull(convertedRecipeCommand.getNotes());
        assertEquals(NOTES_ID, convertedRecipeCommand.getNotes().getId());
        assertEquals(NOTES_RECIPE_NOTES, convertedRecipeCommand.getNotes().getRecipeNotes());

        assertTrue(convertedRecipeCommand.getIngredients().isEmpty());
        assertTrue(convertedRecipeCommand.getCategories().isEmpty());
    }

    @Test
    public void testWithCategoriesButNullNotesAndIngredients() {
        //given
        Recipe recipeToConvert = new Recipe();
        recipeToConvert.setId(ID);
        recipeToConvert.setDescription(DESCRIPTION);
        recipeToConvert.setPrepTime(PREP_TIME);
        recipeToConvert.setServings(SERVINGS);
        recipeToConvert.setCookTime(COOK_TIME);
        recipeToConvert.setSource(SOURCE);
        recipeToConvert.setUrl(URL);
        recipeToConvert.setDirections(DIRECTIONS);
        recipeToConvert.setDifficulty(DIFFICULTY);

        Set<Category> setOfCategoriesToConvert = new HashSet<>();
        Category categoryToConvert = new Category();
        categoryToConvert.setId(CATEGORY_1_ID);
        categoryToConvert.setDescription(CATEGORY_1_DESCRIPTION);
        setOfCategoriesToConvert.add(categoryToConvert);
        Category categoryToConvert2 = new Category();
        categoryToConvert2.setId(CATEGORY_2_ID);
        categoryToConvert2.setDescription(CATEGORY_2_DESCRIPTION);
        setOfCategoriesToConvert.add(categoryToConvert2);

        recipeToConvert.setCategories(setOfCategoriesToConvert);

        //when
        RecipeCommand convertedRecipeCommand = converter.convert(recipeToConvert);

        //then
        assertNotNull(convertedRecipeCommand);
        assertEquals(ID, convertedRecipeCommand.getId());
        assertEquals(DESCRIPTION, convertedRecipeCommand.getDescription());
        assertEquals(PREP_TIME, convertedRecipeCommand.getPrepTime());
        assertEquals(SERVINGS, convertedRecipeCommand.getServings());
        assertEquals(COOK_TIME, convertedRecipeCommand.getCookTime());
        assertEquals(SOURCE, convertedRecipeCommand.getSource());
        assertEquals(URL, convertedRecipeCommand.getUrl());
        assertEquals(DIRECTIONS, convertedRecipeCommand.getDirections());
        assertEquals(DIFFICULTY, convertedRecipeCommand.getDifficulty());

        assertNotNull(convertedRecipeCommand.getCategories());
        assertThat(convertedRecipeCommand.getCategories(), hasSize(2));

        assertTrue(convertedRecipeCommand.getIngredients().isEmpty());
        assertNull(convertedRecipeCommand.getNotes());
    }

    @Test
    public void testWithIngredientsButNullCategoriesAndNotes() {
        //given
        Recipe recipeToConvert = new Recipe();
        recipeToConvert.setId(ID);
        recipeToConvert.setDescription(DESCRIPTION);
        recipeToConvert.setPrepTime(PREP_TIME);
        recipeToConvert.setServings(SERVINGS);
        recipeToConvert.setCookTime(COOK_TIME);
        recipeToConvert.setSource(SOURCE);
        recipeToConvert.setUrl(URL);
        recipeToConvert.setDirections(DIRECTIONS);
        recipeToConvert.setDifficulty(DIFFICULTY);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGREDIENT_1_ID);
        ingredient1.setDescription(INGREDIENT_1_DESCRIPTION);
        recipeToConvert.addIngredient(ingredient1);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGREDIENT_2_ID);
        ingredient2.setDescription(INGREDIENT_2_DESCRIPTION);
        recipeToConvert.addIngredient(ingredient2);

        //when
        RecipeCommand convertedRecipeCommand = converter.convert(recipeToConvert);

        //then
        assertNotNull(convertedRecipeCommand);
        assertEquals(ID, convertedRecipeCommand.getId());
        assertEquals(DESCRIPTION, convertedRecipeCommand.getDescription());
        assertEquals(PREP_TIME, convertedRecipeCommand.getPrepTime());
        assertEquals(SERVINGS, convertedRecipeCommand.getServings());
        assertEquals(COOK_TIME, convertedRecipeCommand.getCookTime());
        assertEquals(SOURCE, convertedRecipeCommand.getSource());
        assertEquals(URL, convertedRecipeCommand.getUrl());
        assertEquals(DIRECTIONS, convertedRecipeCommand.getDirections());
        assertEquals(DIFFICULTY, convertedRecipeCommand.getDifficulty());

        assertNotNull(convertedRecipeCommand.getIngredients());
        assertThat(convertedRecipeCommand.getIngredients(), hasSize(2));

        assertNull(convertedRecipeCommand.getNotes());
        assertTrue(convertedRecipeCommand.getCategories().isEmpty());
    }

    @Test
    public void testWithIngredientsAndNotesAndCategories() {
        //given
        Recipe recipeToConvert = new Recipe();
        recipeToConvert.setId(ID);
        recipeToConvert.setDescription(DESCRIPTION);
        recipeToConvert.setPrepTime(PREP_TIME);
        recipeToConvert.setServings(SERVINGS);
        recipeToConvert.setCookTime(COOK_TIME);
        recipeToConvert.setSource(SOURCE);
        recipeToConvert.setUrl(URL);
        recipeToConvert.setDirections(DIRECTIONS);
        recipeToConvert.setDifficulty(DIFFICULTY);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGREDIENT_1_ID);
        ingredient1.setDescription(INGREDIENT_1_DESCRIPTION);
        recipeToConvert.addIngredient(ingredient1);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGREDIENT_2_ID);
        ingredient2.setDescription(INGREDIENT_2_DESCRIPTION);
        recipeToConvert.addIngredient(ingredient2);

        Set<Category> setOfCategoriesToConvert = new HashSet<>();
        Category categoryToConvert = new Category();
        categoryToConvert.setId(CATEGORY_1_ID);
        categoryToConvert.setDescription(CATEGORY_1_DESCRIPTION);
        setOfCategoriesToConvert.add(categoryToConvert);
        Category categoryToConvert2 = new Category();
        categoryToConvert2.setId(CATEGORY_2_ID);
        categoryToConvert2.setDescription(CATEGORY_2_DESCRIPTION);
        setOfCategoriesToConvert.add(categoryToConvert2);

        recipeToConvert.setCategories(setOfCategoriesToConvert);

        Notes recipeNotesToConvert = new Notes();
        recipeNotesToConvert.setId(NOTES_ID);
        recipeNotesToConvert.setRecipeNotes(NOTES_RECIPE_NOTES);

        recipeToConvert.setNotes(recipeNotesToConvert);

        //when
        RecipeCommand convertedRecipeCommand = converter.convert(recipeToConvert);

        //then
        assertNotNull(convertedRecipeCommand);
        assertEquals(ID, convertedRecipeCommand.getId());
        assertEquals(DESCRIPTION, convertedRecipeCommand.getDescription());
        assertEquals(PREP_TIME, convertedRecipeCommand.getPrepTime());
        assertEquals(SERVINGS, convertedRecipeCommand.getServings());
        assertEquals(COOK_TIME, convertedRecipeCommand.getCookTime());
        assertEquals(SOURCE, convertedRecipeCommand.getSource());
        assertEquals(URL, convertedRecipeCommand.getUrl());
        assertEquals(DIRECTIONS, convertedRecipeCommand.getDirections());
        assertEquals(DIFFICULTY, convertedRecipeCommand.getDifficulty());

        assertNotNull(convertedRecipeCommand.getIngredients());
        assertThat(convertedRecipeCommand.getIngredients(), hasSize(2));

        assertNotNull(convertedRecipeCommand.getCategories());
        assertThat(convertedRecipeCommand.getCategories(), hasSize(2));

        assertNotNull(convertedRecipeCommand.getNotes());
        assertEquals(NOTES_ID, convertedRecipeCommand.getNotes().getId());
        assertEquals(NOTES_RECIPE_NOTES, convertedRecipeCommand.getNotes().getRecipeNotes());

    }

}