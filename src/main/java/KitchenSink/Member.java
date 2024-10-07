package KitchenSink;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Member{

    @Id
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
}
