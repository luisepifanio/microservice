package ar.com.phosgenos.context;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@EqualsAndHashCode(of = {"id"})
@ToString
public class ContextItem<T> implements Serializable {

    private static final long serialVersionUID = -7375267433724473083L;

    @Getter
    final Serializable id;
    @Getter
    final T data;

    public ContextItem(Serializable id, T data) {
        this.id = id;
        this.data = data;
    }

    public static <C extends Serializable> ContextItemBuilder<C> builder() {
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
            return new ContextItem<>(this.id, this.data);
        }
    }
}
