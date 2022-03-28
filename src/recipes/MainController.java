package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import recipes.models.Recipe;
import recipes.models.User;
import recipes.services.RecipeService;
import recipes.services.UserDetailsServiceImplementation;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
public class MainController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserDetailsServiceImplementation userDetailsServiceImplementation;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable long id) {

        Optional<Recipe> recipe = recipeService.findById(id);
        if (recipe.isPresent()) {
            return ResponseEntity.ok(recipe.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/api/recipe/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String category,
                                    @RequestParam(required = false) String name)
    {
        List<Recipe> response;

        if (category != null && name == null) {
            response = recipeService.findRecipesByCategory(category);
        } else if (category == null && name != null) {
            response = recipeService.findRecipesContainingName(name);
        } else {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }


    @PostMapping("/api/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {

        try {
            userDetailsServiceImplementation.loadUserByUsername(user.getEmail());
            return ResponseEntity.badRequest().build();
        } catch (UsernameNotFoundException e) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userDetailsServiceImplementation.saveUser(user);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/api/recipe/new")
    public Map<String, Long> postRecipe(@Valid @RequestBody Recipe recipe,
                                        @AuthenticationPrincipal UserDetails userDetails)
    {
        recipe.setAuthorUsername(userDetails.getUsername());
        recipe = recipeService.save(recipe);
        return Map.of("id", recipe.getId());
    }


    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<?> changeRecipe(@Valid @RequestBody Recipe inputRecipe,
                                          @PathVariable long id,
                                          @AuthenticationPrincipal UserDetails userDetails)
    {
        Optional<Recipe> recipe = recipeService.findById(id);

        if (recipe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!userDetails.getUsername().equals(recipe.get().getAuthorUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        inputRecipe.setId(id);
        inputRecipe.setAuthorUsername(userDetails.getUsername());
        recipeService.save(inputRecipe);

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {

        Optional<Recipe> recipe = recipeService.findById(id);

        if (recipe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!userDetails.getUsername().equals(recipe.get().getAuthorUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
