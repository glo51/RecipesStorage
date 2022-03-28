package recipes.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import recipes.models.Recipe;

import java.util.List;


public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM Recipes WHERE UPPER(category) = UPPER(:category) ORDER BY date DESC")
    List<Recipe> getRecipesByCategory(@Param("category") String category);

    @Query(nativeQuery = true,
            value = "SELECT * FROM Recipes WHERE UPPER(name) LIKE UPPER(CONCAT('%', :name, '%')) ORDER BY date DESC")
    List<Recipe> getRecipesContainingName(@Param("name") String name);
}
