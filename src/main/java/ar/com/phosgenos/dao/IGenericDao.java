package ar.com.phosgenos.dao;

import java.io.Serializable;

public interface IGenericDao<T extends IdentifiableEntity<PK>, PK extends Serializable> {

    Class<T> getPersistentClass();
    // Basic CRUD Operations
    T create(final T t);
    T read(final PK id);
    T update(final T t);
    boolean delete(final T t);
    boolean delete(final PK id);

    /*
    Collection<T> readAll();
    //Utlity
    <P extends IAbstractPersistentDomainObject> List<P> findByFields( Class<?> clazz, List<String> fieldNames, List<? extends Object> fieldValues);
    */

}
