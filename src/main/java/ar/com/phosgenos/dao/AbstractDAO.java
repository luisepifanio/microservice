package ar.com.phosgenos.dao;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Slf4j
public abstract class AbstractDAO<T extends IdentifiableEntity<PK>, PK extends Serializable>
        implements IGenericDao<T, PK> {

    protected final Class<T> persistentClass;

    /**
     * All subclases must call super in his constructor to allow de hack about
     * dynamic typing
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        Type type = genericSuperclass.getActualTypeArguments()[0];
        if (type instanceof Class) {
            this.persistentClass = (Class<T>) type;
        }else{
            log.warn("Could not figure out about persistent class");
            this.persistentClass = null;
        }
        log.info("Class '{}' instatiated with persistent class '{}'",this.getClass(),persistentClass);
    }

    @Override
    public Class<T> getPersistentClass() {
        return this.persistentClass;
    }
}
