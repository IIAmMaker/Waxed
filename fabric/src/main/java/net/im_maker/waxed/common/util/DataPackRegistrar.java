package net.im_maker.waxed.common.util;

import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.im_maker.waxed.Waxed;
import net.im_maker.waxed.config.WaxedAndShinyConfig;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class DataPackRegistrar {

    private static void registerBuiltinDataPack(ModContainer modContainer, String packId, String modName) {
        ResourceManagerHelper.registerBuiltinResourcePack(
                new ResourceLocation(Waxed.MOD_ID, packId),
                modContainer,
                "Waxed & Shiny " + modName + " Compat",
                ResourcePackActivationType.ALWAYS_ENABLED
        );
    }
    private static void registerBuiltinDataPack(ModContainer modContainer, String packId) {
        ResourceManagerHelper.registerBuiltinResourcePack(
                new ResourceLocation(Waxed.MOD_ID, packId),
                modContainer,
                "Waxed & Shiny",
                ResourcePackActivationType.ALWAYS_ENABLED
        );
    }

    public static void loadBuiltinResourcePacks() {
        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(Waxed.MOD_ID);
        if (modContainer.isPresent()) {
            if (FabricLoader.getInstance().isModLoaded("dye_depot")) {
                registerBuiltinDataPack(modContainer.get(), "waxed_dye_depot_compat", "Dye Depot");
            }
        }
        if (modContainer.isPresent()) {
            if (FabricLoader.getInstance().isModLoaded("supplementaries")) {
                registerBuiltinDataPack(modContainer.get(), "waxed_supplementaries_compat", "Supplementaries");
            }
        }
        if (modContainer.isPresent()) {
            WaxedAndShinyConfig CONFIG = AutoConfig.getConfigHolder(WaxedAndShinyConfig.class).getConfig();
            if (CONFIG.generateWaxingRecipes) {
                registerBuiltinDataPack(modContainer.get(), "waxed_generated_recipes");
            }
        }
    }
}