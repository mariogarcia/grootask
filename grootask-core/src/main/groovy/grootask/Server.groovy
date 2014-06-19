package grootask

import grootask.driver.Driver
import groovyx.gpars.actor.DefaultActor

class Server extends DefaultActor {

    final Driver driver
    final Planner planner

    Server(final Driver driver) {
        this.driver = driver
        this.planner = new Planner(driver)
    }

    void afterStart() {
        planner.start()
    }

    void act() {
        loop {
            react { // ServerEvent serverEvent ->

            }
        }
    }

}
