package worldwide.clm.clmwebsite.services.cronJobs;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.utils.HttpUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CronJobsService {
    private final HttpUtils httpUtils;


    @Scheduled(cron = "0 */10 * * * *")
    public void keepServiceAwake(){
        log.info("Waking Support Server...");
        log.info(LocalDateTime.now()+"::>> "+httpUtils.wakeServerSupportUp());
    }
}
