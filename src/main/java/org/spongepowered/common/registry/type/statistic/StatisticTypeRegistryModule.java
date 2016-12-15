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
import org.spongepowered.api.registry.CatalogRegistryModule;
import org.spongepowered.api.registry.util.RegisterCatalog;
import org.spongepowered.api.statistic.StatisticType;
import org.spongepowered.api.statistic.StatisticTypes;
import org.spongepowered.common.statistic.SpongeStatisticType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public final class StatisticTypeRegistryModule implements CatalogRegistryModule<StatisticType> {

    @RegisterCatalog(StatisticTypes.class)
    private final Map<String, StatisticType> universalStatisticMappings = Maps.newHashMap();

    @Override
    public Optional<StatisticType> getById(String id) {
        return Optional.ofNullable(this.universalStatisticMappings.get(id.toLowerCase()));
    }

    @Override
    public Collection<StatisticType> getAll() {
        return ImmutableList.copyOf(this.universalStatisticMappings.values());
    }

    @Override
    public void registerDefaults() {
        this.universalStatisticMappings.put("mine_block", new SpongeStatisticType("mine_block"));
        this.universalStatisticMappings.put("craft_item", new SpongeStatisticType("craft_item"));
        this.universalStatisticMappings.put("use_item", new SpongeStatisticType("use_item"));
        this.universalStatisticMappings.put("break_item", new SpongeStatisticType("break_item"));
        this.universalStatisticMappings.put("pickup", new SpongeStatisticType("pickup"));
        this.universalStatisticMappings.put("drop", new SpongeStatisticType("drop"));
        this.universalStatisticMappings.put("basic", new SpongeStatisticType("basic"));
    }

}
