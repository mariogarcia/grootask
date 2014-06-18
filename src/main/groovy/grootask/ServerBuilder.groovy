package grootask

import grootask.driver.Driver
import grootask.driver.DriverQueue

class ServerBuilder {

    final Configuration configuration

    ServerBuilder(final Configuration configuration) {
        this.configuration = configuration
    }

    Server build() {
        Driver driverInstance =
            this.configuration.driverInstance ?:
            this.configuration.driverClass.newInstance()

        driverInstance.setQueue(DriverQueue.INBOX   , configuration.queues.inbox)
        driverInstance.setQueue(DriverQueue.STATUS  , configuration.queues.status)
        driverInstance.setQueue(DriverQueue.DONE    , configuration.queues.done)
        driverInstance.setQueue(DriverQueue.FAILING , configuration.queues.failing)

        return new ServerImpl(driverInstance)
    }

}
