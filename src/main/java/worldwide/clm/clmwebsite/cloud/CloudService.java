package worldwide.clm.clmwebsite.cloud;

import org.springframework.web.multipart.MultipartFile;

public interface CloudService {
    String upload(MultipartFile image);
}
