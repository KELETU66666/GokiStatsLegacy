package net.infstudio.goki.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiExtendedButton extends GuiButton {
    private int backgroundColor;
    private int borderColor;
    private boolean pressed;

    public GuiExtendedButton(final int id, final int x, final int y, final int width, final int height, final String text, final int color) {
        super(id, x, y, width, height, text);
        this.borderColor = -16777216;
        this.pressed = false;
        this.backgroundColor = color;
    }

    @Override
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY, float partialTicks) {
        if (this.enabled) {
            if (!this.isUnderMouse(mouseX, mouseY)) {
                this.drawIdle(mc, mouseX, mouseY);
            } else if (this.pressed) {
                this.drawDown(mc, mouseX, mouseY);
            } else {
                this.drawHover(mc, mouseX, mouseY);
            }
        } else {
            this.drawDisabled(mc, mouseX, mouseY);
        }
    }

    public boolean isUnderMouse(final int mouseX, final int mouseY) {
        return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
    }

    private void drawBorder() {
        this.drawHorizontalLine(-1, this.width, 0, this.borderColor);
        this.drawHorizontalLine(-1, this.width, this.height, this.borderColor);
        this.drawVerticalLine(-1, 0, this.height, this.borderColor);
        this.drawVerticalLine(this.width, 0, this.height, this.borderColor);
    }

    private void drawDisabled(final Minecraft mc, final int mouseX, final int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) this.x, (float) this.y, 0.0f);
        Gui.drawRect(0, 0, this.width, this.height, -2011028958);
        this.drawCenteredString(mc.fontRenderer, this.displayString, this.width / 2, this.height / 2 - mc.fontRenderer.FONT_HEIGHT / 2 + 1, 3355443);
        this.drawBorder();
        GlStateManager.popMatrix();
    }

    private void drawIdle(final Minecraft mc, final int mouseX, final int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) this.x, (float) this.y, 0.0f);
        Gui.drawRect(0, 0, this.width, this.height, this.backgroundColor - 2013265920);
        this.drawCenteredString(mc.fontRenderer, this.displayString, this.width / 2, this.height / 2 - mc.fontRenderer.FONT_HEIGHT / 2 + 1, 16777215);
        this.drawBorder();
        GlStateManager.popMatrix();
    }

    private void drawHover(final Minecraft mc, final int mouseX, final int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) this.x, (float) this.y, 0.0f);
        Gui.drawRect(0, 0, this.width, this.height, this.backgroundColor - 16777216);
        this.drawCenteredString(mc.fontRenderer, this.displayString, this.width / 2, this.height / 2 - mc.fontRenderer.FONT_HEIGHT / 2 + 1, 16763904);
        this.drawBorder();
        GlStateManager.popMatrix();
    }

    private void drawDown(final Minecraft mc, final int mouseX, final int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) this.x, (float) this.y, 0.0f);
        Gui.drawRect(0, 0, this.width, this.height, -16777216);
        this.drawCenteredString(mc.fontRenderer, this.displayString, this.width / 2, this.height / 2 - mc.fontRenderer.FONT_HEIGHT / 2 + 1, 16763904);
        this.drawBorder();
        GlStateManager.popMatrix();
    }

    public void setBackgroundColor(final int color) {
        this.backgroundColor = color;
    }

    public void onPressed() {
        this.pressed = true;
    }

    public void onReleased() {
        this.pressed = false;
    }

    public boolean isPressed() {
        return this.pressed;
    }
}