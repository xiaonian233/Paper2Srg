package net.minecraft.scoreboard;

public class ScoreCriteriaReadOnly extends ScoreCriteria {

    public ScoreCriteriaReadOnly(String s) {
        super(s);
    }

    public boolean isReadOnly() {
        return true;
    }
}
