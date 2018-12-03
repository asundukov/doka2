package io.cutebot.doka2.repository.repo;

import io.cutebot.doka2.repository.entity.SearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<SearchEntity, Integer> {
    List<SearchEntity> findAllByIsActiveIsTrue();
}
