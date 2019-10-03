package de.hsfulda.et.wbs.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ImmutableMap<K, V> implements Map<K, V> {

    private final Map<K, V> immutableMap;

    private ImmutableMap() {
        immutableMap = new HashMap<>();
    }

    private ImmutableMap(Map<K, V> immutableMap) {
        this.immutableMap = immutableMap;
    }

    public static class Builder<K, V> {
        private final Map<K, V> template;

        private Builder() {
            template = new HashMap<>();
        }

        public Builder<K, V> put(K key, V value) {
            template.put(key, value);
            return this;
        }

        public Map<K, V> build() {
            return new ImmutableMap<>(template);
        }

    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    public static <K, V> Map<K, V> of() {
        return new ImmutableMap<>();
    }

    public static <K, V> Map<K, V> of(K key, V value) {
        Builder<K, V> builder = builder();
        return builder.put(key, value)
                .build();
    }

    // Delegates mit UOE für Änderungen

    @Override
    public int size() {
        return immutableMap.size();
    }

    @Override
    public boolean isEmpty() {
        return immutableMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return immutableMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return immutableMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return immutableMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(Object key) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        return immutableMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return immutableMap.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return immutableMap.entrySet();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return immutableMap.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        immutableMap.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V replace(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return immutableMap.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return immutableMap.computeIfPresent(key, remappingFunction);
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return immutableMap.compute(key, remappingFunction);
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException();
    }
}
