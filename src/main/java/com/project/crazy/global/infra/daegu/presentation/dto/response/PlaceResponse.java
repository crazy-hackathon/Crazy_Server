package com.project.crazy.global.infra.daegu.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @AllArgsConstructor
@Builder
public class PlaceResponse {

    private String title;
    private String content;
    private String imgUrl;
    private String category;

}
