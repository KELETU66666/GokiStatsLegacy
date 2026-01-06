package net.keletu.goki.client.gui;

import net.keletu.goki.lib.Helper;
import net.keletu.goki.lib.Reference;
import net.keletu.goki.stats.Stat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

public class GuiStatButton extends GuiButton {
    public Stat stat;
    public EntityPlayer player;

    public GuiStatButton(final int id, final int x, final int y, final int width, final int height, final Stat stat, final EntityPlayer player) {
        super(id, x, y, width, height, "");
        this.stat = stat;
        this.player = player;
    }

    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY, float partialTicks) {
        if (this.visible) {
            int iconu = 0;
            final int iconv = 24 * (this.stat.imageID % 10);
            final int level = Helper.getPlayerStatLevel(this.player, this.stat);
            final int cost = this.stat.getCost(level);
            final int playerXP = Helper.getXPTotal(this.player);
            String message = level + "";
            int messageColor = 16777215;
            final FontRenderer fontrenderer = mc.fontRenderer;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.hovered = this.isUnderMouse(mouseX, mouseY);
            final int which = this.getHoverState(this.hovered);
            if (which == 2) {
                iconu = 24;
            }
            if (playerXP < cost) {
                iconu = 48;
            }
            if (level >= this.stat.getLimit()) {
                iconu = 72;
            }
            if (!this.stat.enabled) {
                iconu = 48;
                message = "X";
            }
            if (level >= this.stat.getLimit()) {
                message = "*" + level + "*";
                messageColor = 16763904;
            }
            iconu += this.stat.imageID % 20 / 10 * 24 * 4;
            if (this.stat.imageID >= 20) {
                mc.getTextureManager().bindTexture(Reference.RPG_ICON_2_TEXTURE_LOCATION);
            } else {
                mc.getTextureManager().bindTexture(Reference.RPG_ICON_TEXTURE_LOCATION);
            }
            GlStateManager.pushMatrix();
            GlStateManager.translate((float) this.x, (float) this.y, 0.0f);
            GlStateManager.scale(GuiStats.SCALE, GuiStats.SCALE, 0.0f);
            this.drawTexturedModalRect(0, 0, iconu, iconv, this.width, this.height);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.translate((float) this.x, (float) this.y, 0.0f);
            this.drawCenteredString(fontrenderer, message, (int) (this.width / 2 * GuiStats.SCALE), (int) (this.height * GuiStats.SCALE) + 2, messageColor);
            GlStateManager.popMatrix();
        }
    }

    public boolean isUnderMouse(final int mouseX, final int mouseY) {
        return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width * GuiStats.SCALE && mouseY < this.y + this.height * GuiStats.SCALE;
    }

    @Override
    public boolean mousePressed(final Minecraft par1Minecraft, final int mouseX, final int mouseY) {
        return this.enabled && this.visible && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width * GuiStats.SCALE && mouseY < this.y + this.height * GuiStats.SCALE;
    }

    public String getHoverMessage(final int which) {
        if (which == 0) {
            return this.stat.name + " L" + Helper.getPlayerStatLevel(this.player, this.stat);
        }
        return this.stat.getAppliedDescriptionString(this.player);
    }
}