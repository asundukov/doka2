package io.cutebot.doka2.repository.repo;

import io.cutebot.doka2.repository.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {

}
