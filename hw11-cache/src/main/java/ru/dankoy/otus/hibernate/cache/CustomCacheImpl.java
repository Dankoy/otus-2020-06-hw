package ru.dankoy.otus.hibernate.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class CustomCacheImpl<K, V> implements CustomCache<K, V> {

    WeakHashMap<K, V> weakHashMap = new WeakHashMap<>();
    List<CustomCacheListener<K, V>> listeners = new ArrayList<>();

    @Override
    public String toString() {
        return "CustomCacheImpl{" +
                "weakHashMap=" + weakHashMap +
                '}';
    }

    @Override
    public void put(K key, V value) {
        weakHashMap.put(key, value);
        notify(key, value, "put");
    }

    @Override
    public void remove(K key) {
        V value = weakHashMap.get(key);
        weakHashMap.remove(key);
        notify(key, value, "remove");
    }

    @Override
    public V get(K key) {
        V value = weakHashMap.get(key);
        notify(key, value, "get");
        return weakHashMap.get(key);
    }

    @Override
    public void addListener(CustomCacheListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(CustomCacheListener<K, V> listener) {
        listeners.remove(listener);
    }

    public void notify(K key, V value, String action) {
        listeners.forEach(l -> l.notify(key, value, action));
    }
}