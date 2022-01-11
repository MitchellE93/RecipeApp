package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand)
    {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findIngredientCommandByRecipeIdAndId(Long recipeId, Long ingredientId)
    {
        Optional<Recipe> recipeOptional = recipeRepository.findById( recipeId );

        if ( recipeOptional.isEmpty() )
        {
            //todo impl error handling
        }

        Recipe recipe = recipeOptional.get();
        Optional<IngredientCommand> ingredientOptional = recipe.getIngredients().stream()
                .filter( i -> i.getId().equals( ingredientId ) )
                .map( ingredientToIngredientCommand::convert )
                .findFirst();

        if ( ingredientOptional.isEmpty() )
        {
            // todo impl error handling
        }

        return ingredientOptional.get();
    }
}
