package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById( Long id );

    void deleteById( Long id );

    RecipeCommand findRecipeCommandById( Long id );

    RecipeCommand saveRecipeCommand( RecipeCommand command) ;
}
