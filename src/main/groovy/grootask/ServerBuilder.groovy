package grootask

import grootask.driver.Driver

class ServerBuilder {

    final Configuration configuration

    ServerBuilder(final Configuration configuration) {
        this.configuration = configuration
    }

    Server build() {
        Driver driver =
            this.configuration.driverInstance ?:
            this.configuration.driverClass.newInsntance()

        return new ServerImpl(driver)
    }

}
