package grootask

import grootask.driver.Driver
import groovyx.gpars.actor.DefaultActor

// TODO Server should have an executor actor reference
// TODO Server should react to server events (stop, pause...)
class Server extends DefaultActor {

    final Driver driver
    final PlanExecutor planExecutor

    Server(final Driver driver) {
        this.driver = driver
        this.planExecutor = new PlanExecutor(driver)
    }

    void afterStart() {
        planExecutor.start()
    }

    void act() {
        loop {
            Job nextJob = driver.getPending()
            if (nextJob) {
                planExecutor << nextJob
            }
        }
    }

}
