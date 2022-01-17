package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findIngredientCommandByRecipeIdAndId( Long recipeId, Long ingredientId );

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteById( Long recipeId, Long ingredientId );
}
