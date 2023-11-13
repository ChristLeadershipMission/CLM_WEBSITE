package worldwide.clm.clmwebsite.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Campus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private Long ministerId;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;
    private String logo;
}