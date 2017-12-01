package com.creating.bugs.bootstrap;

import com.creating.bugs.domain.*;
import com.creating.bugs.repositories.CategoryRepository;
import com.creating.bugs.repositories.RecipeRepository;
import com.creating.bugs.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by steve on 21/11/17.
 */
@Slf4j
@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private static final String EXPECTED_UNIT_OF_MEASURE_NOT_FOUND = "Expected unit of measure was not found!";

    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public DevBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("Running bootstrap code to initialize data");
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {
        log.debug("Building recipe list");
        List<Recipe> listOfRecipes = new ArrayList<>();

        log.debug("Getting categories");
        Optional<Category> optionalMexicanCategory = categoryRepository.findByDescription("Mexican");
        if (!optionalMexicanCategory.isPresent()) {
            throw new RuntimeException("Expected category was not found!");
        }
        Category mexicanCategory = optionalMexicanCategory.get();

        log.debug("Getting units of measurement");
        Optional<UnitOfMeasure> optionalTablespoon = unitOfMeasureRepository.findByDescription("Tablespoon");
        if (!optionalTablespoon.isPresent()) {
            throw new RuntimeException(EXPECTED_UNIT_OF_MEASURE_NOT_FOUND);
        }

        Optional<UnitOfMeasure> optionalTeaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");
        if (!optionalTeaspoon.isPresent()) {
            throw new RuntimeException(EXPECTED_UNIT_OF_MEASURE_NOT_FOUND);
        }

        Optional<UnitOfMeasure> optionalWhole = unitOfMeasureRepository.findByDescription("Whole");
        if (!optionalWhole.isPresent()) {
            throw new RuntimeException(EXPECTED_UNIT_OF_MEASURE_NOT_FOUND);
        }

        UnitOfMeasure tablespoon = optionalTablespoon.get();
        UnitOfMeasure teaspoon = optionalTeaspoon.get();
        UnitOfMeasure whole = optionalWhole.get();

        log.debug("Creating perfect guacamole recipe");
        Recipe perfectGuacamole = new Recipe();
        perfectGuacamole.setCookTime(0);
        perfectGuacamole.setDescription("Guacamole, a dip made from avocados, is originally from Mexico. The name is derived from two Aztec Nahuatl words—ahuacatl (avocado) and molli (sauce).\n" +
                "\n" +
                "All you really need to make guacamole is ripe avocados and salt. After that, a little lime or lemon juice—a splash of acidity to balance the richness of the avocado. Then comes chopped cilantro, chiles, onion, and tomato, if you want.\n" +
                "\n" +
                "The trick to making perfect guacamole is using good, ripe avocados. Check for ripeness by gently pressing the outside of the avocado. If there is no give, the avocado is not ripe yet and will not taste good. If there is a little give, the avocado is ripe. If there is a lot of give, the avocado may be past ripe and not good. In this case, taste test first before using.");
        perfectGuacamole.setDifficulty(Difficulty.EASY);
        perfectGuacamole.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");
        perfectGuacamole.setPrepTime(10);
        perfectGuacamole.setServings(4);
        perfectGuacamole.setSource("www.simplyrecipes.com");
        perfectGuacamole.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");

        Notes perfectGuacamoleNotes = new Notes();
        perfectGuacamoleNotes.setRecipeNotes("Variations\n" +
                "For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.");
        perfectGuacamole.setNotes(perfectGuacamoleNotes);

        perfectGuacamole.addIngredient(new Ingredient("Avocado", new BigDecimal(2), whole));
        perfectGuacamole.addIngredient(new Ingredient("Kosher Salt", new BigDecimal(0.5), teaspoon));

        perfectGuacamole.getCategories().add(mexicanCategory);
        listOfRecipes.add(perfectGuacamole);

        log.debug("Creating spicy chicken tacos recipe");
        Recipe spicyChickenTacos = new Recipe();
        spicyChickenTacos.setPrepTime(20);
        spicyChickenTacos.setCookTime(15);
        spicyChickenTacos.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        spicyChickenTacos.setSource("http://www.simplyrecipes.com");
        spicyChickenTacos.setDescription("Spicy grilled chicken tacos! Quick marinade, then grill. Ready in about 30 minutes. Great for a quick weeknight dinner, backyard cookouts, and tailgate parties.\n" +
                "Jump to Recipe\n" +
                "Spicy Grilled Chicken Tacos\n" +
                "Photography Credit: Sally Vargas\n" +
                "We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "\n" +
                "\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "Spicy Grilled Chicken TacosThe ancho chiles I use in the marinade are named for their wide shape. They are large, have a deep reddish brown color when dried, and are mild in flavor with just a hint of heat. You can find ancho chile powder at any markets that sell Mexican ingredients, or online.\n" +
                "\n" +
                "I like to put all the toppings in little bowls on a big platter at the center of the table: avocados, radishes, tomatoes, red onions, wedges of lime, and a sour cream sauce. I add arugula, as well – this green isn’t traditional for tacos, but we always seem to have some in the fridge and I think it adds a nice green crunch to the tacos.\n" +
                "\n" +
                "Everyone can grab a warm tortilla from the pile and make their own tacos just they way they like them.\n" +
                "\n" +
                "You could also easily double or even triple this recipe for a larger party. A taco and a cold beer on a warm day? Now that’s living!");
        spicyChickenTacos.setServings(4);
        spicyChickenTacos.setDifficulty(Difficulty.MODERATE);
        spicyChickenTacos.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "Spicy Grilled Chicken Tacos\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n");


        Notes spicyChickenTacosNotes = new Notes();
        spicyChickenTacosNotes.setRecipeNotes("");
        spicyChickenTacosNotes.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");
        spicyChickenTacos.setNotes(spicyChickenTacosNotes);

        spicyChickenTacos.addIngredient(new Ingredient("Ancho chili powder", new BigDecimal(2.0), tablespoon));
        spicyChickenTacos.addIngredient(new Ingredient("Dried oregano", new BigDecimal(1.0), teaspoon));

        spicyChickenTacos.getCategories().add(mexicanCategory);
        listOfRecipes.add(spicyChickenTacos);

        Set<Recipe> setOfMexicanRecipes = new HashSet<>();
        setOfMexicanRecipes.add(perfectGuacamole);
        setOfMexicanRecipes.add(spicyChickenTacos);
        optionalMexicanCategory.get().setRecipes(setOfMexicanRecipes);

        return listOfRecipes;
    }
}
