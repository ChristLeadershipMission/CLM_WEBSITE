package worldwide.clm.clmwebsite.cloud;

import org.springframework.web.multipart.MultipartFile;
import worldwide.clm.clmwebsite.exception.BusinessLogicException;

public interface CloudService {
    String upload(MultipartFile image) throws BusinessLogicException;
}
