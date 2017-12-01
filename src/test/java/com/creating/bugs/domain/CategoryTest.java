package com.creating.bugs.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by steve on 27/11/17.
 */
public class CategoryTest {

    Category category;

    @Before
    public void setUp() {
        category = new Category();
    }

    @Test
    public void getId() throws Exception {
        Long idValue = 4L;

        category.setId(idValue);

        assertEquals(idValue, category.getId());
    }

    @Test
    public void getDescription() throws Exception {
        String description = "this is my description";

        category.setDescription(description);

        assertEquals(description, category.getDescription());
    }

    @Test
    public void getRecipes() throws Exception {

    }

}