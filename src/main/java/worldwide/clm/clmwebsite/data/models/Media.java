package worldwide.clm.clmwebsite.data.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Media {
    private List<Video> videos;
    private List<Audio> audios;
}
