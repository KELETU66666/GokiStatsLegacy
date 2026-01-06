package net.keletu.goki;

import net.keletu.goki.client.gui.GuiInventoryTab;
import net.keletu.goki.handler.PacketStatAlter;
import net.keletu.goki.handler.PacketSyncStatConfig;
import net.keletu.goki.lib.Helper;
import net.keletu.goki.lib.IDMDTuple;
import net.keletu.goki.lib.Reference;
import net.keletu.goki.stats.Stat;
import net.keletu.goki.stats.StatMiningMagician;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class Events {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void inventoryInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiInventory /*&& GokiConfig.inventoryIconEnabled*/)
            event.getButtonList().add(new GuiInventoryTab(2333, (event.getGui().width / 2) + Reference.configuration.get("Gui", "Icon X offset", -51).getInt(), (event.getGui().height / 2) - 111, ""));
    }

    @SubscribeEvent
    public void harvestBlock(final BlockEvent.HarvestDropsEvent event) {
        final EntityPlayer player = event.getHarvester();
        if (player != null) {
            if (Helper.getPlayerStatLevel(player, Stat.STAT_TREASURE_FINDER) > 0) {
                boolean treasureFound = false;
                final Random random = player.getRNG();
                final List<ItemStack> items = Stat.STAT_TREASURE_FINDER.getApplicableItemStackList(event.getState(), Helper.getPlayerStatLevel(player, Stat.STAT_TREASURE_FINDER));
                final List<Integer> chances = Stat.STAT_TREASURE_FINDER.getApplicableChanceList(event.getState(), Helper.getPlayerStatLevel(player, Stat.STAT_TREASURE_FINDER));
                for (int i = 0; i < items.size(); ++i) {
                    final Integer roll = random.nextInt(10000);
                    if (roll <= chances.get(i)) {
                        if (items.get(i) != null) {
                            event.getDrops().add(items.get(i));
                            treasureFound = true;
                        } else {
                            System.out.println("Tried to add an item from Treasure Finder, but it failed!");
                        }
                    }
                }
                if (treasureFound) {
                    player.world.playSound(null, player.getPosition(), new SoundEvent(new ResourceLocation(Reference.MODID, "treasure")), SoundCategory.PLAYERS, 1.0f, 1.0f);
                }
            }
            if (Helper.getPlayerStatLevel(player, Stat.STAT_MINING_MAGICIAN) > 0) {
                boolean magicHappened = false;
                final IDMDTuple mme = new IDMDTuple(event.getState().getBlock(), event.getState().getBlock().getMetaFromState(event.getState()));
                if (Stat.STAT_MINING_MAGICIAN.isAffectedByStat(mme) != 0) {
                    for (int j = 0; j < event.getDrops().size(); ++j) {
                        if (player.getRNG().nextDouble() * 100.0 <= Stat.STAT_MINING_MAGICIAN.getBonus(player)) {
                            final ItemStack item = event.getDrops().get(j);
                            if (item.getItem() instanceof ItemBlock && ItemBlock.getIdFromItem(item.getItem()) == Block.getIdFromBlock(event.getState().getBlock())) {
                                if (item.getMetadata() == event.getState().getBlock().getMetaFromState(event.getState())) {
                                    final int randomEntry = player.getRNG().nextInt(StatMiningMagician.blockEntries.size());
                                    final IDMDTuple entry = StatMiningMagician.blockEntries.get(randomEntry);
                                    final ItemStack stack = new ItemStack(Item.getItemById(entry.id), 1, entry.md);
                                    stack.setCount(event.getDrops().get(j).getCount());
                                    event.getDrops().set(j, stack);
                                    magicHappened = true;
                                }
                            } else {
                                for (int k = 0; k < StatMiningMagician.itemEntries.size(); ++k) {
                                    final IDMDTuple entry = StatMiningMagician.itemEntries.get(k);
                                    if (Item.getIdFromItem(item.getItem()) == entry.id && item.getMetadata() == entry.md) {
                                        final int randomEntry2 = player.getRNG().nextInt(StatMiningMagician.itemEntries.size());
                                        final IDMDTuple chosenEntry = StatMiningMagician.itemEntries.get(randomEntry2);
                                        final ItemStack stack2 = new ItemStack(Item.getItemById(chosenEntry.id), 1, chosenEntry.md);
                                        stack2.setCount(event.getDrops().get(j).getCount());
                                        event.getDrops().set(j, stack2);
                                        magicHappened = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (magicHappened) {
                        player.world.playSound(null, player.getPosition(), new SoundEvent(new ResourceLocation(Reference.MODID, "magician")), SoundCategory.PLAYERS, 0.3f, 1.0f);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void playerJoinWorld(final EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) event.getEntity();
            if (!player.world.isRemote) {
                GokiStats.packetPipeline.sendTo(new PacketSyncStatConfig(Stat.loseStatsOnDeath, Stat.globalBonusMultiplier, Stat.globalCostMultiplier, Stat.globalLimitMultiplier), (EntityPlayerMP) player);
            } else {
                GokiStats.packetPipeline.sendToServer(new PacketStatAlter(0, 0));
            }
        }
    }

    @SubscribeEvent
    public void playerFall(final LivingFallEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) event.getEntity();
            final int featherFallLevel = Helper.getPlayerStatLevel(player, Stat.STAT_FEATHER_FALL);
            if (event.getDistance() < 3.0 + featherFallLevel * 0.1) {
                event.setDistance(0.0f);
            }
        }
    }

    @SubscribeEvent
    public void playerDead(final LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (Stat.loseStatsOnDeath) {
                for (int stat = 0; stat < Stat.totalStats; ++stat) {
                    Helper.setPlayerStatLevel(player, Stat.stats.get(stat), 0);
                }
            }
        }
    }

    @SubscribeEvent
    public void playerJump(final LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) event.getEntity();
            if (player.isSprinting()) {
                final EntityPlayer entityPlayer = player;
                entityPlayer.motionY *= 1.0f + Stat.STAT_LEAPERV.getBonus(player);
                final EntityPlayer entityPlayer2 = player;
                entityPlayer2.motionX *= 1.0f + Stat.STAT_LEAPERH.getBonus(player);
                final EntityPlayer entityPlayer3 = player;
                entityPlayer3.motionZ *= 1.0f + Stat.STAT_LEAPERH.getBonus(player);
            }
        }
    }

    @SubscribeEvent
    public void playerBreakSpeed(final PlayerEvent.BreakSpeed event) {
        final IBlockAccess world = event.getEntity().world;
        final ItemStack heldItem = event.getEntityPlayer().getHeldItemMainhand();
        final EntityPlayer player = event.getEntityPlayer();
        final BlockPos pos = event.getPos();
        final float multiplier = 1.0f + (Stat.STAT_MINING.isAffectedByStat(world, pos, heldItem) * Stat.STAT_MINING.getBonus(player) + Stat.STAT_DIGGING.isAffectedByStat(world, pos, heldItem) * Stat.STAT_DIGGING.getBonus(player) + Stat.STAT_CHOPPING.isAffectedByStat(world, pos, heldItem) * Stat.STAT_CHOPPING.getBonus(player) + Stat.STAT_TRIMMING.isAffectedByStat(world, pos, heldItem) * Stat.STAT_TRIMMING.getBonus(player));
        event.setNewSpeed(event.getOriginalSpeed() * multiplier);
    }

    @SubscribeEvent
    public void entityHurt(final LivingHurtEvent event) {
        final DamageSource source = event.getSource();
        if (event.getEntity() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) event.getEntity();
            final float damageMultiplier = 1.0f - (Stat.STAT_PROTECTION.getAppliedBonus(player, source) + Stat.STAT_TOUGH_SKIN.getAppliedBonus(player, source) + Stat.STAT_FEATHER_FALL.getAppliedBonus(player, source) + Stat.STAT_TEMPERING.getAppliedBonus(player, source));
            event.setAmount(event.getAmount() * damageMultiplier);
        } else if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            final ItemStack heldItem = player.getHeldItemMainhand();
            final Entity target = event.getEntity();
            final float damage = event.getAmount();
            float bonus = 0.0f;
            if (!player.getHeldItemMainhand().isEmpty()) {
                bonus = (float) Math.round(damage * (Stat.STAT_SWORDSMANSHIP.getAppliedBonus(player, heldItem) + Stat.STAT_BOWMANSHIP.getAppliedBonus(player, heldItem)));
            }
            event.setAmount(bonus + damage);
            if (Stat.STAT_REAPER.isAffectedByStat(target) != 0) {
                final float reap = Stat.STAT_REAPER.getBonus(player);
                final float reapBonus = reap * Stat.STAT_STEALTH.getSecondaryBonus(player) / 100.0f * Stat.STAT_STEALTH.isAffectedByStat(player);
                final float reapChance = reap + reapBonus;
                if (player.getRNG().nextFloat() <= reapChance) {
                    player.onEnchantmentCritical(target);
                    player.world.playSound(null, player.getPosition(), new SoundEvent(new ResourceLocation(Reference.MODID, "reaper")), SoundCategory.PLAYERS, 1.0f, 1.0f);
                    event.setAmount(100000.0f);
                }
            }
        }
    }
}
