package com.creating.bugs.converters;

import com.creating.bugs.commands.NotesCommand;
import com.creating.bugs.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by steve on 06/12/17.
 */
public class NotesCommandToNotesTest {

    NotesCommandToNotes converter;

    private static final Long ID = 1L;
    private static final String RECIPE_NOTES = "here be notes";

    @Before
    public void setUp() throws Exception {
        converter = new NotesCommandToNotes();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        NotesCommand notesCommandToConvert = new NotesCommand();
        notesCommandToConvert.setId(ID);
        notesCommandToConvert.setRecipeNotes(RECIPE_NOTES);

        //when
        Notes convertedNotes = converter.convert(notesCommandToConvert);

        //then
        assertNotNull(convertedNotes);
        assertEquals(ID, convertedNotes.getId());
        assertEquals(RECIPE_NOTES, convertedNotes.getRecipeNotes());
    }

}