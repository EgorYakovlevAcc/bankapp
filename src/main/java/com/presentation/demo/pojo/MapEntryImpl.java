package com.presentation.demo.pojo;

import java.util.Map;

public class MapEntryImpl<K,V> implements Map.Entry<K,V> {

    private final K key;
    private V value;

    public MapEntryImpl(K key,V value){
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V v) {
        V old = this.value;
        this.value = v;
        return old;
    }
}
