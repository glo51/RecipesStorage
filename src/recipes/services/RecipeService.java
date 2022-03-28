package recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.models.Recipe;
import recipes.repositories.RecipeRepository;

import java.util.List;
import java.util.Optional;


@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;


    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void delete(long id) {
        recipeRepository.deleteById(id);
    }

    public Optional<Recipe> findById(long id) {
        return recipeRepository.findById(id);
    }

    public List<Recipe> findRecipesByCategory(String category) {
        return recipeRepository.getRecipesByCategory(category);
    }

    public List<Recipe> findRecipesContainingName(String name) {
        return recipeRepository.getRecipesContainingName(name);
    }
}
