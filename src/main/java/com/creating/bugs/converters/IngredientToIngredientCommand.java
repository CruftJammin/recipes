package com.creating.bugs.converters;

import com.creating.bugs.commands.IngredientCommand;
import com.creating.bugs.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by steve on 06/12/17.
 */
@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureConverter) {
        this.unitOfMeasureConverter = unitOfMeasureConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {
        if (source == null) {
            return null;
        }

        final IngredientCommand convertedIngredientCommand = new IngredientCommand();
        convertedIngredientCommand.setId(source.getId());
        convertedIngredientCommand.setDescription(source.getDescription());
        if (source.getRecipe() != null) {
            convertedIngredientCommand.setRecipeId(source.getRecipe().getId());
        }
        convertedIngredientCommand.setAmount(source.getAmount());
        convertedIngredientCommand.setUnitOfMeasure(unitOfMeasureConverter.convert(source.getUnitOfMeasure()));

        return convertedIngredientCommand;
    }
}
