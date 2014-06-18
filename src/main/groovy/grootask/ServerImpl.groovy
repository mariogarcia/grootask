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
                nextJob.result =
                    nextJob.taskList.collect { taskClass ->
                        taskClass.
                            newInstance(nextJob.sharedData).
                            execute().
                            get()
                    }.first()
                nextJob.status = JobStatus.DONE
                driver.queue('priority-queue', nextJob)
            }
        }
    }

}
