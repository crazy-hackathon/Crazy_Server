package com.project.crazy.global.infra.daegu.service;

import com.project.crazy.global.infra.daegu.PlaceCrawlerService;
import com.project.crazy.global.infra.daegu.entity.Place;
import com.project.crazy.global.infra.daegu.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Charsets.UTF_8;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeleniumPlaceCrawlerService {
    private final PlaceRepository placeRepository;

    public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static String WEB_DRIVER_PATH = "C:\\Program Files\\chromeDriver\\chromedriver.exe";
    private WebDriver driver;
    private static final String[][] categoryUrlList = new String[][]{
            {"/html/body/c-wiz[2]/div/div[2]/div/c-wiz/div/div/div/div[1]/div/div[1]/div[1]/div[2]/div/div/div/div/div[1]/div/div/span[2]/span/button", "유아 동반 가능"},
            {"/html/body/c-wiz[2]/div/div[2]/div/c-wiz/div/div/div/div[1]/div/div[1]/div[1]/div[2]/div/div/div/div/div[1]/div/div/span[3]/span/button", "예술 및 문화"},
            {"/html/body/c-wiz[2]/div/div[2]/div/c-wiz/div/div/div/div[1]/div/div[1]/div[1]/div[2]/div/div/div/div/div[1]/div/div/span[4]/span/button", "야외 활동"},
            {"/html/body/c-wiz[2]/div/div[2]/div/c-wiz/div/div/div/div[1]/div/div[1]/div[1]/div[2]/div/div/div/div/div[1]/div/div/span[5]/span/button", "역사"},
            {"/html/body/c-wiz[2]/div/div[2]/div/c-wiz/div/div/div/div[1]/div/div[1]/div[1]/div[2]/div/div/div/div/div[1]/div/div/span[6]/span/button", "박물관"}
    };

    public List<PlaceCrawlerService.PlaceInfraResponse> parserData() {

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        driver = new ChromeDriver();

        try {
            driver.get("https://www.google.com/travel/things-to-do/see-all?dest_mid=%2Fm%2F01vskn&dest_state_type=sattd&dest_src=yts&q=%EB%8C%80%EA%B5%AC&ved=0CAAQ8IAIahcKEwj4rtf43Y_7AhUAAAAAHQAAAAAQfw&hl=ko-KR&gl=kr");

                List<PlaceCrawlerService.PlaceInfraResponse> response = null;
            for(int i = 0; i < 5; i++) {
                WebElement button = driver.findElement(
                        By.xpath(categoryUrlList[i][0]));
                button.click();
                Thread.sleep(1000);

                List<WebElement> data = driver.findElements(By.cssSelector("#yDmH0d > c-wiz.zQTmif.SSPGKf > div > div.lteUWc > div > c-wiz > div > div > div > div:nth-child(3) > c-wiz > div > div.XzK3Bf > div > div.kQb6Eb > div"));
                for (WebElement ele : data) {
                    List<WebElement> list = ele.findElements(By.xpath("//*[@id=\"yDmH0d\"]/c-wiz[2]/div/div[2]/div/c-wiz/div/div/div[1]/div[2]/c-wiz/div/div[2]/div/div[2]/div[1]"));

                    int finalI = i;
                    response = list.stream().map(it ->
                                        PlaceCrawlerService.PlaceInfraResponse.builder()
                                                .title(it.findElement(By.xpath("//*[@id=\"yDmH0d\"]/c-wiz[2]/div/div[2]/div/c-wiz/div/div/div/div[2]/c-wiz/div/div[2]/div/div[2]/div[1]/div/div/div[1]/div[2]/div[1]/div")).getText())
                                                .content(it.findElement(By.xpath("//*[@id=\"yDmH0d\"]/c-wiz[2]/div/div[2]/div/c-wiz/div/div/div/div[2]/c-wiz/div/div[2]/div/div[2]/div[1]/div/div/div[1]/div[2]/div[3]")).getText())
                                                .imageUrl(it.findElement(By.xpath("//*[@id=\"yDmH0d\"]/c-wiz[2]/div/div[2]/div/c-wiz/div/div/div/div[2]/c-wiz/div/div[2]/div/div[2]/div[1]/div/div/div[1]/div[1]/easy-img/img")).getAttribute("src"))
                                                .category(categoryUrlList[finalI][1])
                                                .build()
                                ).collect(Collectors.toList());
                }
            }
            return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            driver.quit();
        }
    }

    public void execute() {
        List<PlaceCrawlerService.PlaceInfraResponse> list = parserData();

        List<Place> save = list.stream().map(it -> {
            if(placeRepository.findByTitleAndContent(it.getTitle(), it.getContent()).isPresent()) {
                return Place.builder()
                        .placeId(placeRepository.findByTitleAndContent(it.getTitle(), it.getContent()).get().getPlaceId())
                        .title(it.getTitle())
                        .content(it.getContent())
                        .imgUrl(it.getImageUrl())
                        .category(it.getCategory())
                        .build();
            } else {
                return Place.builder()
                        .title(it.getTitle())
                        .content(it.getContent())
                        .imgUrl(it.getImageUrl())
                        .category(it.getCategory())
                        .build();
            }
        }).collect(Collectors.toList());
        placeRepository.saveAll(save);
    }

}
