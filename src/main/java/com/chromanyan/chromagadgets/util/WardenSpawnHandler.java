package com.chromanyan.chromagadgets.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.phys.Vec3;

public class WardenSpawnHandler {

    // these methods are taken directly from SculkShriekerBlockEntity and adapted to work from a static setting


    /**
     * Responds to a given warning level in the same way a shrieker would. Does nothing at warning level 0,
     * plays a sound at warning levels 1 through 3, and attempts to summon a warden at warning level 4.
     * @param serverLevel The server-side level to perform the effects in.
     * @param blockPos The block position at which to perform the effects at. The sound is played within 10
     *                 blocks of this spot in all directions, and if a Warden spawns, it spawns here.
     * @param warningLevel The warning level to respond to.
     */
    public static void tryRespond(ServerLevel serverLevel, BlockPos blockPos, int warningLevel) {
        if (
                serverLevel.getDifficulty() != Difficulty.PEACEFUL
                        && serverLevel.getGameRules().getBoolean(GameRules.RULE_DO_WARDEN_SPAWNING)
                        && warningLevel > 0
        ) {
            if (!trySummonWarden(serverLevel, blockPos, warningLevel)) {
                playWardenReplySound(warningLevel, blockPos, serverLevel);
            }
            Warden.applyDarknessAround(serverLevel, Vec3.atCenterOf(blockPos), null, 40);
        }
    }

    public static boolean trySummonWarden(ServerLevel serverLevel, BlockPos blockPos, int warningLevel) {
        return warningLevel >= 4 && SpawnUtil.trySpawnMob(EntityType.WARDEN, MobSpawnType.TRIGGERED, serverLevel, blockPos, 20, 5, 6, SpawnUtil.Strategy.ON_TOP_OF_COLLIDER).isPresent();
    }

    public static void playWardenReplySound(int warningLevel, BlockPos blockPos, Level level) {
        SoundEvent soundevent = SculkShriekerBlockEntity.SOUND_BY_LEVEL.get(warningLevel);
        if (soundevent != null) {
            int i = blockPos.getX() + Mth.randomBetweenInclusive(level.random, -10, 10);
            int j = blockPos.getY() + Mth.randomBetweenInclusive(level.random, -10, 10);
            int k = blockPos.getZ() + Mth.randomBetweenInclusive(level.random, -10, 10);
            level.playSound(null, i, j, k, soundevent, SoundSource.HOSTILE, 5.0F, 1.0F);
        }
    }
}
