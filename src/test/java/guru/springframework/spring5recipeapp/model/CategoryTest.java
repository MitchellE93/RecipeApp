package guru.springframework.spring5recipeapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private Category category;

    @BeforeEach
    public void setUp()
    {
        category = new Category();
    }

    @Test
    void getId()
    {
        long idValue = 4L;

        category.setId( idValue );
        assertEquals( idValue, category.getId() );
    }

    @Test
    void getDescription()
    {
    }

    @Test
    void getRecipes()
    {
    }
}