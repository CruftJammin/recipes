package com.creating.bugs.repositories;

import com.creating.bugs.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by steve on 21/11/17.
 */
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
