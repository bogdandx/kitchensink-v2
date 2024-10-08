package KitchenSink;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Member{

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String name;
    @NotNull
    @NotEmpty
    @Email
    private String email;
    @NotNull
    @Digits(fraction = 0, integer = 12)
    @Size(min = 10, max = 12)
    private String phoneNumber;
}
