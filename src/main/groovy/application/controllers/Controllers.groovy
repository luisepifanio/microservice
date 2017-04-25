package application.controllers

import application.controllers.impl.CategoryController
import application.controllers.impl.CategorySearchController
import application.controllers.impl.HelloController
import ar.com.phosgenos.rest.Controller

class Controllers {
    static Set<Class<Controller>> get() {
        Set<Class<Controller>> set = new HashSet<>(
                Arrays.asList(
                        HelloController,
                        CategoryController,
                        CategorySearchController
                )
        )
        set
    }
}
