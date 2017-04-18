package ar.com.phosgenos.context;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@EqualsAndHashCode(of = {"id"})
@ToString
public class ContextItem<T> implements Serializable {
    @Getter
    final Serializable id;
    @Getter
    final T data;

    public ContextItem(Serializable id, T data) {
        this.id = id;
        this.data = data;
    }

    public static <C> ContextItemBuilder<C> builder() {
        return new ContextItemBuilder<>();
    }

    @Data
    public static class ContextItemBuilder<R> {
        Serializable id;
        R data;

        ContextItemBuilder<R> id(Serializable _id) {
            this.id = _id;
            return this;
        }

        ContextItemBuilder<R> data(R _data) {
            this.data = _data;
            return this;
        }

        ContextItem<R> build() {
            return new ContextItem<>(id, data);
        }
    }
}
