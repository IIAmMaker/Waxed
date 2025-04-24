package net.im_maker.waxed.common.player.interactions;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.im_maker.waxed.Waxed;
import net.im_maker.waxed.common.util.WTags;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Waxed.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ShiningEffectForWaxedBlocks {

    private static final Set<BlockPos> renderedBlocks = new HashSet<>();
    private static final int RENDER_RADIUS = 5;

    // Pattern for texture cycles: 0, 1, 2, 3, 3, 3, 2, 4
    private static final int[] textureCycle = {1, 2, 3, 4, 5, 6, 7, 8 ,9 ,10 ,6, 6, 6, 6 ,6, 6, 6, 6 ,6, 6, 6, 6 ,6, 6, 6, 6};
    private static int tickCounter = 0; // To keep track of the tick and texture cycle

    @SubscribeEvent
    public static void onRenderLevelLast(RenderLevelStageEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || mc.level == null || mc.isPaused() || event.getStage() != RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) return;

        ItemStack heldItem = player.getMainHandItem();
        ItemStack offhandItem = player.getOffhandItem();
        if (!(heldItem.is(WTags.Items.CAN_WAX) || offhandItem.is(WTags.Items.CAN_WAX))) return;

        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 cameraPos = camera.getPosition();
        double camX = cameraPos.x();
        double camY = cameraPos.y();
        double camZ = cameraPos.z();

        PoseStack poseStack = event.getPoseStack();
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

                    // Avoid redundant rendering.
                    if (!renderedBlocks.add(blockPos)) continue;
                    if (!isItWaxedBlock(blockState)) continue;
                    if (!isBlockInFrustum(blockPos, mc.levelRenderer)) continue;

                    double ddx = blockPos.getX() - camX;
                    double ddy = blockPos.getY() - camY;
                    double ddz = blockPos.getZ() - camZ;
                    double blockDistanceSquared = ddx * ddx + ddy * ddy + ddz * ddz;
                    if (blockDistanceSquared > 1024.0D) continue;

                    // Check if this block is closer to the player than the current closest block
                    if (blockDistanceSquared < closestDistanceSquared) {
                        closestBlockPos = blockPos;
                        closestDistanceSquared = blockDistanceSquared;
                    }

                    // Render shining effect.
                    poseStack.pushPose();
                    poseStack.translate(
                            blockPos.getX() - camX,
                            blockPos.getY() - camY,
                            blockPos.getZ() - camZ
                    );

                    if (tickCounter % 80 == 0) {
                        ParticleUtils.spawnParticlesOnBlockFaces(mc.level, blockPos, ParticleTypes.WAX_OFF, UniformInt.of(0, 1));
                    }

                    if (!isCutoutBlock(blockState)) {
                        PoseStack.Pose matrixPose = poseStack.last();
                        int cycleIndex = textureCycle[(tickCounter / 10) % textureCycle.length];
                        ResourceLocation shineTexture = new ResourceLocation("waxed", "textures/block/shining/shining_" + cycleIndex + ".png");
                        VertexConsumer vertexConsumer = new SheetedDecalTextureGenerator(
                                bufferSource.getBuffer(WRenderType.shining(shineTexture)),
                                matrixPose.pose(),
                                matrixPose.normal(),
                                1.0F
                        );
                        renderShineEffect(poseStack, blockState, blockPos, mc, vertexConsumer);
                    }
                    poseStack.popPose();
                }
            }
        }

        // Use the closest block
        if (closestBlockPos != null && mc.level.isClientSide) {
            int cycleIndex = textureCycle[(tickCounter / 10) % textureCycle.length];
            Level level = mc.level;

            // Play sound at the closest block if conditions are met
            if (cycleIndex == 5 || cycleIndex == 10) {
                level.playSound(player, closestBlockPos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }

        bufferSource.endBatch();
        renderedBlocks.clear(); // Clear set for next frame.

        // Increment tick counter every frame
        tickCounter++;
    }

    private static Boolean isItWaxedBlock (BlockState blockState) {
        String blockId = BuiltInRegistries.BLOCK.getKey(blockState.getBlock()).getPath();
        return blockId.contains("waxed_");
    }

    private static void renderShineEffect(PoseStack poseStack, BlockState blockState, BlockPos blockPos, Minecraft mc, VertexConsumer vertexConsumer) {
        var modelData = mc.level.getModelDataManager().getAt(blockPos);
        mc.getBlockRenderer().renderBreakingTexture(blockState, blockPos, mc.level, poseStack, vertexConsumer, modelData == null ? ModelData.EMPTY : modelData);
    }

    private static boolean isBlockInFrustum(BlockPos blockPos, LevelRenderer levelRenderer) {
        @Nullable Frustum frustum = levelRenderer.getFrustum();
        return frustum != null && frustum.isVisible(new AABB(blockPos));
    }

    private static boolean isCutoutBlock(BlockState blockState) {
        return Minecraft.getInstance().getBlockRenderer().getBlockModel(blockState).getRenderTypes(blockState, RandomSource.create(), ModelData.EMPTY)
                .contains(RenderType.cutout());
    }
}