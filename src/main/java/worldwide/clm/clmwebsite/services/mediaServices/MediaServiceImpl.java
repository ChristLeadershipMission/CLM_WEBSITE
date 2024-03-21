package worldwide.clm.clmwebsite.services.mediaServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Media;
import worldwide.clm.clmwebsite.data.repositories.AudioRepository;
import worldwide.clm.clmwebsite.data.repositories.VideoRepository;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService{
    private final AudioRepository audioRepository;
    private final VideoRepository videoRepository;

    @Override
    public Media retrieveMediaData() {
        return Media.builder()
                .audios(audioRepository.findAll())
                .videos(videoRepository.findAll())
                .build();
    }
}
