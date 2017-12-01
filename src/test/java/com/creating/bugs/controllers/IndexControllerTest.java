package com.creating.bugs.controllers;

import com.creating.bugs.domain.Recipe;
import com.creating.bugs.repositories.CategoryRepository;
import com.creating.bugs.repositories.UnitOfMeasureRepository;
import com.creating.bugs.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by steve on 27/11/17.
 */
public class IndexControllerTest {

    IndexController indexController;

    @Mock
    Model model;

    @Mock
    RecipeService recipeService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(categoryRepository, unitOfMeasureRepository, recipeService);
    }

    @Test
    public void getIndexPage_RecipeServiceExists_GetsCalledOnce() throws Exception {
        indexController.getIndexPage(model);

        verify(recipeService, times(1)).getAllRecipes();
    }

    @Test
    public void getIndexPage_RecipeListExists_AddsToModelOnce() throws Exception {
        indexController.getIndexPage(model);

        verify(model, times(1)).addAttribute(eq("recipeList"), anySet());
    }

    @Test
    public void getIndexPage_RecipeListFromRecipeService_ReturnedRecipeListAddedToModel() throws Exception {
        Set<Recipe> recipeList = new HashSet<>();
        Recipe recipe = new Recipe();
        recipe.setDescription("description");
        recipeList.add(recipe);

        when(recipeService.getAllRecipes()).thenReturn(recipeList);

        indexController.getIndexPage(model);

        verify(model, times(1)).addAttribute(eq("recipeList"), eq(recipeList));
    }

    @Test
    public void getIndexPage_SomeOtherTest() throws Exception {
        //given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());

        Recipe recipe = new Recipe();
        recipe.setDescription("test");
        recipes.add(recipe);

        when(recipeService.getAllRecipes()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);


        //when
        String viewName = indexController.getIndexPage(model);

        //then
        assertEquals("index", viewName);
        verify(recipeService, times(1)).getAllRecipes();
        verify(model, times(1)).addAttribute(eq("recipeList"), argumentCaptor.capture());
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }

    @Test
    public void getIndexPage_ModelReturned_ValueShouldEqualIndex() throws Exception {
        assertEquals("index", indexController.getIndexPage(model));
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}