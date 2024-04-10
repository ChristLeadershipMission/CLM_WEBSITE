package worldwide.clm.clmwebsite.services.mediaServices;

import worldwide.clm.clmwebsite.data.models.Audio;
import worldwide.clm.clmwebsite.data.models.Media;
import worldwide.clm.clmwebsite.data.models.Video;
import worldwide.clm.clmwebsite.dto.request.AudioCreationRequest;
import worldwide.clm.clmwebsite.dto.request.VideoCreationRequest;
import worldwide.clm.clmwebsite.exception.MediaProcessingException;

public interface MediaService {
    Media retrieveMediaData();

    Video createVideo(VideoCreationRequest videoCreationRequest) throws MediaProcessingException;

    Audio createAudio(AudioCreationRequest audioCreationRequest) throws MediaProcessingException;
}
