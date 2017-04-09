package ar.com.phosgenos.entities

import ar.com.phosgenos.dao.IdentifiableEntity
import groovy.transform.builder.Builder

@Builder
class Category implements IdentifiableEntity<Long> {
    Long id
    String name
    Date modified
}
