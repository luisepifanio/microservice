package ar.com.phosgenos.entities

import ar.com.phosgenos.dao.IdentifiableEntity
import groovy.transform.builder.Builder

@Builder
class Beer implements IdentifiableEntity<Long> {
    Long id
    Brewery brewery
    String name
    Category category
    Style style
    BigDecimal abv
    BigDecimal ibu
    BigDecimal srm
    BigDecimal upc
    String description
    Date modified
}
