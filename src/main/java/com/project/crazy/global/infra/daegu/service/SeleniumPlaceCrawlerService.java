package com.project.crazy.global.infra.daegu.service;

import com.project.crazy.global.infra.daegu.PlaceCrawlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeleniumPlaceCrawlerService {

    public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static String WEB_DRIVER_PATH = "C:\\Program Files\\chromeDriver\\chromedriver.exe";

    public List<PlaceCrawlerService.PlaceInfraResponse> parserData() {

        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.google.com/travel/things-to-do/see-all?dest_mid=%2Fm%2F01vskn&dest_state_type=sattd&dest_src=yts&q=대구&ved=0CAAQ8IAIahcKEwj4rtf43Y_7AhUAAAAAHQAAAAAQfw&hl=ko-KR&gl=kr");

            List<WebElement> list = driver.findElements(By.className(".kQb6Eb"));
            for(WebElement ele : list) {
                log.info("element : " + ele.findElement(By.className(".f4hh3d")).findElement(By.className(".skFvHc")).getText());
                log.info("element : " + ele.findElement(By.className(".f4hh3d")).findElement(By.className(".nFoFM")).getText());
            }

            return null;
        } finally {
            driver.quit();
        }
    }


    public void execute() {
        parserData();
    }

    public static void main(String[] args) {
        SeleniumPlaceCrawlerService s = new SeleniumPlaceCrawlerService();

        s.execute();
    }
}
