package ar.com.phosgenos.entities

import ar.com.phosgenos.dao.IdentifiableEntity

class Brewery implements IdentifiableEntity<Long> {
    Long id
    String name
    String address1
    String address2
    String city
    String state
    String code
    String country
    String phone
    String website
    String description
    Date modified

    static BreweryLocationBuilder builder(){
        new BreweryLocationBuilder()
    }
}
