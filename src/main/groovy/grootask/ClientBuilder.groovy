package grootask

import grootask.driver.DriverQueue

class ClientBuilder {

    final Configuration configuration

    ClientBuilder(final Configuration configuration) {
        this.configuration = configuration
    }

    Client build() {
        def driverClass = configuration.driverClass
        def driverInstance = configuration.driverInstance ?: driverClass.newInstance()

        driverInstance.setQueue(DriverQueue.INBOX   , configuration.queues.inbox)
        driverInstance.setQueue(DriverQueue.STATUS  , configuration.queues.status)
        driverInstance.setQueue(DriverQueue.DONE    , configuration.queues.done)
        driverInstance.setQueue(DriverQueue.FAILING , configuration.queues.failing)

        return new Client(driver: driverInstance)
    }

}
