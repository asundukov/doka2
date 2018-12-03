package io.cutebot.doka2.model.tool;

public class DefaultTool extends Tool {

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public String getTitle() {
        return "Левая рука";
    }

    @Override
    public String getFullTitle() {
        return getTitle();
    }

    @Override
    public Integer getModifier() {
        return 0;
    }

    @Override
    public boolean isDropable() {
        return false;
    }
}
