package com.creating.bugs.services;

import com.creating.bugs.commands.UnitOfMeasureCommand;
import com.creating.bugs.domain.UnitOfMeasure;

import java.util.Set;

/**
 * Created by steve on 16/12/17.
 */
public interface UnitOfMeasureService {
    public Set<UnitOfMeasureCommand> listAllUnitOfMeasures();
}
