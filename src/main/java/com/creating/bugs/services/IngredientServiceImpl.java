package com.creating.bugs.services;

import com.creating.bugs.commands.IngredientCommand;
import com.creating.bugs.converters.IngredientCommandToIngredient;
import com.creating.bugs.converters.IngredientToIngredientCommand;
import com.creating.bugs.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.creating.bugs.domain.Ingredient;
import com.creating.bugs.domain.Recipe;
import com.creating.bugs.repositories.RecipeRepository;
import com.creating.bugs.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * Created by steve on 14/12/17.
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            //todo error handling
            log.error("Recipe id is not found: '" + recipeId + "'");
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert).findFirst();

        if(!ingredientCommandOptional.isPresent()) {
            //todo error handling
            log.error("Ingredient id not found: '" + ingredientId + "'");
        }

        return ingredientCommandOptional.get();
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!recipeOptional.isPresent()) {
            //todo error handling
            log.error("Recipe id is not found: '" + ingredientCommand.getId() + "'");
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(ingredientCommand.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UNIT OF MEASURE NOT FOUND"))); //todo error handling
            } else {
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(!savedIngredientOptional.isPresent()) {
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                        .findFirst();
            }

            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }
    }

    @Override
    public IngredientCommand deleteIngredientFromRecipe(Long recipeId, Long ingredientIdToDelete) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(!recipeOptional.isPresent()) {
            //todo error handling
            log.error("Unable to find recipe with id: " + recipeId);
            return new IngredientCommand();
        }

        Recipe recipe = recipeOptional.get();

        Set<Ingredient> recipeIngredients = recipe.getIngredients();
        Optional<Ingredient> ingredientToDeleteOptional = recipeIngredients.stream()
                .filter(ingredientFromRecipe -> ingredientFromRecipe.getId().equals(ingredientIdToDelete))
                .findFirst();

        if(!ingredientToDeleteOptional.isPresent()) {
            //todo error handling
            log.error("Unable to find ingredient to delete with id: " + ingredientIdToDelete);
        } else {
            Ingredient ingredientToDelete = ingredientToDeleteOptional.get();
            ingredientToDelete.setRecipe(null);
            recipeIngredients.remove(ingredientToDelete);
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        return ingredientToIngredientCommand.convert(ingredientToDeleteOptional.get());

    }
}
