package grootask

import grootask.driver.Driver
import groovyx.gpars.actor.DefaultActor

// TODO Server should have an executor actor reference
// TODO Server should react to server events (stop, pause...)
class Server extends DefaultActor {

    final Driver driver

    Server(final Driver driver) {
        this.driver = driver
    }

    void act() {
        loop {
            Job nextJob = driver.getPending()
            if (nextJob) {
                nextJob.result =
                    nextJob.plan.
                        newInstance(nextJob.data).
                        execute().
                        get()
                driver.finish(nextJob)
            }
        }
    }

}
