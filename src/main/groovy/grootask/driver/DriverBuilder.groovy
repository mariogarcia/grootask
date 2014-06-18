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
        def driverInstance = configuration.driverInstance ?: driverClass.newInstance()

        driverInstance.setQueue(DriverQueue.INBOX   , configuration.queues.inbox)
        driverInstance.setQueue(DriverQueue.STATUS  , configuration.queues.status)
        driverInstance.setQueue(DriverQueue.DONE    , configuration.queues.done)
        driverInstance.setQueue(DriverQueue.FAILING , configuration.queues.failing)

        return driverInstance
    }

}
