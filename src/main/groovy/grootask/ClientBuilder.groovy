package grootask

class ClientBuilder {

    final Configuration configuration

    ClientBuilder(final Configuration configuration) {
        this.configuration = configuration
    }

    Client build() {
        def driverClass = configuration.driverClass
        def specificDriver = driverClass.newInstance()

        return new Client(driver: specificDriver)
    }

}
