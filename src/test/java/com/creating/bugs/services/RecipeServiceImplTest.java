package com.creating.bugs.services;

import com.creating.bugs.domain.Recipe;
import com.creating.bugs.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by steve on 27/11/17.
 */
public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getAllRecipes_NoRecipesExist_ShouldReturnEmpty() throws Exception {
        Iterable<Recipe> recipes = recipeService.getAllRecipes();

        assertEquals(false, recipes.iterator().hasNext());
    }

    @Test
    public void getAllRecipes_ManyRecipesExists_ShouldCallRepositoryOnce() throws Exception {
        Set<Recipe> recipeData = getSetOfManyRecipes();

        when(recipeService.getAllRecipes()).thenReturn(recipeData);

        Set<Recipe> recipes = recipeService.getAllRecipes();

        assertEquals(3, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void getAllRecipes_OneRecipeExists_ShouldReturnOneRecipe() throws Exception {
        Recipe recipe = new Recipe();

        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe));

        Set<Recipe> recipes = recipeService.getAllRecipes();
        assertEquals(1, recipes.size());
        assertTrue(recipes.contains(recipe));
    }

    @Test
    public void getAllRecipes_ManyRecipesExist_ShouldReturnAllRecipes() throws Exception {
        Set<Recipe> recipeData = getSetOfManyRecipes();

        when(recipeRepository.findAll()).thenReturn(new ArrayList<>(recipeData));

        Set<Recipe> recipes = recipeService.getAllRecipes();
        assertEquals(3, recipes.size());
        assertTrue(recipes.containsAll(recipeData));
    }

    private Set<Recipe> getSetOfManyRecipes() {
        Recipe recipe1 = new Recipe();
        recipe1.setDescription("recipe1");
        Recipe recipe2 = new Recipe();
        recipe2.setDescription("recipe2");
        Recipe recipe3 = new Recipe();
        recipe3.setDescription("recipe3");

        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe1);
        recipeData.add(recipe2);
        recipeData.add(recipe3);

        return recipeData;
    }

}