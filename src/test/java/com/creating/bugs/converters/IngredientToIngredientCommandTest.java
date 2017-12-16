package com.creating.bugs.converters;

import com.creating.bugs.commands.IngredientCommand;
import com.creating.bugs.domain.Ingredient;
import com.creating.bugs.domain.Recipe;
import com.creating.bugs.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by steve on 06/12/17.
 */
public class IngredientToIngredientCommandTest {
    private static final Long ID = 1L;
    private static final String DESCRIPTION = "ingredient description";
    private static final BigDecimal AMOUNT = new BigDecimal("1");
    private static final Long UOM_ID = 2L;
    private static final String UOM_DESCRIPTION = "unit of measure description";
    private static final Long RECIPE_ID = 3L;

    IngredientToIngredientCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    public void convertWithNullUnitOfMeasure() throws Exception {
        //given
        Ingredient ingredientToConvert = new Ingredient();
        ingredientToConvert.setId(ID);
        ingredientToConvert.setDescription(DESCRIPTION);
        ingredientToConvert.setAmount(AMOUNT);
        ingredientToConvert.setUnitOfMeasure(null);

        //when
        IngredientCommand convertedIngredientCommand = converter.convert(ingredientToConvert);

        //then
        assertNotNull(convertedIngredientCommand);
        assertEquals(ID, convertedIngredientCommand.getId());
        assertEquals(DESCRIPTION, convertedIngredientCommand.getDescription());
        assertEquals(AMOUNT, convertedIngredientCommand.getAmount());
        assertNull(convertedIngredientCommand.getUnitOfMeasure());
    }

    @Test
    public void convertWithUnitOfMeasure() throws Exception {
        //given
        Ingredient ingredientToConvert = new Ingredient();
        ingredientToConvert.setId(ID);
        ingredientToConvert.setDescription(DESCRIPTION);
        ingredientToConvert.setAmount(AMOUNT);

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOM_ID);
        unitOfMeasure.setDescription(UOM_DESCRIPTION);

        ingredientToConvert.setUnitOfMeasure(unitOfMeasure);

        //when
        IngredientCommand convertedIngredientCommand = converter.convert(ingredientToConvert);

        //then
        assertNotNull(convertedIngredientCommand);
        assertEquals(ID, convertedIngredientCommand.getId());
        assertEquals(DESCRIPTION, convertedIngredientCommand.getDescription());
        assertEquals(AMOUNT, convertedIngredientCommand.getAmount());
        assertEquals(UOM_ID, convertedIngredientCommand.getUnitOfMeasure().getId());
        assertEquals(UOM_DESCRIPTION, convertedIngredientCommand.getUnitOfMeasure().getDescription());
    }

    @Test
    public void convertWithRecipeId() throws Exception {
        //given
        Ingredient ingredientToConvert = new Ingredient();
        ingredientToConvert.setId(ID);
        ingredientToConvert.setDescription(DESCRIPTION);
        ingredientToConvert.setAmount(AMOUNT);
        ingredientToConvert.setUnitOfMeasure(null);

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        ingredientToConvert.setRecipe(recipe);

        //when
        IngredientCommand convertedIngredientCommand = converter.convert(ingredientToConvert);

        //then
        assertNotNull(convertedIngredientCommand);
        assertEquals(RECIPE_ID, convertedIngredientCommand.getRecipeId());
        assertEquals(ID, convertedIngredientCommand.getId());
        assertEquals(DESCRIPTION, convertedIngredientCommand.getDescription());
        assertEquals(AMOUNT, convertedIngredientCommand.getAmount());
        assertNull(convertedIngredientCommand.getUnitOfMeasure());
    }

}