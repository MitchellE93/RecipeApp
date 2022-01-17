package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.model.Ingredient;
import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient)
    {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
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

    @Override
    public void deleteById(Long recipeId, Long ingredientId)
    {
       Optional<Recipe> recipeOptional = recipeRepository.findById( recipeId );

       if( recipeOptional.isPresent() )
       {
           Recipe recipe = recipeOptional.get();

           Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                   .filter( i -> i.getId().equals( ingredientId ) )
                   .findFirst();

           ingredientOptional.ifPresent( ingredient -> recipeRepository.save( recipe.removeIngredient( ingredient ) ) );
       }
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command)
    {
        Optional<Recipe> recipeOptional = recipeRepository.findById( command.getRecipeId() );

        if ( recipeOptional.isEmpty() )
        {
            log.error( String.format( "Recipe not found for id: %s", command.getRecipeId() ) );
            return new IngredientCommand();
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                .stream()
                .filter( i -> i.getId().equals( command.getId() ) )
                .findFirst();

        if ( ingredientOptional.isPresent() )
        {
            Ingredient ingredient = ingredientOptional.get();
            ingredient.setDescription( command.getDescription() );
            ingredient.setAmount( command.getAmount() );
            ingredient.setUom( unitOfMeasureRepository
                    .findById( command.getUom().getId() )
                    .orElseThrow( () -> new RuntimeException( "Uom Not Found" ) ) );
        }
        else
        {
            Ingredient ingredient = ingredientCommandToIngredient.convert( command );
            recipe.addIngredient( ingredient );
        }
        Recipe savedRecipe = recipeRepository.save( recipe );

        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter( i -> i.getId().equals( command.getId() ) )
                .findFirst();

        if( savedIngredientOptional.isEmpty() )
        {
            savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter( i -> i.getDescription().equals( command.getDescription() ) )
                    .filter( i -> i.getAmount().equals( command.getAmount() ) )
                    .filter( i -> i.getUom().getId().equals( command.getUom().getId() ) )
                    .findFirst();
        }

        return ingredientToIngredientCommand.convert( savedIngredientOptional.get() );
    }
}
