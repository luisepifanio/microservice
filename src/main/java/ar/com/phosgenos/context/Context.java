package ar.com.phosgenos.context;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.*;

@Slf4j
public class Context {

    final Map<Serializable, ContextItem> repository;

    public Context() {
        this(new LinkedHashMap<>());
    }

    public Context(final Map<Serializable, ContextItem> _repository) {
        this.repository = new LinkedHashMap<>(
                Objects.requireNonNull(_repository, "Context should be initialized with non null Map")
        );
    }

    public Context(final Context toCopy) {
        this();
        extend(toCopy);
    }

    public void clear() {
        repository.clear();
    }

    @SuppressWarnings("unchecked")
    public <V> ContextItem<V> get(final Serializable key) {
        return (ContextItem<V>) repository.get(key);
    }

    @SuppressWarnings("unchecked")
    public <V> ContextItem<V> put(final ContextItem<V> contextItem) {
        Objects.requireNonNull(contextItem);
        Objects.requireNonNull(contextItem.getData());
        Preconditions.checkState(contextItem.getId() != null, "Context item should have an id");
        repository.put(contextItem.getId(), contextItem);

        return contextItem;

    }

    public <V> V putContextValue(final Serializable key, final V value) {
        ContextItem<V> _item = asContextItem(key, value);
        ContextItem<V> item = put(_item);
        return item.getData();
    }

    public <V> V getContextValue(final Serializable key) {
        ContextItem<V> value = get(key);
        return (value == null) ? null : value.getData();
    }

    public <V> V removeContextValue(final Serializable key) {
        if (repository.containsKey(key)) {
            ContextItem value = repository.remove(key);
            try {
                return (V) value.getData();
            } catch (ClassCastException e) {
                log.warn("Could not cast your Object as desired but key was removed");
            }
        }
        log.warn("Key has not been found");
        return null;

    }

    public <V> V getContextValue(final Serializable key, final V defaultValue) {
        Optional<V> val = Optional.ofNullable(getContextValue(key));

        return val.orElseGet(() -> put(asContextItem(key, defaultValue)).getData());
    }

    public Context extend(Context toCopy) {
        this.repository.putAll(toCopy.repository);
        return this;
    }

    public Context extendACopy(Context toCopy) {
        Context copy = new Context(toCopy != null ? toCopy.repository : new HashMap<>());
        return copy.extend(toCopy);
    }

    private <V> ContextItem<V> asContextItem(Serializable key, V defaultValue) {
        return new ContextItem.ContextItemBuilder<V>()
                .id(key)
                .data(defaultValue)
                .build();
    }
}
