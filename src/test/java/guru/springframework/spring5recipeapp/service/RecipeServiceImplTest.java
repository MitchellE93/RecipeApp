package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks( this );
        recipeService = new RecipeServiceImpl( recipeRepository, null, null );
    }

    @Test
    void getRecipeByIdTest()
    {
        Recipe recipe = new Recipe();
        recipe.setId( 1L );
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when( recipeRepository.findById( anyLong() ) ).thenReturn( recipeOptional );

        Recipe returnedRecipe = recipeService.findById( 1L );

        assertNotNull( returnedRecipe );
        verify( recipeRepository, times( 1 ) ).findById( anyLong() );
        verify( recipeRepository, never() ).findAll();
    }

    @Test
    void getRecipesTest()
    {
        Recipe recipe = new Recipe();
        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add( recipe );

        when(recipeRepository.findAll()).thenReturn( recipeData );

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals( 1, recipes.size() );
        verify( recipeRepository, times( 1 ) ).findAll();
    }

    @Test
    void testDeleteById()
    {
        //given
        Long idToDelete = 2L;

        //when
        recipeService.deleteById( idToDelete );

        //then
        verify( recipeRepository, times( 1 ) ).deleteById( anyLong() );
    }
}