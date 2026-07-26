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
 * The Rift Anchor: a dormant frame sealed inside Excavation Sites.
 *
 * Outward journey  -- strike it with a Seed Shard. The shard is consumed and a
 *                     matching Anchor materialises where you land, so the rift
 *                     is anchored at both ends.
 * Return journey   -- right-click the Anchor in Stratum I with an empty hand.
 *                     Free, deliberately: a player who runs out of Seed Shards
 *                     must never be stranded in a dead world.
 */
public class RiftAnchorBlock extends Block {

    public static final ResourceKey<Level> STRATUM_ONE = ResourceKey.create(
            Registries.DIMENSION,
            Identifier.fromNamespaceAndPath(STRATA.MODID, "stratum_one"));

    public RiftAnchorBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    /** Outward: requires a Seed Shard. */
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

        boolean inStratum = serverLevel.dimension().equals(STRATUM_ONE);
        if (!openRift(serverLevel, serverPlayer, pos, inStratum ? Level.OVERWORLD : STRATUM_ONE)) {
            return InteractionResult.CONSUME;
        }
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return InteractionResult.SUCCESS;
    }

    /** Return: free, but only out of a stratum -- never into one. */
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                                Player player, BlockHitResult hit) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        if (!(player instanceof ServerPlayer serverPlayer) || !(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.PASS;
        }
        if (!serverLevel.dimension().equals(STRATUM_ONE)) {
            // Entering a stratum always costs a Seed Shard.
            return InteractionResult.PASS;
        }
        return openRift(serverLevel, serverPlayer, pos, Level.OVERWORLD)
                ? InteractionResult.SUCCESS
                : InteractionResult.PASS;
    }

    /**
     * Moves the player across at the same X/Z and plants an Anchor at the far end.
     * Returns false if the destination dimension could not be resolved.
     */
    private boolean openRift(ServerLevel from, ServerPlayer player, BlockPos pos, ResourceKey<Level> destKey) {
        ServerLevel destination = from.getServer().getLevel(destKey);
        if (destination == null) {
            STRATA.LOGGER.warn("Rift Anchor could not resolve destination dimension {}", destKey.identifier());
            return false;
        }

        // The destination chunk may not exist yet. Force it to generate first --
        // the heightmap of an ungenerated chunk reports the world bottom, which
        // drops the player out of the world.
        destination.getChunk(pos.getX() >> 4, pos.getZ() >> 4);

        BlockPos surface = destination.getHeightmapPos(
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos);

        int anchorY = Math.min(
                Math.max(surface.getY(), destination.getMinY() + 1),
                destination.getMaxY() - 2);
        BlockPos anchorPos = new BlockPos(surface.getX(), anchorY, surface.getZ());

        // Anchor the far end so the journey is always reversible.
        if (!destination.getBlockState(anchorPos).is(this)) {
            destination.setBlockAndUpdate(anchorPos, defaultBlockState());
        }

        Vec3 target = new Vec3(anchorPos.getX() + 0.5D, anchorPos.getY() + 1.0D, anchorPos.getZ() + 0.5D);

        from.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 0.8F, 0.5F);
        player.teleport(new TeleportTransition(
                destination, target, Vec3.ZERO,
                player.getYRot(), player.getXRot(),
                TeleportTransition.DO_NOTHING));
        destination.playSound(null, anchorPos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 0.8F, 0.5F);
        return true;
    }
}
