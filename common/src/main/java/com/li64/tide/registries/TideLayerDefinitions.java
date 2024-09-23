package com.li64.tide.registries;

import com.li64.tide.registries.entities.misc.fishing.TideFishingBobberLayer;
import com.li64.tide.registries.entities.misc.fishing.TideFishingHookModel;
import com.li64.tide.registries.entities.models.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

import java.util.ArrayList;
import java.util.function.Supplier;

public class TideLayerDefinitions {
    public static final ArrayList<Registration> REGISTRY = new ArrayList<>();

    public record Registration(ModelLayerLocation location, Supplier<LayerDefinition> layerDefinition) {}

    public static void init() {
        registerLayerDefinition(TideFishingHookModel.LAYER_LOCATION, TideFishingHookModel::createBodyLayer);
        registerLayerDefinition(TideFishingBobberLayer.LAYER_LOCATION, TideFishingHookModel::createBodyLayer);

        registerSimpleFishVariant(SimpleFishModel.Variant.NOSE_NORMAL);
        registerSimpleFishVariant(SimpleFishModel.Variant.NOSE_UPPER);
        registerSimpleFishVariant(SimpleFishModel.Variant.NOSE_LOWER);
        registerSimpleFishVariant(SimpleFishModel.Variant.NOSE_FULL);
        registerLayerDefinition(GuppyModel.LAYER_LOCATION, GuppyModel::createBodyLayer);
        registerLayerDefinition(CatfishModel.LAYER_LOCATION, CatfishModel::createBodyLayer);
        registerLayerDefinition(AngelfishModel.LAYER_LOCATION, AngelfishModel::createBodyLayer);
        registerLayerDefinition(BarracudaModel.LAYER_LOCATION, BarracudaModel::createBodyLayer);
        registerLayerDefinition(SailfishModel.LAYER_LOCATION, SailfishModel::createBodyLayer);
    }

    private static void registerSimpleFishVariant(SimpleFishModel.Variant variant) {
        registerLayerDefinition(variant.modelLocation(), () -> SimpleFishModel.createBodyLayer(variant));
    }

    public static void registerLayerDefinition(ModelLayerLocation location, Supplier<LayerDefinition> layerDefinition) {
        REGISTRY.add(new Registration(location, layerDefinition));
    }
}
