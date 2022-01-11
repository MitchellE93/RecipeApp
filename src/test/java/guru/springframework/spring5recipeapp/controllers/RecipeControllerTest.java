package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks( this );
        RecipeController controller = new RecipeController( recipeService );
        mockMvc =  MockMvcBuilders.standaloneSetup( controller ).build();
    }

    @Test
    void testGetRecipe() throws Exception
    {
        Recipe recipe = new Recipe();
        recipe.setId( 1L );

        when( recipeService.findById( anyLong() ) ).thenReturn( recipe );

        mockMvc.perform( get( "/recipe/1/show") )
                .andExpect( status().isOk() )
                .andExpect( view().name( "recipe/show" ) )
                .andExpect( model().attributeExists( "recipe" ) );
    }

    @Test
    void testGetNewRecipeForm() throws Exception
    {
        RecipeCommand command = new RecipeCommand();

        mockMvc.perform( get( "/recipe/new" ) )
                .andExpect( status().isOk() )
                .andExpect( view().name( "recipe/recipeform" ) )
                .andExpect( model().attributeExists( "recipe" ) );
    }

    @Test
    void testPostNewRecipeForm() throws Exception
    {
        RecipeCommand command = new RecipeCommand();
        command.setId( 2L );

        when( recipeService.saveRecipeCommand( any() ) ).thenReturn( command );

        mockMvc.perform( post( "/recipe" )
                .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                .param( "id", "" )
                .param( "description", "some string" )
        )
                .andExpect( status().is3xxRedirection() )
                .andExpect( view().name( "redirect:/recipe/2/show" ) );
    }

    @Test
    void testGetUpdateView() throws Exception
    {
        RecipeCommand command = new RecipeCommand();
        command.setId( 2L );

        when( recipeService.findRecipeCommandById( anyLong() )).thenReturn( command );

        mockMvc.perform( get( "/recipe/1/update" ) )
                .andExpect( status().isOk() )
                .andExpect( view().name( "recipe/recipeform" ) )
                .andExpect( model().attributeExists( "recipe" ) );
    }

    @Test
    void testDeleteAction() throws Exception
    {
        mockMvc.perform( get( "/recipe/1/delete" ) )
                .andExpect( status().is3xxRedirection() )
                .andExpect( view().name( "redirect:/" ) );
    }


















}