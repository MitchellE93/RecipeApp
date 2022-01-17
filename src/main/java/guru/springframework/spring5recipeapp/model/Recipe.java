package guru.springframework.spring5recipeapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "recipe",
            orphanRemoval = true)
    private Set<Ingredient> ingredients = new HashSet<>();

    @Lob
    private Byte[] image;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public Recipe addIngredient( Ingredient ingredient )
    {
        ingredients.add( ingredient );
        ingredient.setRecipe( this );
        return this;
    }

    public Recipe removeIngredient( Ingredient ingredient )
    {
        ingredients.remove( ingredient );
        ingredient.setRecipe( null );
        return this;
    }

    public void setNotes(Notes notes)
    {
        this.notes = notes;
        notes.setRecipe( this );
    }
}
