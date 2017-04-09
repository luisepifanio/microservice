package ar.com.phosgenos.entities

import ar.com.phosgenos.dao.IdentifiableEntity

abstract class EntityLocation<T extends IdentifiableEntity> implements IdentifiableEntity<Long>{
    Long id
    BigDecimal latitude
    BigDecimal longitude
    AcuracyType accuracy
    T identifiableEntity
}
