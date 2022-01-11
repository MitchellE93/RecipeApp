package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.spring5recipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand)
    {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes()
    {
        log.debug( "I'm in the service" );

        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach( recipes::add );
        return recipes;
    }

    @Override
    public Recipe findById( Long id )
    {
        Optional<Recipe> recipeOptional = recipeRepository.findById( id );

        if( recipeOptional.isEmpty() )
        {
            throw new RuntimeException( "Recipe not found!" );
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand findRecipeCommandById(Long id)
    {
        return recipeToRecipeCommand.convert( findById( id ) );
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command)
    {
        Recipe detachedRecipe = recipeCommandToRecipe.convert( command );

        Recipe savedRecipe = recipeRepository.save( detachedRecipe );
        log.debug( "Saved Recipe Id: " + savedRecipe.getId() );
        return recipeToRecipeCommand.convert( savedRecipe );
    }

    @Override
    public void deleteById( Long id )
    {
        recipeRepository.deleteById( id );
    }
}
