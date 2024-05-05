package com.chromanyan.chromagadgets.triggers;

import com.chromanyan.chromagadgets.ChromaGadgets;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class DegravelerVeinTrigger extends SimpleCriterionTrigger<DegravelerVeinTrigger.TriggerInstance> {
    public static final ResourceLocation ID = new ResourceLocation(ChromaGadgets.MODID, "degraveler_vein");
    public static final DegravelerVeinTrigger INSTANCE = new DegravelerVeinTrigger();

    @Override
    protected @NotNull TriggerInstance createInstance(@Nonnull JsonObject json, @Nonnull ContextAwarePredicate playerPred, @NotNull DeserializationContext conditions) {
        return new TriggerInstance(playerPred, GsonHelper.getAsInt(json, "required_amount"));
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player, int amount) {
        this.trigger(player, triggerInstance -> triggerInstance.test(amount));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final int requiredAmount;

        public TriggerInstance(ContextAwarePredicate playerPred, int requiredAmount) {
            super(ID, playerPred);

            this.requiredAmount = requiredAmount;
        }

        @NotNull
        @Override
        public ResourceLocation getCriterion() {
            return ID;
        }

        boolean test(int amount) {
            return amount >= this.requiredAmount;
        }

        @Override
        public @NotNull JsonObject serializeToJson(@NotNull SerializationContext pConditions) {
            JsonObject jsonobject = super.serializeToJson(pConditions);
            jsonobject.addProperty("required_amount", this.requiredAmount);
            return jsonobject;
        }
    }
}
