package com.project.crazy.global.infra.daegu.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor
@Builder
public class PlaceListResponse {

    private List<PlaceResponse> list;

}
