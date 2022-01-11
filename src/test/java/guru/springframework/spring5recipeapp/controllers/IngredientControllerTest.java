package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.service.IngredientService;
import guru.springframework.spring5recipeapp.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks( this );

        IngredientController controller = new IngredientController( recipeService, ingredientService );
        mockMvc = MockMvcBuilders.standaloneSetup( controller ).build();
    }

    @Test
    void testListIngredients() throws Exception
    {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findRecipeCommandById( anyLong() )).thenReturn( recipeCommand );

        //when
        mockMvc.perform( get( "/recipe/1/ingredients" ) )
                .andExpect( status().isOk() )
                .andExpect( view().name( "recipe/ingredient/list" ) )
                .andExpect( model().attributeExists( "recipe" ) );

        //then
        verify( recipeService, times( 1 ) ).findRecipeCommandById( anyLong() );
    }

    @Test
    void testShowIngredient() throws Exception
    {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        when( ingredientService.findIngredientCommandByRecipeIdAndId( anyLong(), anyLong() ) ).thenReturn( ingredientCommand );

        //then
        mockMvc.perform( get( "/recipe/1/ingredient/2/show" ) )
                .andExpect( status().isOk())
                .andExpect( view().name( "recipe/ingredient/show" ) )
                .andExpect( model().attributeExists( "ingredient" ) );
    }
}