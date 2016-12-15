/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.common.mixin.core.stats;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.TupleIntJsonSerializable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.SpongeEventFactory;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.statistic.ChangeStatisticEvent;
import org.spongepowered.api.statistic.Statistic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.common.interfaces.statistic.IMixinStatisticsManager;

import java.util.Map;

@Mixin(StatisticsManager.class)
public abstract class MixinStatisticsManager implements IMixinStatisticsManager {

    @Shadow public abstract int readStat(StatBase stat);
    @Shadow public abstract void increaseStat(EntityPlayer player, StatBase stat, int amount);

    @Shadow @Final protected Map<StatBase, TupleIntJsonSerializable> statsData;

    private boolean statCaptured = false;

    @Inject(method = "increaseStat(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/stats/StatBase;I)V",
            at = @At("HEAD"), cancellable = true)
    public void onStatChange(EntityPlayer player, StatBase stat, int amount, CallbackInfo ci) {
        if (this.statCaptured) {
            return;
        }

        int prev = readStat(stat);
        // TODO: Better cause here?
        ChangeStatisticEvent.TargetPlayer event = SpongeEventFactory.createChangeStatisticEventTargetPlayer(
            Cause.source(player).build(), prev, prev + amount, (Statistic) stat, (Player) player);
        boolean cancelled = Sponge.getEventManager().post(event);
        this.statCaptured = true;
        ci.cancel();

        if (!cancelled) {
            increaseStat(player, stat, (int) (event.getValue() - prev));
            this.statCaptured = false;
        }
    }

    @Override
    public Map<StatBase, TupleIntJsonSerializable> getStatsData() {
        return ImmutableMap.copyOf(this.statsData);
    }

}
