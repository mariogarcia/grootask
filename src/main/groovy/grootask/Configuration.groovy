package grootask

import grootask.driver.Driver

class Configuration {

    Class<Driver> driverClass
    Driver driverInstance

    Map<String,String> queues = [:]
    Map<String,Object> extras = [:]

}
