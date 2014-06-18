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
            if (nextJob) {
                Thread.sleep(2000)
                nextJob.status = JobStatus.DONE
                nextJob.result = nextJob.taskList.collect {
                    def a = it.execute()
                    println "result:$a"
                    a
                }.first()
                driver.queue('outbox', nextJob)
            }
        }
    }

}
