package com.creating.bugs.converters;

import com.creating.bugs.commands.NotesCommand;
import com.creating.bugs.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by steve on 06/12/17.
 */
public class NotesToNotesCommandTest {

    NotesToNotesCommand converter;

    private static final Long ID = 1L;
    private static final String RECIPE_NOTES = "here are some notes";

    @Before
    public void setUp() throws Exception {
        converter = new NotesToNotesCommand();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    public void convert() throws Exception {
        //given
        Notes notes = new Notes();
        notes.setId(ID);
        notes.setRecipeNotes(RECIPE_NOTES);

        //when
        NotesCommand convertedNotesCommand = converter.convert(notes);

        //then
        assertNotNull(convertedNotesCommand);
        assertEquals(ID, convertedNotesCommand.getId());
        assertEquals(RECIPE_NOTES, convertedNotesCommand.getRecipeNotes());
    }

}