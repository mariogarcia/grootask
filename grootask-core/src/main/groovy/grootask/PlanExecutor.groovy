package grootask

import grootask.driver.Driver
import groovyx.gpars.actor.DefaultActor

class PlanExecutor extends DefaultActor {

    final Driver driver

    PlanExecutor(final Driver driver) {
        this.driver = driver
    }

    // Maybe this actor can react to new jobs and finished jobs
    // former can be process latter can be marked asynchronously
    void act() {
        loop {
            react { Job job ->
                job.plan.newInstance(job.data).execute() >> { result ->
                   job.result = result
                   driver.finish(job)
                }
            }
        }
    }

}

