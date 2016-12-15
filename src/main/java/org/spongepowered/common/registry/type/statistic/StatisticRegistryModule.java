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
package org.spongepowered.common.registry.type.statistic;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import org.spongepowered.api.registry.AlternateCatalogRegistryModule;
import org.spongepowered.api.registry.util.RegisterCatalog;
import org.spongepowered.api.statistic.Statistic;
import org.spongepowered.api.statistic.Statistics;
import org.spongepowered.common.registry.SpongeAdditionalCatalogRegistryModule;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public final class StatisticRegistryModule implements SpongeAdditionalCatalogRegistryModule<Statistic>, AlternateCatalogRegistryModule<Statistic> {

    @RegisterCatalog(Statistics.class)
    private final Map<String, Statistic> statisticMappings = Maps.newHashMap();

    public static StatisticRegistryModule getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public Optional<Statistic> getById(String id) {
        return Optional.ofNullable(this.statisticMappings.get(id.toLowerCase()));
    }

    @Override
    public Collection<Statistic> getAll() {
        return ImmutableList.copyOf(this.statisticMappings.values());
    }

    @Override
    public void registerAdditionalCatalog(Statistic stat) {
        this.statisticMappings.put(stat.getId(), stat);
    }

    @Override
    public boolean allowsApiRegistration() {
        return false;
    }

    @Override
    public Map<String, Statistic> provideCatalogMap() {
        return this.statisticMappings;
    }

    private static final class Holder {

        static final StatisticRegistryModule INSTANCE = new StatisticRegistryModule();

    }

}
