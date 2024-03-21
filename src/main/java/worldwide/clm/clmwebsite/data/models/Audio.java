package worldwide.clm.clmwebsite.data.models;

import jakarta.persistence.*;
import lombok.*;
import worldwide.clm.clmwebsite.enums.Channel;
import worldwide.clm.clmwebsite.enums.MediaCategory;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Audio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private MediaCategory category;
    private String videoUrl;
    private Channel channel;
}
