package KitchenSink;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
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
    private String name;
    private String email;
    @Digits(fraction = 0, integer = 12)
    @Size(min = 10, max = 12)
    private String phoneNumber;
}
