package com.project.crazy.global.infra.daegu.presentation;

import com.project.crazy.global.infra.daegu.presentation.dto.response.PlaceListResponse;
import com.project.crazy.global.infra.daegu.service.PlaceService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceController {

    private final PlaceService placeService;

    @ApiOperation("전체 장소")
    @GetMapping("/")
    public PlaceListResponse getAllPlace() {
        return placeService.getAllPlace();
    }

    @ApiOperation("카테고리별 추천 장소")
    @GetMapping("/category/{category}")
    public PlaceListResponse getAllPlaceByCategory(
            @PathVariable("category") String category
    ) {
        return placeService.getAllPlaceByCategory(category);
    }

}
