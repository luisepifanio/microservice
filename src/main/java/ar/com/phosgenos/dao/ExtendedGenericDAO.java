package ar.com.phosgenos.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExtendedGenericDAO<T extends IdentifiableEntity<PK>, PK extends Serializable> implements IGenericDao<T,PK> {

    private final IGenericDao<T, PK> delegate;

    public ExtendedGenericDAO(final IGenericDao<T, PK> delegate) {
        this.delegate = Objects.requireNonNull(delegate, "delegated DAO cannot be null");
    }

    @Override
    public Class<T> getPersistentClass() {
        return delegate.getPersistentClass();
    }

    @Override
    public T create(T t) {
        return delegate.create(t);
    }

    @Override
    public T read(PK id) {
        return delegate.read(id);
    }

    @Override
    public T update(T t) {
        return delegate.update(t);
    }

    @Override
    public boolean delete(T t) {
        return delegate.delete(t);
    }

    @Override
    public boolean delete(PK id) {
        return delegate.delete(id);
    }

    //Extended CRUD operations
    public Collection<T> createAll(final Collection<T> instances){
        final Collection<T> _instances = Objects.requireNonNull(instances, "instances to create cannot be null");
        return new HashSet<T>(_instances)
                .parallelStream()
                .filter(t -> null != t)
                .map(t -> this.create(t) )
                .collect(Collectors.toSet());
    }

    public Collection<T> readAll(final Collection<PK> ids) {
        final Collection<PK> _ids = Objects.requireNonNull(ids, "instances ids to read cannot be null");
        return new HashSet<>(_ids)
                .parallelStream()
                .filter(t -> null != t)
                .map(t -> this.read(t) )
                .collect(Collectors.toSet());
    }

    public Collection<T> updateAll(final Collection<T> instances) {
        final Collection<T> _instances = Objects.requireNonNull(instances, "instances to update cannot be null");
        return new HashSet<>(_instances)
                .parallelStream()
                .filter(t -> null != t)
                .map(t -> this.update(t) )
                .collect(Collectors.toSet());
    }

    public int deleteAll(final Collection<T> instances) {
        final Collection<T> _instances = Objects.requireNonNull(instances, "instances to delete cannot be null");
        return new HashSet<>(_instances)
                .parallelStream()
                .filter(t -> null != t)
                .map(t -> this.delete(t))
                .filter(t -> t)
                .collect(Collectors.toSet())
                .size();
    }

    public boolean exists(T t) {
        final T _instance = Objects.requireNonNull(t, "instance to check existence cannot be null");
        return  exists(_instance.getId());
    }

    public boolean exists(PK id) {
        final PK _id = Objects.requireNonNull(id, "identifier to check existence cannot be null");
        return null != read(_id);
    }

    public Collection<T> saveOrUpdate(final Collection<T> instances) {
        final Collection<T> _instances = Objects.requireNonNull(instances, "instances to save/update cannot be null");
        return new HashSet<>(_instances)
                .parallelStream()
                .filter(t -> null != t)
                .map(t -> this.exists(t) ? this.update(t) : this.create(t))
                .collect(Collectors.toSet());
    }

    public static <C extends IdentifiableEntity<P>,P extends Serializable> ExtendedGenericDAO<C ,P> fromInstance(final IGenericDao<C,P> delegate){
        return new ExtendedGenericDAO<>(delegate);
    }
}
