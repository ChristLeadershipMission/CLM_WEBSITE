package worldwide.clm.clmwebsite.services.mediaServices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Audio;
import worldwide.clm.clmwebsite.data.models.Media;
import worldwide.clm.clmwebsite.data.models.Video;
import worldwide.clm.clmwebsite.data.repositories.AudioRepository;
import worldwide.clm.clmwebsite.data.repositories.VideoRepository;
import worldwide.clm.clmwebsite.dto.request.AudioCreationRequest;
import worldwide.clm.clmwebsite.dto.request.VideoCreationRequest;
import worldwide.clm.clmwebsite.exception.MediaProcessingException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final AudioRepository audioRepository;
    private final VideoRepository videoRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Media retrieveMediaData() {
        return Media.builder()
                .audios(audioRepository.findAll())
                .videos(videoRepository.findAll())
                .build();
    }

    @Override
    public Video createVideo(VideoCreationRequest videoCreationRequest) throws MediaProcessingException {
        try {
            Video video = objectMapper.readValue(objectMapper.writeValueAsString(videoCreationRequest), Video.class);
            video.setTimeCreated(LocalDateTime.now());
            return videoRepository.save(video);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MediaProcessingException(e.getMessage());
        }
    }

    @Override
    public Audio createAudio(AudioCreationRequest audioCreationRequest) throws MediaProcessingException {
        try {
            Audio audio = objectMapper.readValue(objectMapper.writeValueAsString(audioCreationRequest), Audio.class);
            audio.setTimeCreated(LocalDateTime.now());
            System.out.println(objectMapper.writeValueAsString(audio));
            return audioRepository.save(audio);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MediaProcessingException(e.getMessage());
        }
    }
}
