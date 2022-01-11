package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    private IndexController controller;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks( this );
        controller = new IndexController( recipeService );
    }

    @Test
    void testMockMVC() throws Exception
    {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup( controller ).build();

        mockMvc.perform( get( "/" ) )
                .andExpect( status().isOk() )
                .andExpect( view().name( "index" ) );
    }

    @Test
    void getIndexPage()
    {
        //given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add( new Recipe() );

        Recipe recipe = new Recipe();
        recipe.setId( 1L );
        recipes.add( recipe );

        //when
        when( recipeService.getRecipes() ).thenReturn( recipes );

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass( Set.class );

        String viewName = controller.getIndexPage( model );

        //then
        assertEquals( "index", viewName );
        verify( recipeService, times( 1 )).getRecipes();
        verify( model, times( 1 ) ).addAttribute( eq( "recipes" ), argumentCaptor.capture() );
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals( 2, setInController.size() );
    }
}