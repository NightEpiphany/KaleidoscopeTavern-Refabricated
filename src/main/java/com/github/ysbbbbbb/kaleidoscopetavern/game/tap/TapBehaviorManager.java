package com.github.ysbbbbbb.kaleidoscopetavern.game.tap;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.ITapBehavior;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class TapBehaviorManager {
    private static final Map<Block, ITapBehavior> BEHAVIOR_MAP = Maps.newHashMap();
    private static final List<MatchedBehavior> PREDICATE_BEHAVIORS = Lists.newArrayList();

    /**
     * 按方块注册提取行为（精确匹配方块类型）。
     */
    public static void register(Block block, ITapBehavior behavior) {
        BEHAVIOR_MAP.put(block, behavior);
    }

    /**
     * 按方块状态谓词注册提取行为，用于一类方块（如所有含水方块）。
     * 精确注册的方块优先于谓词匹配。
     */
    public static void register(Predicate<BlockState> matcher, ITapBehavior behavior) {
        PREDICATE_BEHAVIORS.add(new MatchedBehavior(matcher, behavior));
    }

    public static boolean contains(Block block) {
        return BEHAVIOR_MAP.containsKey(block);
    }

    public static ITapBehavior get(Block block) {
        return BEHAVIOR_MAP.get(block);
    }

    public static boolean contains(BlockState state) {
        if (BEHAVIOR_MAP.containsKey(state.getBlock())) {
            return true;
        }
        return PREDICATE_BEHAVIORS.stream().anyMatch(m -> m.matcher().test(state));
    }

    public static ITapBehavior get(BlockState state) {
        ITapBehavior behavior = BEHAVIOR_MAP.get(state.getBlock());
        if (behavior != null) {
            return behavior;
        }
        for (MatchedBehavior matched : PREDICATE_BEHAVIORS) {
            if (matched.matcher().test(state)) {
                return matched.behavior();
            }
        }
        return null;
    }

    private record MatchedBehavior(Predicate<BlockState> matcher, ITapBehavior behavior) {
    }
}
