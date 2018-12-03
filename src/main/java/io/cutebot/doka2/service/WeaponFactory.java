package io.cutebot.doka2.service;

import io.cutebot.doka2.model.ItemType;
import io.cutebot.doka2.model.weapon.DefaultWeapon;
import io.cutebot.doka2.model.weapon.StoredWeapon;
import io.cutebot.doka2.model.weapon.Weapon;
import io.cutebot.doka2.repository.entity.ItemEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class WeaponFactory {
    @Inject
    private ItemService itemService;

    @Inject
    private ItemTitleService itemTitleService;

    public Weapon getWeapon(Integer toolId) {
        if (toolId == null) {
            return new DefaultWeapon();
        }
        return new StoredWeapon(itemService.getExistedItemById(toolId));
    }

    public Weapon generateWeapon(int foundItemLevel) {
        String title = itemTitleService.generateTitle(ItemType.WEAPON, foundItemLevel);
        ItemEntity item = itemService.create(title, randModifier(foundItemLevel), ItemType.WEAPON);
        return new StoredWeapon(item);
    }

    private int randModifier(int foundItemLevel) {
        return (int) Math.round((foundItemLevel - 1) * 10 + Math.floor(Math.random() * 12));
    }

}
