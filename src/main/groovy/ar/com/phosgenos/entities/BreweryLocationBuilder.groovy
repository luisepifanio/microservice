package ar.com.phosgenos.entities

class BreweryLocationBuilder {
    Long id
    BigDecimal latitude
    BigDecimal longitude
    AcuracyType accuracy
    BreweryLocation identifiableEntity

    BreweryLocationBuilder setId(Long id) {
        this.id = id
        this
    }

    BreweryLocationBuilder setLatitude(BigDecimal latitude) {
        this.latitude = latitude
        this
    }

    BreweryLocationBuilder setLongitude(BigDecimal longitude) {
        this.longitude = longitude
        this
    }

    BreweryLocationBuilder setAccuracy(AcuracyType accuracy) {
        this.accuracy = accuracy
        this
    }

    BreweryLocationBuilder setIdentifiableEntity(BreweryLocation identifiableEntity) {
        this.identifiableEntity = identifiableEntity
        this
    }

    BreweryLocationBuilder setBrewery(Brewery brewery){
        this.identifiableEntity = brewery
        this
    }

    BreweryLocation build(){
        new BreweryLocation(
                id: id,
                latitude: latitude,
                longitude: longitude,
                accuracy: accuracy,
                identifiableEntity: identifiableEntity
        )
    }
}
