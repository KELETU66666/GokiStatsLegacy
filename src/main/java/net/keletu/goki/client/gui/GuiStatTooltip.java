package net.keletu.goki.client.gui;

import net.keletu.goki.lib.Helper;
import net.keletu.goki.stats.Stat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;

public class GuiStatTooltip extends Gui {
    private Stat stat;
    private EntityPlayer player;
    private Minecraft mc;
    private int padding;

    public GuiStatTooltip(final Stat stat, final EntityPlayer player) {
        this.mc = Minecraft.getMinecraft();
        this.padding = 4;
        this.stat = stat;
        this.player = player;
    }

    public void draw(final int drawX, final int drawY, final int mouseButton) {
        final int level = Helper.getPlayerStatLevel(this.player, this.stat);
        final String header = this.stat.name + " L" + level;
        final String message = this.stat.getAppliedDescriptionString(this.player);
        String cost = "Costs " + this.stat.getCost(level) + "xp";
        if (level >= this.stat.getLimit()) {
            cost = "Maxed!";
        }
        final int width = Math.max(this.mc.fontRenderer.getStringWidth(message), this.mc.fontRenderer.getStringWidth(header)) + this.padding * 2;
        final int height = this.mc.fontRenderer.FONT_HEIGHT * 3 + this.padding * 2;
        final int h = height / 3;
        int x = drawX - width / 2;
        final int y = drawY;
        final int leftEdge = 0;
        final int left = x;
        if (left < leftEdge) {
            x -= leftEdge - left + 1;
        }
        final int rightEdge = this.mc.currentScreen.width;
        final int right = x + width;
        if (right > rightEdge) {
            x += rightEdge - right - 1;
        }
        drawRect(x, y, x + width, y - height, -872415232);
        this.drawString(this.mc.fontRenderer, header, x + this.padding / 2, y - h * 3 + this.padding / 2, -13312);
        this.drawString(this.mc.fontRenderer, message, x + this.padding / 2, y - h * 2 + this.padding / 2, -1);
        this.drawString(this.mc.fontRenderer, cost, x + this.padding / 2, y - h + this.padding / 2, -16724737);
        this.drawBorder(x, y, width, height, -1);
    }

    private void drawBorder(final int x, final int y, final int width, final int height, final int borderColor) {
        this.drawHorizontalLine(x - 1, x + width, y, borderColor);
        this.drawHorizontalLine(x - 1, x + width, y - height, borderColor);
        this.drawVerticalLine(x - 1, y, y - height, borderColor);
        this.drawVerticalLine(x + width, y, y - height, borderColor);
    }
}