package com.creating.bugs.converters;

import com.creating.bugs.commands.NotesCommand;
import com.creating.bugs.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by steve on 06/12/17.
 */
@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand source) {
        if (source == null) {
            return null;
        }

        Notes convertedNotes = new Notes();
        convertedNotes.setId(source.getId());
        convertedNotes.setRecipeNotes(source.getRecipeNotes());

        return convertedNotes;
    }
}
