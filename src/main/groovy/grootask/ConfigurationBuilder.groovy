package grootask

class ConfigurationBuilder {

    URL configurationLocator

    ConfigurationBuilder(URL locator) {
        this.configurationLocator = locator
    }

    Configuration build() {
        def values = new ConfigSlurper().parse(configurationLocator)
        def config = new Configuration(
            driverClass: values.driver.driverClass
        )

        return config
    }

}
