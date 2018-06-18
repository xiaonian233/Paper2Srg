package net.minecraft.item;
import net.minecraft.util.text.TextFormatting;


public enum EnumRarity {

    COMMON(TextFormatting.WHITE, "Common"), UNCOMMON(TextFormatting.YELLOW, "Uncommon"), RARE(TextFormatting.AQUA, "Rare"), EPIC(TextFormatting.LIGHT_PURPLE, "Epic");

    public final TextFormatting rarityColor;
    public final String rarityName;

    private EnumRarity(TextFormatting enumchatformat, String s) {
        this.rarityColor = enumchatformat;
        this.rarityName = s;
    }
}
