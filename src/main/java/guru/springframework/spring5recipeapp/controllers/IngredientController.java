package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.service.IngredientService;
import guru.springframework.spring5recipeapp.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IngredientController {

    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService)
    {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable Long recipeId, Model model)
    {
        model.addAttribute( "recipe", recipeService.findRecipeCommandById( recipeId ) );

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient( @PathVariable Long recipeId,
                                        @PathVariable Long id, Model model )
    {
        model.addAttribute( "ingredient",
                 ingredientService.findIngredientCommandByRecipeIdAndId( recipeId, id ));
        return "recipe/ingredient/show";
    }


}
