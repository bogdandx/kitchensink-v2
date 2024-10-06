package KitchenSink;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member{

    private int id;
    private String name;
    private String email;
    private String phoneNumber;
}
