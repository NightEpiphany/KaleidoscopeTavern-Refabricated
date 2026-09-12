package com.github.ysbbbbbb.kaleidoscopetavern.client;

import net.minecraft.client.resources.model.cuboid.CuboidModel;
import net.minecraft.client.resources.model.cuboid.UnbakedCuboidGeometry;
import net.minecraft.core.Direction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GrapevineModelTest {
    static Stream<String> models() {
        return Stream.of("grapevine_trellis", "ice_grapevine_trellis", "gold_grapevine_trellis")
                .flatMap(variant -> List.of("single_stage0", "single_stage1", "single_stage2", "single_stage3",
                        "north_south", "east_west", "cross_north_south", "cross_east_west",
                        "cross_up_down", "six_direction").stream().map(shape -> variant + "/" + shape));
    }

    @ParameterizedTest(name = "{0}: Minecraft parses plant lighting")
    @MethodSource("models")
    void leafLightingMatchesVanillaCross(String name) throws Exception {
        var cross = readModel("/assets/minecraft/models/block/cross.json");
        var model = readModel("/assets/kaleidoscope_tavern/models/block/plant/" + name + ".json");
        assertEquals(cross.ambientOcclusion(), model.ambientOcclusion(), "Plant ambient occlusion");
        var reference = ((UnbakedCuboidGeometry) cross.geometry()).elements().getFirst();
        var geometry = assertInstanceOf(UnbakedCuboidGeometry.class, model.geometry());
        int leaves = 0;
        int supports = 0;
        for (var element : geometry.elements()) {
            boolean plane = element.from().x() == element.to().x()
                    || element.from().y() == element.to().y()
                    || element.from().z() == element.to().z();
            if (plane) {
                leaves++;
                assertEquals(Direction.UP, reference.shadeDirectionOverride());
                assertEquals(reference.shadeDirectionOverride(), element.shadeDirectionOverride(),
                        "Leaves must use the current Minecraft shading field");
                assertEquals(reference.lightEmission(), element.lightEmission(), "Leaves must not glow");
            } else {
                supports++;
                assertNull(element.shadeDirectionOverride(), "Wood retains directional shading");
            }
        }
        assertEquals(name.contains("single_stage") ? 2 : 6, leaves, "Leaf geometry coverage");
        assertTrue(supports > 0, "Wooden support geometry exists");
    }

    private static CuboidModel readModel(String path) throws Exception {
        var stream = GrapevineModelTest.class.getResourceAsStream(path);
        assertNotNull(stream, path);
        try (var reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return CuboidModel.fromStream(reader);
        }
    }
}
