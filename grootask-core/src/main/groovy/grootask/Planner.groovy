package grootask

import grootask.driver.Driver
import groovyx.gpars.actor.DefaultActor

class Planner extends DefaultActor {

    final Driver driver
    final PlanExecutor planExecutor

    Planner(final Driver driver) {
        this.driver = driver
        this.planExecutor = new PlanExecutor(driver)
    }

    void afterStart() {
        this.planExecutor.start()
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
