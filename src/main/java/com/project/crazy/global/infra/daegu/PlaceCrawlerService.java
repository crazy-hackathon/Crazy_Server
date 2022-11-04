package com.project.crazy.global.infra.daegu;

import com.project.crazy.global.infra.daegu.entity.Place;
import com.project.crazy.global.infra.daegu.repository.PlaceRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceCrawlerService {

    private static final String[][] urlList = {
            {"https://www.google.com/travel/things-to-do/see-all?dest_mid=%2Fm%2F01vskn&dest_state_type=sattd&dest_src=yts&q=%EB%8C%80%EA%B5%AC&ved=0CAAQ8IAIahcKEwj4rtf43Y_7AhUAAAAAHQAAAAAQfw&hl=ko-KR&gl=kr&rf=EtwBCg0vZy8xMWM3MWh2ZnQwEhTsnKDslYQg64-Z67CYIOqwgOuKpTAIIrABClZodHRwczovL3d3dy5nc3RhdGljLmNvbS9pbWFnZXMvaWNvbnMvbWF0ZXJpYWwvc3lzdGVtX2dtLzF4L2NoaWxkX2NhcmVfZ21fYmx1ZV8yMGRwLnBuZxJWaHR0cHM6Ly93d3cuZ3N0YXRpYy5jb20vaW1hZ2VzL2ljb25zL21hdGVyaWFsL3N5c3RlbV9nbS8yeC9jaGlsZF9jYXJlX2dtX2JsdWVfMjBkcC5wbmcoAQ", "유아동반가능"},
            {"https://www.google.com/travel/things-to-do/see-all?dest_mid=%2Fm%2F01vskn&dest_state_type=sattd&dest_src=yts&q=%EB%8C%80%EA%B5%AC&ved=0CAAQ8IAIahcKEwj4rtf43Y_7AhUAAAAAHQAAAAAQfw&hl=ko-KR&gl=kr&rf=EtMBCgcvbS8wamp3EhHsmIjsiKAg67CPIOusuO2ZlDACIrABClZodHRwczovL3d3dy5nc3RhdGljLmNvbS9pbWFnZXMvaWNvbnMvbWF0ZXJpYWwvc3lzdGVtX2dtLzF4L2NvbG9yX2xlbnNfZ21fYmx1ZV8yMGRwLnBuZxJWaHR0cHM6Ly93d3cuZ3N0YXRpYy5jb20vaW1hZ2VzL2ljb25zL21hdGVyaWFsL3N5c3RlbV9nbS8yeC9jb2xvcl9sZW5zX2dtX2JsdWVfMjBkcC5wbmcoAQ", "예술및문화"},
            {"https://www.google.com/travel/things-to-do/see-all?dest_mid=%2Fm%2F01vskn&dest_state_type=sattd&dest_src=yts&q=%EB%8C%80%EA%B5%AC&ved=0CAAQ8IAIahcKEwj4rtf43Y_7AhUAAAAAHQAAAAAQfw&hl=ko-KR&gl=kr&rf=Et0BCg0vZy8xMWJjNThsMTN3Eg3slbzsmbgg7Zmc64-ZMA4iuAEKWmh0dHBzOi8vd3d3LmdzdGF0aWMuY29tL2ltYWdlcy9pY29ucy9tYXRlcmlhbC9zeXN0ZW1fZ20vMXgvZmlsdGVyX3ZpbnRhZ2VfZ21fYmx1ZV8yMGRwLnBuZxJaaHR0cHM6Ly93d3cuZ3N0YXRpYy5jb20vaW1hZ2VzL2ljb25zL21hdGVyaWFsL3N5c3RlbV9nbS8yeC9maWx0ZXJfdmludGFnZV9nbV9ibHVlXzIwZHAucG5nKAE", "야외활동"},
            {"https://www.google.com/travel/things-to-do/see-all?dest_mid=%2Fm%2F01vskn&dest_state_type=sattd&dest_src=yts&q=%EB%8C%80%EA%B5%AC&ved=0CAAQ8IAIahcKEwj4rtf43Y_7AhUAAAAAHQAAAAAQfw&hl=ko-KR&gl=kr&rf=Es0BCggvbS8wM2czdxIG7Jet7IKsMAcitAEKWGh0dHBzOi8vd3d3LmdzdGF0aWMuY29tL2ltYWdlcy9pY29ucy9tYXRlcmlhbC9zeXN0ZW1fZ20vMXgvYXV0b19zdG9yaWVzX2dtX2JsdWVfMjBkcC5wbmcSWGh0dHBzOi8vd3d3LmdzdGF0aWMuY29tL2ltYWdlcy9pY29ucy9tYXRlcmlhbC9zeXN0ZW1fZ20vMngvYXV0b19zdG9yaWVzX2dtX2JsdWVfMjBkcC5wbmcoAQ", "역사"},
            {"https://www.google.com/travel/things-to-do/see-all?dest_mid=%2Fm%2F01vskn&dest_state_type=sattd&dest_src=yts&q=%EB%8C%80%EA%B5%AC&ved=0CAAQ8IAIahcKEwj4rtf43Y_7AhUAAAAAHQAAAAAQfw&hl=ko-KR&gl=kr&rf=EtYBCggvbS8wOWNtcRIJ67CV66y86rSAMAwiugEKW2h0dHBzOi8vd3d3LmdzdGF0aWMuY29tL2ltYWdlcy9pY29ucy9tYXRlcmlhbC9zeXN0ZW1fZ20vMXgvYWNjb3VudF9iYWxhbmNlX2dtX2JsdWVfMjBkcC5wbmcSW2h0dHBzOi8vd3d3LmdzdGF0aWMuY29tL2ltYWdlcy9pY29ucy9tYXRlcmlhbC9zeXN0ZW1fZ20vMngvYWNjb3VudF9iYWxhbmNlX2dtX2JsdWVfMjBkcC5wbmcoAQ", "박물관"}
    };
    private final PlaceRepository placeRepository;

    @Getter @AllArgsConstructor
    @Builder @ToString
    public static class PlaceInfraResponse {
        private String title;
        private String content;
        private String imageUrl;
        private String category;
    }

    public List<PlaceInfraResponse> parserData(String url, String name) {
        log.info("url : " + url);
        try {
            Document document = Jsoup.connect(url).get();
            Element body = document.body();

            List<Element> data = body.select(".kQb6Eb");
            List<PlaceInfraResponse> response = null;
            for (Element ele : data) {
                List<Element> list = ele.children();
                response = list.stream().map(it ->
                        PlaceInfraResponse.builder()
                                .title(it.select(".skFvHc").text())
                                .content(it.select(".nFoFM").text())
                                .imageUrl(it.select(".R1Ybne").attr("data-src"))
                                .category(name)
                                .build()
                ).collect(Collectors.toList());
            }
            log.info("{}개의 데이터", response.size());
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void execute() {
        for (String[] url : urlList) {
            List<PlaceInfraResponse> list = parserData(url[0], url[1]);

            List<Place> saveData = list.stream().map(it -> {
                if (!placeRepository.findByTitle(it.getTitle()).isEmpty()) {
                    return Place.builder()
                            .placeId(placeRepository.findByTitle(it.getTitle()).get().getPlaceId())
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
            placeRepository.saveAll(saveData);
            log.info(url[1] + " 카테고리 저장");
        }
        log.info("전체 저장 성공");
    }

}
