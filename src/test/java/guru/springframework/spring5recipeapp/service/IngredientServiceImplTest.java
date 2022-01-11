package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.model.Ingredient;
import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.criteria.CriteriaBuilder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    @Mock
    private RecipeRepository recipeRepository;

    private IngredientService ingredientService;

    public IngredientServiceImplTest()
    {
        this.ingredientToIngredientCommand =
                new IngredientToIngredientCommand( new UnitOfMeasureToUnitOfMeasureCommand() );
    }

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks( this );
        ingredientService = new IngredientServiceImpl( recipeRepository, ingredientToIngredientCommand );
    }

    @Test
    void findIngredientCommandByRecipeIdAndId()
    {
        //given
        Recipe recipe = new Recipe();
        recipe.setId( 1L );

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId( 1L );

        Ingredient ingredient2 = new Ingredient();
        ingredient1.setId( 1L );

        Ingredient ingredient3 = new Ingredient();
        ingredient1.setId( 3L );

        recipe.addIngredient( ingredient1 );
        recipe.addIngredient( ingredient2 );
        recipe.addIngredient( ingredient3 );
        Optional<Recipe> recipeOptional = Optional.of( recipe );

        when( recipeRepository.findById( anyLong() )).thenReturn( recipeOptional );

        //then
        IngredientCommand ingredientCommand = ingredientService.findIngredientCommandByRecipeIdAndId( 1L, 3L );

        //when
        assertEquals( 3L, ingredientCommand.getId() );
        assertEquals( 1L, ingredientCommand.getRecipeId() );
        verify( recipeRepository, times( 1 ) ).findById( anyLong() );
    }
}