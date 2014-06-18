package grootask

import grootask.driver.Driver
import groovyx.gpars.actor.DefaultActor

class ServerImpl extends DefaultActor implements Server {

    final Driver driver

    ServerImpl(final Driver driver) {
        this.driver = driver
    }

    void act() {
        loop {
            Job nextJob = driver.get('priority-queue')
            println "------------->${nextJob.id}"
            Thread.sleep(2000)
            nextJob.status = JobStatus.DONE
            driver.queue('outbox', nextJob)
            react { ServerEvent event ->
                switch(event.signal) {
                    default:
                        println "SIGNAL: ${event.signal}"
                    break
                }
            }
        }
    }

}
