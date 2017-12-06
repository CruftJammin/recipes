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
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandConverter) {
        this.unitOfMeasureCommandConverter = unitOfMeasureCommandConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }

        Ingredient convertedIngredient = new Ingredient();
        convertedIngredient.setId(source.getId());
        convertedIngredient.setDescription(source.getDescription());
        convertedIngredient.setAmount(source.getAmount());
        convertedIngredient.setUnitOfMeasure(unitOfMeasureCommandConverter.convert(source.getUnitOfMeasure()));

        return convertedIngredient;
    }
}
