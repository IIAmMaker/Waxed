package net.im_maker.waxed.common.util;

import net.im_maker.waxed.Waxed;
import net.im_maker.waxed.config.WaxedAndShinyConfig;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            if (ModList.get().isLoaded("brushstrokes")) {
                onAddPackFindersB(event);
            }
            if (WaxedAndShinyConfig.GENERATE_WAXING_RECIPES.get() && WaxedAndShinyConfig.WAX_AND_WAX_BLOCKS.get() && WaxedAndShinyConfig.WAXED_BLOCKS.get()) {
                onAddPackFindersGWR(event);
            }
            if (WaxedAndShinyConfig.WAX_AND_WAX_BLOCKS.get()) {
                addPackFinder(event, "wax_and_wax_blocks");
            }
            if (WaxedAndShinyConfig.WAXED_BLOCKS.get()) {
                addPackFinder(event, "waxed_blocks");
            }
            if (WaxedAndShinyConfig.TALL_CANDLES.get()) {
                addPackFinder(event, "tall_candles");
            }
            if (WaxedAndShinyConfig.CANDLE_VARIANTS.get()) {
                addPackFinder(event, "candle_variants");
                if (WaxedAndShinyConfig.WAX_AND_WAX_BLOCKS.get()) addPackFinder(event, "wax_variants_blocks");
                if (WaxedAndShinyConfig.TALL_CANDLES.get()) addPackFinder(event, "tall_candle_variants");
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

    private static void onAddPackFindersB(AddPackFindersEvent event) {
        IModFileInfo mod = ModList.get().getModFileById(Waxed.MOD_ID);
        Path datapack_file = mod.getFile().findResource("resourcepacks/waxed_brushstrokes_compat");
        event.addRepositorySource(packConsumer -> packConsumer.accept(
                Pack.create(
                        "waxed_brushstrokes_compat",
                        Component.literal("Waxed & Shiny Brushstrokes Compat"),
                        true,
                        (path) -> new PathPackResources(path, datapack_file, true),
                        new Pack.Info(
                                Component.literal("Waxed & Shiny Brushstrokes Compat"),
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

    private static void addPackFinder(AddPackFindersEvent event, String s) {
        Pattern p = Pattern.compile("\\b\\w");
        Matcher m = p.matcher(s.toLowerCase());
        String r = m.replaceAll(match -> match.group().toUpperCase()).replace('_', ' ');
        for (int i =0;i<700;i++) System.out.println(s);
        for (int i =0;i<700;i++) System.out.println(r);
        IModFileInfo mod = ModList.get().getModFileById(Waxed.MOD_ID);
        Path datapack_file = mod.getFile().findResource("resourcepacks/waxed_" + s);
        event.addRepositorySource(packConsumer -> packConsumer.accept(
                Pack.create(
                        "waxed_" + s,
                        Component.literal("Waxed & Shiny " + r),
                        true,
                        (path) -> new PathPackResources(path, datapack_file, true),
                        new Pack.Info(
                                Component.literal("Waxed & Shiny " + r ),
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

    private static void onAddPackFindersGWR(AddPackFindersEvent event) {
        IModFileInfo mod = ModList.get().getModFileById(Waxed.MOD_ID);
        Path datapack_file = mod.getFile().findResource("resourcepacks/waxed_generated_recipes");
        event.addRepositorySource(packConsumer -> packConsumer.accept(
                Pack.create(
                        "waxed_generated_recipes",
                        Component.literal("Waxed & Shiny"),
                        true,
                        (path) -> new PathPackResources(path, datapack_file, true),
                        new Pack.Info(
                                Component.literal("Waxed & Shiny"),
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
