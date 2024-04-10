package worldwide.clm.clmwebsite.dto.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import worldwide.clm.clmwebsite.enums.Channel;
import worldwide.clm.clmwebsite.enums.MediaCategory;

@Setter
@Getter
public class AudioCreationRequest {

    private String title;
    private MediaCategory category;
    private String audioUrl;
    private Channel channel;
}
