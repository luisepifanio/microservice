package application.services

import application.services.definitions.CategoryService
import application.services.impl.dao.CategoryDaoImpl
import application.services.impl.CategoryServiceImpl
import ar.com.phosgenos.dao.IGenericDao
import ar.com.phosgenos.entities.Category
import com.google.inject.Binder
import com.google.inject.Module
import com.google.inject.Scopes
import com.google.inject.TypeLiteral
import groovy.util.logging.Slf4j

@Slf4j
class ServicesModule implements Module {
    @Override
    void configure(Binder binder) {
        log.info("Binding ServicesModule")
        //Persistence Services
        binder.bind(new TypeLiteral<IGenericDao<Category,Long>>(){})
                //.annotatedWith(Names.named('categoryDao'))
                .to(CategoryDaoImpl)
                .in(Scopes.SINGLETON)
        //Bussiness Logic Services
        binder.bind(CategoryService)
                .to(CategoryServiceImpl)
                .in(Scopes.SINGLETON)
    }
}
