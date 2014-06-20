package grootask

import grootask.driver.Driver
import groovyx.gpars.actor.DefaultActor

class Planner extends DefaultActor {

    final Driver driver
    final PlanDispatcher planDispatcher

    Planner(final Driver driver) {
        this.driver = driver
        this.planDispatcher = new PlanDispatcher(driver)
    }

    void afterStart() {
        this.planDispatcher.start()
    }

    void act() {
        loop {
            Job nextJob = driver.getPending()
            if (nextJob) {
                planDispatcher << nextJob
            }
        }
    }

}
