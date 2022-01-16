package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.service.IngredientService;
import guru.springframework.spring5recipeapp.service.RecipeService;
import guru.springframework.spring5recipeapp.service.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IngredientController {

    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService)
    {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
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

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable Long recipeId,
                                         @PathVariable Long id, Model model )
    {
        model.addAttribute( "ingredient",
                ingredientService.findIngredientCommandByRecipeIdAndId( recipeId, id ));
        model.addAttribute( "uomList", unitOfMeasureService.listAllUoms() );

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand command)
    {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand( command );

        return String.format( "redirect:/recipe/%s/ingredient/%s/show", savedCommand.getRecipeId(), savedCommand.getId() );
    }


}
