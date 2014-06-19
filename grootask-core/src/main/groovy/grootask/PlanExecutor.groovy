package grootask

import grootask.driver.Driver
import groovyx.gpars.actor.DefaultActor

class PlanExecutor extends DefaultActor {

    final Driver driver

    PlanExecutor(final Driver driver) {
        this.driver = driver
    }

    void act() {
        loop {
            react { Job job ->
                job.with {
                    result = plan.newInstance(data).execute().get()
                }
                driver.finish(job)
            }
        }
    }

}

