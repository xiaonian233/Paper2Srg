package net.minecraft.util.text;

import java.util.Iterator;

public class TextComponentSelector extends TextComponentBase {

    private final String selector;

    public TextComponentSelector(String s) {
        this.selector = s;
    }

    public String getSelector() {
        return this.selector;
    }

    @Override
    public String getUnformattedComponentText() {
        return this.selector;
    }

    @Override
    public TextComponentSelector createCopy() {
        TextComponentSelector chatcomponentselector = new TextComponentSelector(this.selector);

        chatcomponentselector.setStyle(this.getStyle().createShallowCopy());
        Iterator iterator = this.getSiblings().iterator();

        while (iterator.hasNext()) {
            ITextComponent ichatbasecomponent = (ITextComponent) iterator.next();

            chatcomponentselector.appendSibling(ichatbasecomponent.createCopy());
        }

        return chatcomponentselector;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof TextComponentSelector)) {
            return false;
        } else {
            TextComponentSelector chatcomponentselector = (TextComponentSelector) object;

            return this.selector.equals(chatcomponentselector.selector) && super.equals(object);
        }
    }

    @Override
    public String toString() {
        return "SelectorComponent{pattern=\'" + this.selector + '\'' + ", siblings=" + this.siblings + ", style=" + this.getStyle() + '}';
    }
}
