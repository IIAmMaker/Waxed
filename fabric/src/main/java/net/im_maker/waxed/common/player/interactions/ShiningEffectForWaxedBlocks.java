package net.im_maker.waxed.common.player.interactions;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.im_maker.waxed.common.util.WTags;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;

public class ShiningEffectForWaxedBlocks {

    private static final Set<BlockPos> renderedBlocks = new HashSet<>();
    private static final int RENDER_RADIUS = 5;
    private static final int[] textureCycle = {1, 2, 3, 4, 5, 6, 7, 8 ,9 ,10 ,6, 6, 6, 6 ,6, 6, 6, 6 ,6, 6, 6, 6 ,6, 6, 6, 6};
    private static int tickCounter = 0;

    public static void register() {
        WorldRenderEvents.AFTER_TRANSLUCENT.register(ShiningEffectForWaxedBlocks::render);
    }

    private static void render(WorldRenderContext context) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || mc.level == null || mc.isPaused()) return;

        ItemStack heldItem = player.getMainHandItem();
        ItemStack offhandItem = player.getOffhandItem();
        if (!(heldItem.is(WTags.Items.CAN_WAX) || offhandItem.is(WTags.Items.CAN_WAX))) return;

        Camera camera = context.camera();
        Vec3 cameraPos = camera.getPosition();
        double camX = cameraPos.x();
        double camY = cameraPos.y();
        double camZ = cameraPos.z();

        PoseStack poseStack = context.matrixStack();
        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();

        BlockPos playerPos = player.blockPosition();
        BlockPos closestBlockPos = null;
        double closestDistanceSquared = Double.MAX_VALUE;

        for (int dx = -RENDER_RADIUS; dx <= RENDER_RADIUS; dx++) {
            for (int dy = -RENDER_RADIUS; dy <= RENDER_RADIUS; dy++) {
                for (int dz = -RENDER_RADIUS; dz <= RENDER_RADIUS; dz++) {
                    double distanceSquared = dx * dx + dy * dy + dz * dz;
                    if (distanceSquared > (RENDER_RADIUS + 0.5) * (RENDER_RADIUS + 0.5)) continue;
                    BlockPos blockPos = playerPos.offset(dx, dy, dz);
                    BlockState blockState = mc.level.getBlockState(blockPos);
                    if (!isItWaxedBlock(blockState)) continue;
                    double ddx = blockPos.getX() - camX;
                    double ddy = blockPos.getY() - camY;
                    double ddz = blockPos.getZ() - camZ;
                    double blockDistanceSquared = ddx * ddx + ddy * ddy + ddz * ddz;
                    if (blockDistanceSquared > 1024.0D) continue;
                    if (blockDistanceSquared < closestDistanceSquared) {
                        closestBlockPos = blockPos;
                        closestDistanceSquared = blockDistanceSquared;
                    }
                    poseStack.pushPose();
                    poseStack.translate(
                            blockPos.getX() - camX,
                            blockPos.getY() - camY,
                            blockPos.getZ() - camZ
                    );
                    if (tickCounter % 80 == 0) {
                        ParticleUtils.spawnParticlesOnBlockFaces(mc.level, blockPos, ParticleTypes.WAX_OFF, UniformInt.of(0, 1));
                    }
                    poseStack.popPose();
                }
            }
        }

        if (closestBlockPos != null && mc.level.isClientSide) {
            int cycleIndex = textureCycle[(tickCounter / 10) % textureCycle.length];
            Level level = mc.level;

            if (cycleIndex == 5 || cycleIndex == 10) {
                level.playSound(player, closestBlockPos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }

        bufferSource.endBatch();
        renderedBlocks.clear();
        tickCounter++;
    }

    private static Boolean isItWaxedBlock(BlockState blockState) {
        String blockId = BuiltInRegistries.BLOCK.getKey(blockState.getBlock()).getPath();
        return blockId.contains("waxed_");
    }
}