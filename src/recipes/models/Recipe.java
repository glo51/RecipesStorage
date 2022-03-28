package recipes.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Recipes")
public class Recipe {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @NotBlank
    @Column
    private String name;

    @NotBlank
    @Column
    private String description;

    @NotBlank
    @Column
    private String category;

    @NotEmpty
    @Size(min = 1)
    @ElementCollection
    private List<String> ingredients;

    @NotEmpty
    @Size(min = 1)
    @ElementCollection
    private List<String> directions;

    @Column
    @UpdateTimestamp
    private final LocalDateTime date = LocalDateTime.now();

    @JsonIgnore
    @Column(name = "author_username")
    private String authorUsername;
}
