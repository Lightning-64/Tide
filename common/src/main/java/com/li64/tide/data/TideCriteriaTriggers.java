package com.li64.tide.data;

import com.li64.tide.Tide;
import com.li64.tide.data.triggers.SupernaturalCatchTrigger;
import com.li64.tide.data.triggers.TideSimpleTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;

import java.util.HashMap;

public class TideCriteriaTriggers {
    public static final HashMap<String, CriterionTrigger<?>> CRITERIA_TRIGGERS = new HashMap<>();

    public static SupernaturalCatchTrigger SUPERNATURAL_CATCH = (SupernaturalCatchTrigger) register("supernatural_catch", new SupernaturalCatchTrigger());
    public static TideSimpleTrigger FINISH_PAGE = (TideSimpleTrigger) register("finish_page", new TideSimpleTrigger("finish_page"));
    public static TideSimpleTrigger FINISH_JOURNAL = (TideSimpleTrigger) register("finish_journal", new TideSimpleTrigger("finish_journal"));
    public static TideSimpleTrigger FISHED_IN_LAVA = (TideSimpleTrigger) register("fish_in_lava", new TideSimpleTrigger("fish_in_lava"));
    public static TideSimpleTrigger FISHED_CRATE = (TideSimpleTrigger) register("fish_crate", new TideSimpleTrigger("fish_crate"));

    public static <T extends AbstractCriterionTriggerInstance> SimpleCriterionTrigger<T> register(String key, SimpleCriterionTrigger<T> trigger) {
        CRITERIA_TRIGGERS.put(key, trigger);
        return trigger;
    }

    public static void init() {
        CRITERIA_TRIGGERS.forEach(Tide.PLATFORM::registerCriteriaTrigger);
    }
}
