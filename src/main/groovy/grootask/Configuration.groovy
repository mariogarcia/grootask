package grootask

import grootask.driver.Driver
import grootask.driver.InMemoryDriver

class Configuration {

    Class<Driver> driverClass
    Driver driverInstance = new InMemoryDriver()

    Map<String,String> queues = [:]

}
