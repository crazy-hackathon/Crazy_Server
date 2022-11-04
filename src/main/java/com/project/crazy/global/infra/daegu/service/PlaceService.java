package com.project.crazy.global.infra.daegu.service;

import com.project.crazy.global.infra.daegu.entity.Place;
import com.project.crazy.global.infra.daegu.presentation.dto.response.PlaceListResponse;
import com.project.crazy.global.infra.daegu.presentation.dto.response.PlaceResponse;
import com.project.crazy.global.infra.daegu.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional(readOnly = true)
    public PlaceListResponse getAllPlace() {
        List<Place> list = placeRepository.findAll();

        return new PlaceListResponse(
                list.stream().map(it ->
                                PlaceResponse.builder()
                                        .title(it.getTitle())
                                        .content(it.getContent())
                                        .imgUrl(it.getImgUrl())
                                        .build()
                        ).collect(Collectors.toList())
        );
    }

}
