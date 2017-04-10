package application.services.definitions

import ar.com.phosgenos.entities.Category

interface CategoryServiceDefinition {

    Category create(Category category)

    Category findById(Long identifier)

    Category update(Category category)

    boolean remove(Long identifier)
}
