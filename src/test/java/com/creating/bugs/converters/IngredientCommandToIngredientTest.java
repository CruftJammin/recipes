package com.creating.bugs.converters;

import com.creating.bugs.commands.IngredientCommand;
import com.creating.bugs.commands.UnitOfMeasureCommand;
import com.creating.bugs.domain.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by steve on 06/12/17.
 */
public class IngredientCommandToIngredientTest {
    private static final Long ID = 1L;
    private static final String DESCRIPTION = "ingredient description";
    private static final BigDecimal AMOUNT = new BigDecimal("1");
    private static final Long UOM_ID = 2L;
    private static final String UOM_DESCRIPTION = "unit of measure description";

    IngredientCommandToIngredient converter;

    @Before
    public void setUp() throws Exception {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    public void testNullUnitOfMeasureCommand() {
        //given
        IngredientCommand ingredientCommandToConvert = new IngredientCommand();
        ingredientCommandToConvert.setId(ID);
        ingredientCommandToConvert.setDescription(DESCRIPTION);
        ingredientCommandToConvert.setAmount(AMOUNT);

        //when
        Ingredient convertedIngredient = converter.convert(ingredientCommandToConvert);

        //then
        assertNotNull(convertedIngredient);
        assertEquals(ID, convertedIngredient.getId());
        assertEquals(DESCRIPTION, convertedIngredient.getDescription());
        assertEquals(AMOUNT, convertedIngredient.getAmount());
        assertNull(convertedIngredient.getUnitOfMeasure());
    }

    @Test
    public void testWithUnitOfMeasureCommand() throws Exception {
        //given
        IngredientCommand ingredientCommandToConvert = new IngredientCommand();
        ingredientCommandToConvert.setId(ID);
        ingredientCommandToConvert.setDescription(DESCRIPTION);
        ingredientCommandToConvert.setAmount(AMOUNT);

        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        unitOfMeasureCommand.setDescription(UOM_DESCRIPTION);

        ingredientCommandToConvert.setUnitOfMeasure(unitOfMeasureCommand);

        //when
        Ingredient convertedIngredient = converter.convert(ingredientCommandToConvert);

        //then
        assertNotNull(convertedIngredient);
        assertEquals(ID, convertedIngredient.getId());
        assertEquals(DESCRIPTION, convertedIngredient.getDescription());
        assertEquals(AMOUNT, convertedIngredient.getAmount());
        assertEquals(UOM_ID, convertedIngredient.getUnitOfMeasure().getId());
        assertEquals(UOM_DESCRIPTION, convertedIngredient.getUnitOfMeasure().getDescription());
    }

}