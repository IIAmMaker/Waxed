package net.im_maker.waxed.common.util;

import net.im_maker.waxed.Waxed;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModFileInfo;

import java.nio.file.Path;

@Mod.EventBusSubscriber(modid = Waxed.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataPackRegistrar {

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            if (ModList.get().isLoaded("dye_depot")) {
                onAddPackFindersDD(event);
            }
            if (ModList.get().isLoaded("supplementaries")) {
                onAddPackFindersS(event);
            }
            if (ModList.get().isLoaded("oreganized")) {
                onAddPackFindersO(event);
            }
        }
    }

    private static void onAddPackFindersDD(AddPackFindersEvent event) {
        IModFileInfo mod = ModList.get().getModFileById(Waxed.MOD_ID);
        Path datapack_file = mod.getFile().findResource("resourcepacks/waxed_dye_depot_compat");
        event.addRepositorySource(packConsumer -> packConsumer.accept(
                Pack.create(
                        "waxed_dye_depot_compat",
                        Component.literal("Waxed & Shiny DyeDepot Compat"),
                        true,
                        (path) -> new PathPackResources(path, datapack_file, true),
                        new Pack.Info(
                                Component.literal("Waxed & Shiny DyeDepot Compat"),
                                SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA),
                                FeatureFlagSet.of()
                        ),
                        PackType.SERVER_DATA,
                        Pack.Position.TOP,
                        true,
                        PackSource.BUILT_IN
                )
        ));
    }

    private static void onAddPackFindersS(AddPackFindersEvent event) {
        IModFileInfo mod = ModList.get().getModFileById(Waxed.MOD_ID);
        Path datapack_file = mod.getFile().findResource("resourcepacks/waxed_supplementaries_compat");
        event.addRepositorySource(packConsumer -> packConsumer.accept(
                Pack.create(
                        "waxed_supplementaries_compat",
                        Component.literal("Waxed & Shiny Supplementaries Compat"),
                        true,
                        (path) -> new PathPackResources(path, datapack_file, true),
                        new Pack.Info(
                                Component.literal("Waxed & Shiny Supplementaries Compat"),
                                SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA),
                                FeatureFlagSet.of()
                        ),
                        PackType.SERVER_DATA,
                        Pack.Position.TOP,
                        true,
                        PackSource.BUILT_IN
                )
        ));
    }

    private static void onAddPackFindersO(AddPackFindersEvent event) {
        IModFileInfo mod = ModList.get().getModFileById(Waxed.MOD_ID);
        Path datapack_file = mod.getFile().findResource("resourcepacks/waxed_oreganized_compat");
        event.addRepositorySource(packConsumer -> packConsumer.accept(
                Pack.create(
                        "waxed_oreganized_compat",
                        Component.literal("Waxed & Shiny Oreganized Compat"),
                        true,
                        (path) -> new PathPackResources(path, datapack_file, true),
                        new Pack.Info(
                                Component.literal("Waxed & Shiny Oreganized Compat"),
                                SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA),
                                FeatureFlagSet.of()
                        ),
                        PackType.SERVER_DATA,
                        Pack.Position.TOP,
                        true,
                        PackSource.BUILT_IN
                )
        ));
    }
}
