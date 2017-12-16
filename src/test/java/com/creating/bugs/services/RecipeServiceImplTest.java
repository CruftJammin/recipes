package com.creating.bugs.services;

import com.creating.bugs.commands.RecipeCommand;
import com.creating.bugs.converters.RecipeCommandToRecipe;
import com.creating.bugs.converters.RecipeToRecipeCommand;
import com.creating.bugs.domain.Recipe;
import com.creating.bugs.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
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

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
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

    @Test
    public void getRecipeByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void saveRecipeCommand_RecipeCommandProvided_ProvidedRecipeCommandIsConvertedAndSaved() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();

        Recipe recipeFromConverter = new Recipe();
        recipeFromConverter.setId(1L);

        when(recipeCommandToRecipe.convert(any(RecipeCommand.class))).thenReturn(recipeFromConverter);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipeFromConverter);

        recipeService.saveRecipeCommand(recipeCommand);

        verify(recipeCommandToRecipe, times(1)).convert(recipeCommand);
        verify(recipeRepository, times(1)).save(recipeFromConverter);
        verify(recipeToRecipeCommand, times(1)).convert(recipeFromConverter);
    }

    @Test
    public void testDeleteById() throws Exception {
        //given
        Long idToDelete = 2L;

        //when
        recipeService.deleteById(idToDelete);

        verify(recipeRepository, times(1)).deleteById(anyLong());
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