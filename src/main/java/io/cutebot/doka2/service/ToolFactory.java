package io.cutebot.doka2.service;

import io.cutebot.doka2.model.ItemType;
import io.cutebot.doka2.model.tool.DefaultTool;
import io.cutebot.doka2.model.tool.StoredTool;
import io.cutebot.doka2.model.tool.Tool;
import io.cutebot.doka2.repository.entity.ItemEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ToolFactory {

    @Inject
    private ItemService itemService;

    @Inject
    private ItemTitleService itemTitleService;

    public Tool getTool(Integer toolId) {
        if (toolId == null) {
            return new DefaultTool();
        }
        return new StoredTool(itemService.getExistedItemById(toolId));
    }

    public Tool generateTool(int foundItemLevel) {
        String title = itemTitleService.generateTitle(ItemType.TOOL, foundItemLevel);
        int modifier = randModifier(foundItemLevel);
        ItemEntity entity = itemService.create(title, modifier, ItemType.TOOL);
        return new StoredTool(entity);
    }

    private int randModifier(int foundItemLevel) {
        return (int) Math.round((foundItemLevel - 1) * 10 + Math.floor(Math.random() * 12));
    }

}
