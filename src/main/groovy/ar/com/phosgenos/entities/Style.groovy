package ar.com.phosgenos.entities

import ar.com.phosgenos.dao.IdentifiableEntity
import groovy.transform.builder.Builder

@Builder
class Style implements IdentifiableEntity<Long> {
    Long id
    Category category
    String name
    Date modified
}
