package ar.com.phosgenos.context;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Context {

    final Map<Serializable, ContextItem> repository;

    public Context() {
        this(new LinkedHashMap<>());
    }

    public Context(final Map<Serializable, ContextItem> repository) {
        this.repository = repository;
    }

    public Context(final Context toCopy) {
        this();
        this.repository.putAll(toCopy.repository);
    }

    public <V> ContextItem<V> get(final Serializable key) {
        return (ContextItem<V>) repository.get(key);
    }

    public <V> ContextItem<V> put(final ContextItem<V> contextItem) {
        Objects.requireNonNull(contextItem);
        Objects.requireNonNull(contextItem.getData());
        return repository.put(contextItem.getId(),contextItem);
    }

    public <V> V putContextValue(final Serializable key, final V value) {
        return put(asContextItem(key,value)).getData();
    }

    public <V> V getContextValue(final Serializable key) {
        ContextItem<V> value = repository.get(key);
        return (value == null) ? null : value.getData();
    }

    public <V> V removeContextValue(final Serializable key) {
        return (V) repository.remove(key).getData();
    }

    public <V> V getContextValue(final Serializable key, final V defaultValue) {
        Optional<V> val = Optional.ofNullable(getContextValue(key));

        return val.orElseGet( () -> put(asContextItem(key, defaultValue)).getData() );
    }

    public Context extend(Context toCopy) {
        this.repository.putAll(toCopy.repository);
        return this;
    }

    private <V> ContextItem<V> asContextItem(Serializable key, V defaultValue) {
        return new ContextItem.ContextItemBuilder<V>()
                    .id(key)
                    .data(defaultValue)
                    .build();
    }
}
