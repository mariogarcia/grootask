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
                // DEPENDING ON IF PLAN THROWS AN EXCEPTION OR NOT
                // IT SHOULD RETURN WHETHER A JOBRESPOjNSE OR A JOBFAILED
                // IN ORDER TO LET THE DISPATCHER TO ACT IN CONSEQUENCE
                // ACTORS SHUTDOWN SHOULD BE ORGANISED
                if (job.plan) { // WEIRD BEHAVIOR BECAUSE OF ACTOR'S DEATH
                    println "PROCESSING JOB ${job.id}"
                    reply new JobResponse(
                        id: job.id,
                        result:job.plan.newInstance(job.data).execute().get()
                    )
                }

            }
        }
    }

}
