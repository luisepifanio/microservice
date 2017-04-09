package ar.com.phosgenos.dao.memory

import ar.com.phosgenos.entities.AcuracyType
import ar.com.phosgenos.entities.Beer
import ar.com.phosgenos.entities.Brewery
import ar.com.phosgenos.entities.BreweryLocation
import ar.com.phosgenos.entities.Category
import ar.com.phosgenos.entities.Style
import groovy.json.JsonSlurper

class InMemoryRepository {

    private static final InMemoryRepository instance = new InMemoryRepository()

    final List<BreweryLocation> breweryLocations
    final List<Style> styles
    final List<Category> categories
    final List<Brewery> breweries
    final List<Beer> beers

    private InMemoryRepository() {
        categories = loadCategories()
        styles = loadStyles()
        breweries = loadBreweries()
        breweryLocations = loadLocations()
        beers = loadBeers()
    }

    private List<Category> loadCategories() {
        load('source/regular/categories.json').collect {
            Date modified = Date.parse('yyyy-MM-dd HH:mm:ss',it.last_mod as String)
            Category.builder()
                .id( it.id as Long )
                .name(  it.name as String )
                .modified(modified​)
            .build()
        }
    }

    private List<Style> loadStyles() {
        load('source/regular/styles.json').collect {
            Long styleId = it.cat_id
            Date modified = Date.parse('yyyy-MM-dd HH:mm:ss',it.last_mod as String)
            new Style(
                id:  it.id,
                category: categories.find { it.id == styleId },
                name: it.style_name as String ,
                modified: modified
            )
        }
    }

    private List<Brewery> loadBreweries() {
        load('source/regular/breweries.json').collect {
            Date modified = Date.parse('yyyy-MM-dd HH:mm:ss',it.last_mod as String)
            new Brewery(
                    id: it.id,
                    name: it.name,
                    address1: it.address1,
                    address2: it.address2,
                    city: it.city,
                    state: it.state,
                    code: it.code,
                    country: it.country,
                    phone: it.phone,
                    website: it.website,
                    description: it.descript,
                    modified:  modified
            )
        }
    }

    private List<BreweryLocation> loadLocations() {
        load('repository/geocodes.json').collect {
            Long breweryId = it.brewery_id
            new BreweryLocation(
                    id: it.id,
                    latitude: it.latitude,
                    longitude: it.longitude,
                    accuracy: it.accuracy as AcuracyType,
                    identifiableEntity: breweries.find { breweryId == it.id }
            )
        }
    }

    private List<Beer> loadBeers() {
        load('source/regular/beers.json').collect {
            Long breweryId = it.brewery_id
            Long categoryId = it.cat_id
            Long styleId = it.style_id

            new Beer(
                    id: it.id,
                    brewery: breweries.find { it.id = breweryId },
                    name: it.name,
                    category: categories.find { it.id = categoryId } ,
                    style: styles.find { it.id == styleId },
                    abv: it.abv,
                    ibu: it.ibu,
                    srm:it.srm,
                    upc: it.upc,
                    description: it.descript,
                    modified: Date.parse('yyyy-MM-dd HH:mm:ss', it.last_mod as String)
            )
        }
    }

    static InMemoryRepository getInstance() { instance }

    private static <T> T load(final String source){
        File inputFile = new File(source)
        T result = (T) new JsonSlurper().parseText(inputFile.text)
        result
    }
}
