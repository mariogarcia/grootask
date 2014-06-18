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
            println "Lala"
            if (nextJob) {
                Thread.sleep(2000)
                nextJob.status = JobStatus.DONE
                println nextJob.taskList.size()
                nextJob.result = nextJob.taskList.collect {
                    println "a1"
                    def a = it.newInstance(new Input(name:'lala')).execute().get()
                    println "a2"
                    println "result:$a"
                    a
                }.find{it}
                println "Sending job to outbox"
                driver.queue('priority-queue', nextJob)
            }
        }
    }

}
