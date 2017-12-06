package com.creating.bugs.converters;

import com.creating.bugs.commands.UnitOfMeasureCommand;
import com.creating.bugs.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by steve on 01/12/17.
 */
public class UnitOfMeasureToUnitOfMeasureCommandTest {

    private static final String DESCRIPTION = "description";
    private static final Long ID = 1L;

    private UnitOfMeasureToUnitOfMeasureCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullParamter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() {
        //given
        UnitOfMeasure command = new UnitOfMeasure();
        command.setDescription(DESCRIPTION);
        command.setId(ID);

        //when
        UnitOfMeasureCommand convertedUnitOfMeasureCommand = converter.convert(command);

        //then
        assertNotNull(convertedUnitOfMeasureCommand);
        assertEquals(DESCRIPTION, convertedUnitOfMeasureCommand.getDescription());
        assertEquals(ID, convertedUnitOfMeasureCommand.getId());
    }

}