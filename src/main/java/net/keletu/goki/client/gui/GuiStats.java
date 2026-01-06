package net.keletu.goki.client.gui;

import net.keletu.goki.GokiStats;
import net.keletu.goki.handler.PacketStatAlter;
import net.keletu.goki.lib.Helper;
import net.keletu.goki.stats.Stat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.util.vector.Vector2f;

public class GuiStats extends GuiScreen {
    private EntityPlayer player;
    public static final int STATUS_BUTTON_WIDTH = 24;
    public static final int STATUS_BUTTON_HEIGHT = 24;
    public static float SCALE = 1.0F;
    private static final int HORIZONTAL_SPACING = 8;
    private static final int VERTICAL_SPACING = 12;
    public static final int IMAGE_ROWS = 10;
    private static final int[] COLUMNS = new int[]{4, 3, 5, 3, 5};
    private int currentColumn;
    private int currentRow;
    private GuiStatTooltip toolTip;
    private FontRenderer fontRenderer;

    public GuiStats(final EntityPlayer player) {
        this.player = null;
        this.currentColumn = 0;
        this.currentRow = 0;
        this.toolTip = null;
        this.player = player;
        this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float par3) {
        int ttx = 0;
        int tty = 0;
        this.toolTip = null;
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, par3);
        for (int i = 0; i < this.buttonList.size(); ++i) {
            if (this.buttonList.get(i) instanceof GuiStatButton) {
                final GuiStatButton button = (GuiStatButton) this.buttonList.get(i);
                if (button.isUnderMouse(mouseX, mouseY)) {
                    this.toolTip = new GuiStatTooltip(Stat.stats.get(i), this.player);
                    ttx = button.x + 12;
                    tty = button.y - 1;
                    break;
                }
            }
        }
        this.drawCenteredString(this.fontRenderer, "Current XP: " + Helper.getXPTotal(this.player.experienceLevel, this.player.experience) + "xp", this.width / 2, this.height - 16, -1);
        if (this.toolTip != null) {
            this.toolTip.draw(ttx, tty, 0);
        }
    }

    @Override
    public void initGui() {
        for (int stat = 0; stat < Stat.totalStats; ++stat) {
            final Vector2f pos = this.getButtonPosition(stat);
            this.buttonList.add(new GuiStatButton(stat, (int) pos.x, (int) pos.y, 24, 24, Stat.stats.get(stat), this.player));
            ++this.currentColumn;
            if (this.currentColumn >= GuiStats.COLUMNS[this.currentRow]) {
                ++this.currentRow;
                this.currentColumn = 0;
            }
            if (this.currentRow >= GuiStats.COLUMNS.length) {
                this.currentRow = GuiStats.COLUMNS.length - 1;
            }
        }
    }

    private Vector2f getButtonPosition(final int n) {
        final Vector2f vec = new Vector2f();
        final int columns = GuiStats.COLUMNS[this.currentRow];
        final int x = n % columns;
        final int y = this.currentRow;
        final int rows = GuiStats.COLUMNS.length;
        final int amount = columns;
        final float width = amount * 32 * GuiStats.SCALE;
        final float height = rows * 36 * GuiStats.SCALE;
        vec.x = width / amount * x + (this.width - width + 8.0f) / 2.0f;
        vec.y = height / rows * y + (this.height - height + 12.0f) / 2.0f;
        return vec;
    }

    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.id >= 0 && button.id <= Stat.totalStats && button instanceof GuiStatButton) {
            final GuiStatButton statButton = (GuiStatButton) button;
            if (!GuiScreen.isCtrlKeyDown()) {
                GokiStats.packetPipeline.sendToServer(new PacketStatAlter(Stat.stats.indexOf(statButton.stat), 1));
            } else {
                GokiStats.packetPipeline.sendToServer(new PacketStatAlter(Stat.stats.indexOf(statButton.stat), -1));
            }
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}
