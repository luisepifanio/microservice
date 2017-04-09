package ar.com.phosgenos.entities

class BreweryLocation extends EntityLocation<Brewery>{
    void setBrewery(Brewery brewery){
        this.identifiableEntity = brewery
    }

    Brewery getBrewery(){
        this.identifiableEntity
    }
}

