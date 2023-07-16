package worldwide.clm.clmwebsite.data.models;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Campus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String state;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Minister minister;
}
