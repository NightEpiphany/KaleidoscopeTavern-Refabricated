package com.github.ysbbbbbb.kaleidoscopetavern.client;

import com.github.ysbbbbbb.kaleidoscopetavern.block.plant.GrapevineTrellisBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.plant.ITrellis;
import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.TrellisType;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public final class GrapevineLightingGameTest implements FabricClientGameTest {
    @Override
    public void runTest(ClientGameTestContext context) {
        context.runOnClient(client -> {
            var models = client.getModelManager().getBlockStateModelSet();
            var random = RandomSource.create(0);
            var referenceParts = new ArrayList<BlockStateModelPart>();
            var referenceModel = models.get(ModBlocks.WILD_GRAPEVINE_PLANT.defaultBlockState());
            require(referenceModel != models.missingModel(), "Missing wild grapevine model");
            referenceModel.collectParts(random, referenceParts);
            require(!referenceParts.isEmpty(), "Missing wild grapevine parts");
            var reference = quads(referenceParts.getFirst()).getFirst().materialInfo();
            require(reference.shadeDirectionOverride() == Direction.UP, "Unexpected wild grapevine shading");
            int checked = 0;
            for (var block : List.of(ModBlocks.GRAPEVINE_TRELLIS,
                    ModBlocks.ICE_GRAPEVINE_TRELLIS, ModBlocks.GOLD_GRAPEVINE_TRELLIS)) {
                for (var state : block.getStateDefinition().getPossibleStates()) {
                    var model = models.get(state);
                    require(model != models.missingModel(), "Missing model: " + state);
                    var parts = new ArrayList<BlockStateModelPart>();
                    model.collectParts(random, parts);
                    int leafFaces = 0;
                    int woodFaces = 0;
                    for (var part : parts) {
                        require(part.useAmbientOcclusion() == referenceParts.getFirst().useAmbientOcclusion(),
                                "Ambient occlusion differs from wild grapevine: " + state);
                        for (var quad : quads(part)) {
                            var material = quad.materialInfo();
                            if (material.shadeDirectionOverride() == reference.shadeDirectionOverride()) {
                                leafFaces++;
                                require(material.lightEmission() == reference.lightEmission(), "Glowing leaf: " + state);
                                require(material.tintIndex() == reference.tintIndex(), "Unexpected leaf tint: " + state);
                                require(material.layer() == reference.layer(), "Wrong leaf render layer: " + state);
                            } else {
                                woodFaces++;
                                require(material.shadeDirectionOverride() == null, "Wrong wood shading: " + state);
                            }
                        }
                    }
                    require(leafFaces == (state.getValue(ITrellis.TYPE) == TrellisType.SINGLE ? 4 : 12),
                            "Wrong number of correctly shaded leaf faces: " + state + ", got " + leafFaces);
                    require(woodFaces > 0, "Missing wooden support: " + state);
                    checked++;
                }
            }
            require(checked == 3 * TrellisType.values().length * (GrapevineTrellisBlock.MAX_AGE + 1) * 2,
                    "Incomplete block state coverage");
            LoggerFactory.getLogger("GrapevineLightingGameTest").info("Verified leaf lighting for {} baked block states", checked);
        });
    }

    private static List<BakedQuad> quads(BlockStateModelPart part) {
        var result = new ArrayList<>(part.getQuads(null));
        for (var direction : Direction.values()) {
            result.addAll(part.getQuads(direction));
        }
        return result;
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
