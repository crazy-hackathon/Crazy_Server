package com.project.crazy.global.infra.daegu.repository;

import com.project.crazy.global.infra.daegu.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByTitleAndContent(String title, String content);

    List<Place> findAllByCategory(String category);

}
