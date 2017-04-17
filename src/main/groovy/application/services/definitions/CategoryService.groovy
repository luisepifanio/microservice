package application.services.definitions

import ar.com.phosgenos.entities.Category
import ar.com.phosgenos.exception.ApplicationException

interface CategoryService extends ServiceDefinition<Long,Category,ApplicationException> {

}