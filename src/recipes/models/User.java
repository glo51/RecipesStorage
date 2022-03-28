package recipes.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User {

    @GeneratedValue
    @Id
    Long id;

    @Column
    @Pattern(regexp = ".+@.+\\..+")
    String email;

    @NotBlank
    @Length(min = 8)
    @Column
    String password;
}
