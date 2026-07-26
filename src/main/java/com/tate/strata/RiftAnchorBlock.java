package com.tate.strata;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * The Rift Anchor: a dormant frame buried in Excavation Sites.
 * Striking it with a Seed Shard opens a passage into Stratum I --
 * the same coordinates, an older world.
 */
public class RiftAnchorBlock extends Block {

    public static final ResourceKey<Level> STRATUM_ONE = ResourceKey.create(
            Registries.DIMENSION,
            Identifier.fromNamespaceAndPath(STRATA.MODID, "stratum_one"));

    public RiftAnchorBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                          Player player, InteractionHand hand, BlockHitResult hit) {
        if (!stack.is(STRATA.SEED_SHARD.get())) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        if (!(player instanceof ServerPlayer serverPlayer) || !(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.CONSUME;
        }

        // The rift runs both ways: into the stratum, and back out of it.
        ResourceKey<Level> destinationKey =
                serverLevel.dimension().equals(STRATUM_ONE) ? Level.OVERWORLD : STRATUM_ONE;

        ServerLevel destination = serverLevel.getServer().getLevel(destinationKey);
        if (destination == null) {
            STRATA.LOGGER.warn("Rift Anchor could not resolve destination dimension {}", destinationKey.identifier());
            return InteractionResult.CONSUME;
        }

        // The destination chunk may not exist yet. Force it to generate first --
        // querying the heightmap of an ungenerated chunk returns the world bottom,
        // which drops the player out of the world.
        destination.getChunk(pos.getX() >> 4, pos.getZ() >> 4);

        // Same X/Z -- that is the whole point. Only the era changes.
        BlockPos landing = destination.getHeightmapPos(
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos);
        int safeY = Math.min(
                Math.max(landing.getY(), destination.getMinY() + 1),
                destination.getMaxY() - 1);
        Vec3 target = new Vec3(landing.getX() + 0.5D, safeY, landing.getZ() + 0.5D);

        serverLevel.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 0.8F, 0.5F);

        serverPlayer.teleport(new TeleportTransition(
                destination, target, Vec3.ZERO,
                serverPlayer.getYRot(), serverPlayer.getXRot(),
                TeleportTransition.DO_NOTHING));

        destination.playSound(null, BlockPos.containing(target), SoundEvents.BEACON_ACTIVATE,
                SoundSource.BLOCKS, 0.8F, 0.5F);

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return InteractionResult.SUCCESS;
    }
}
