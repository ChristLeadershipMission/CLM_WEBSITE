package worldwide.clm.clmwebsite.dto.request;

import lombok.Getter;
import lombok.Setter;
import worldwide.clm.clmwebsite.enums.Channel;
import worldwide.clm.clmwebsite.enums.MediaCategory;

@Setter
@Getter
public class VideoCreationRequest {

    private String title;
    private MediaCategory category;
    private String videoUrl;
    private Channel channel;
}
