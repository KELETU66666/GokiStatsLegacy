package net.infstudio.goki.client.gui;

import net.infstudio.goki.ToolSpecificStat;
import net.infstudio.goki.lib.Reference;
import net.infstudio.goki.stats.Stat;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class GuiCompatibilityHelper extends GuiScreen {
    private EntityPlayer player;
    private final int BUTTON_WIDTH = 240;
    private final int BUTTON_HEIGHT = 15;
    private final ToolSpecificStat[] compatibleStats;

    public GuiCompatibilityHelper(final EntityPlayer player) {
        this.player = null;
        this.compatibleStats = new ToolSpecificStat[]{Stat.STAT_MINING, Stat.STAT_DIGGING, Stat.STAT_CHOPPING, Stat.STAT_TRIMMING, Stat.STAT_SWORDSMANSHIP, Stat.STAT_BOWMANSHIP};
        this.player = player;
    }

    @Override
    public void initGui() {
        int button = 0;
        final int x = this.width / 2;
        final int y = this.height / 2 - 45;
        this.buttonList.add(new GuiExtendedButton(button, x - 120, y - 15 + 15 * button++, 240, 15, "Add item to Mining list", 3355443));
        this.buttonList.add(new GuiExtendedButton(button, x - 120, y - 15 + 15 * button++, 240, 15, "Add item to Digging list", 3355443));
        this.buttonList.add(new GuiExtendedButton(button, x - 120, y - 15 + 15 * button++, 240, 15, "Add item to Chopping list", 3355443));
        this.buttonList.add(new GuiExtendedButton(button, x - 120, y - 15 + 15 * button++, 240, 15, "Add item to Trimming list", 3355443));
        this.buttonList.add(new GuiExtendedButton(button, x - 120, y - 15 + 15 * button++, 240, 15, "Add item to Swordsmanship list", 3355443));
        this.buttonList.add(new GuiExtendedButton(button, x - 120, y - 15 + 15 * button++, 240, 15, "Add item to Bowmanship list", 3355443));
        this.checkStatus();
    }

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        super.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.mc.fontRenderer, "This is CLIENT-SIDE. Copy the config to the server and /reloadGokiStats.", this.width / 2, 16, 16777215);
    }

    @Override
    protected void actionPerformed(final GuiButton button) {
        if (this.compatibleStats[button.id].isAffectedByStat(this.player.getHeldItemMainhand()) != 0) {
            this.compatibleStats[button.id].removeSupportForItem(this.player.getHeldItemMainhand());
        } else {
            this.compatibleStats[button.id].addSupportForItem(this.player.getHeldItemMainhand());
        }
        Reference.configuration.save();
        this.checkStatus();
    }

    public void checkStatus() {
        Reference.configuration.load();
        for (int i = 0; i < this.compatibleStats.length; ++i) {
            if (this.compatibleStats[i].isAffectedByStat(this.player.getHeldItemMainhand()) != 0) {
                this.buttonList.get(i).displayString = "Remove item from " + this.compatibleStats[i].name + " list.";
                ((GuiExtendedButton) this.buttonList.get(i)).setBackgroundColor(3381555);
            } else {
                this.buttonList.get(i).displayString = "Add item to " + this.compatibleStats[i].name + " list.";
                ((GuiExtendedButton) this.buttonList.get(i)).setBackgroundColor(10040115);
            }
        }
    }

    @Override
    public void onGuiClosed() {
        Reference.configuration.save();
    }
}
