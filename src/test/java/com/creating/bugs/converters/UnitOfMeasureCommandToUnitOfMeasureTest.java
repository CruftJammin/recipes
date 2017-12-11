package com.creating.bugs.converters;

import com.creating.bugs.commands.UnitOfMeasureCommand;
import com.creating.bugs.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by steve on 01/12/17.
 */
public class UnitOfMeasureCommandToUnitOfMeasureTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = 1L;

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullParameter() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(LONG_VALUE);
        command.setDescription(DESCRIPTION);

        //when
        UnitOfMeasure convertedUnitOfMeasure = converter.convert(command);

        //then
        assertNotNull(convertedUnitOfMeasure);
        assertEquals(LONG_VALUE, convertedUnitOfMeasure.getId());
        assertEquals(DESCRIPTION, convertedUnitOfMeasure.getDescription());
    }

}