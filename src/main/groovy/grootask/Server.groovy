package grootask

import grootask.driver.Driver
import groovyx.gpars.actor.DefaultActor

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
                    nextJob.taskList.collect { taskClass ->
                        taskClass.
                            newInstance(nextJob.sharedData).
                            execute().
                            get()
                    }.first()
                driver.finish(nextJob)
            }
        }
    }

}
