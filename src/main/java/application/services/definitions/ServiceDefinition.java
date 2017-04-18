package application.services.definitions;

import ar.com.phosgenos.dao.IdentifiableEntity;
import ar.com.phosgenos.functional.Either;

import java.util.Objects;

public interface ServiceDefinition<PK, E extends IdentifiableEntity<PK>, T extends Throwable> {

    Either<E, T> create(E entity);

    Either<E, T> findById(PK identifier);

    Either<E, T> update(E entity);

    Either<Boolean, T> remove(PK identifier);

    default Either<Boolean, T> remove(E entity) {
        Objects.requireNonNull(entity,"Cannot delete a null entity");
        return remove(entity.getId());
    }
}
