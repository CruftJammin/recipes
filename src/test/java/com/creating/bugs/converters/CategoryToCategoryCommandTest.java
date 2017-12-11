package com.creating.bugs.converters;

import com.creating.bugs.commands.CategoryCommand;
import com.creating.bugs.domain.Category;
import com.creating.bugs.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by steve on 01/12/17.
 */
public class CategoryToCategoryCommandTest {
    private static final Long ID = 1L;
    private static final String DESCRIPTION = "description";

    CategoryToCategoryCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyParameter() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    public void convert() throws Exception {
        //given
        Category category = new Category();
        category.setId(ID);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand convertedCategoryCommand = converter.convert(category);

        //then
        assertNotNull(convertedCategoryCommand);
        assertEquals(DESCRIPTION, convertedCategoryCommand.getDescription());
        assertEquals(ID, convertedCategoryCommand.getId());
    }

}