package grootask

import grootask.driver.Driver
import groovyx.gpars.actor.DynamicDispatchActor

class PlanDispatcher extends DynamicDispatchActor {

    final Driver driver
    final PlanExecutor planExecutor

    PlanDispatcher(final Driver driver) {
        this.driver = driver
        this.planExecutor = new PlanExecutor(driver)
    }

    void afterStart() {
       this.planExecutor.start()
    }

    void onMessage(Job job) {
        planExecutor << job
    }

    void onMessage(JobResponse response) {
        driver.finish(new Job(id: response.id, result: response.result))
    }

}

