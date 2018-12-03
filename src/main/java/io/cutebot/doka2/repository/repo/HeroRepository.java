package io.cutebot.doka2.repository.repo;

import io.cutebot.doka2.repository.entity.HeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<HeroEntity, Long> {
}
