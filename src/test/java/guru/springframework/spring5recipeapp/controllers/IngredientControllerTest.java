package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.model.Ingredient;
import guru.springframework.spring5recipeapp.service.IngredientService;
import guru.springframework.spring5recipeapp.service.RecipeService;
import guru.springframework.spring5recipeapp.service.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.criteria.CriteriaBuilder;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks( this );

        IngredientController controller = new IngredientController( recipeService, ingredientService, unitOfMeasureService );
        mockMvc = MockMvcBuilders.standaloneSetup( controller ).build();
    }

    @Test
    void testListIngredients() throws Exception
    {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        when( recipeService.findRecipeCommandById( anyLong() ) ).thenReturn( recipeCommand );

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
                .andExpect( status().isOk() )
                .andExpect( view().name( "recipe/ingredient/show" ) )
                .andExpect( model().attributeExists( "ingredient" ) );
    }

    @Test
    void testUpdateIngredientForm() throws Exception
    {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();
        UnitOfMeasureCommand uom = new UnitOfMeasureCommand();
        uom.setId( 1L );
        unitOfMeasureCommands.add( uom );
        unitOfMeasureCommands.add( new UnitOfMeasureCommand() );

        //when
        when( ingredientService.findIngredientCommandByRecipeIdAndId( anyLong(), anyLong() ) ).thenReturn( ingredientCommand );
        when( unitOfMeasureService.listAllUoms() ).thenReturn( unitOfMeasureCommands );

        //then
        mockMvc.perform( get( "/recipe/1/ingredient/2/update" ) )
                .andExpect( status().isOk() )
                .andExpect( view().name( "recipe/ingredient/ingredientform" ) )
                .andExpect( model().attributeExists( "ingredient", "uomList" ) );
    }

    @Test
    void testSaveOrUpdate() throws Exception
    {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId( 3L );
        command.setRecipeId( 2L );

        //when
        when( ingredientService.saveIngredientCommand( any() ) ).thenReturn( command );

        //then
        mockMvc.perform( post( "/recipe/2/ingredient" )
                .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                .param( "id", "" )
                .param( "description", "some string" )
        )
                .andExpect( status().is3xxRedirection() )
                .andExpect( view().name( "redirect:/recipe/2/ingredient/3/show" ) );


    }
}