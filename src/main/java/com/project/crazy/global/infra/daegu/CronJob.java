package com.project.crazy.global.infra.daegu;

import com.project.crazy.global.infra.daegu.service.SeleniumPlaceCrawlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CronJob {

    private final PlaceCrawlerService placeCrawlerService;
    private final SeleniumPlaceCrawlerService seleniumPlaceCrawlerService;

    @Scheduled(cron = "0 */1 * * * *")
    public void updatePlace() {
        log.info("updatePlace");
        placeCrawlerService.execute();
//        seleniumPlaceCrawlerService.execute();
    }

}
