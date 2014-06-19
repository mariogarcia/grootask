package grootask.driver

import grootask.Configuration
import grootask.driver.DriverQueue

class DriverBuilder {

    final Configuration configuration

    DriverBuilder(final Configuration configuration) {
        this.configuration = configuration
    }

    Driver build() {
        def driverClass = configuration.driverClass
        def driverInstance = driverClass.newInstance(configuration)

        return driverInstance
    }

}
