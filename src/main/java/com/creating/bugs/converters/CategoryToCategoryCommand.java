package com.creating.bugs.converters;

import com.creating.bugs.commands.CategoryCommand;
import com.creating.bugs.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by steve on 01/12/17.
 */
@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return null;
        }

        CategoryCommand convertedCategoryCommand = new CategoryCommand();
        convertedCategoryCommand.setId(source.getId());
        convertedCategoryCommand.setDescription(source.getDescription());

        return convertedCategoryCommand;
    }
}
