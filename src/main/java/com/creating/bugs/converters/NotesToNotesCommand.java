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
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {

        if (source == null) {
            return null;
        }

        final NotesCommand convertedNotesCommand = new NotesCommand();
        convertedNotesCommand.setId(source.getId());
        convertedNotesCommand.setRecipeNotes(source.getRecipeNotes());

        return convertedNotesCommand;
    }
}
