package io.cutebot.doka2.repository.repo;

import io.cutebot.doka2.repository.entity.BuildEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildRepository extends JpaRepository<BuildEntity, Integer> {
    List<BuildEntity> findAllByBuildActiveIsTrue();
    List<BuildEntity> findAllByBuildActiveIsFalseAndGainedDestroyExpIsNull();

    List<BuildEntity> findAllByDestroyActiveIsTrue();
}
