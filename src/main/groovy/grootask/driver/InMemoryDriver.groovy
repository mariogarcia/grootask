package grootask.driver

import grootask.Job
import grootask.JobStatus

import groovyx.gpars.actor.DefaultActor

class InMemoryDriver implements Driver {

    Driver driverActor

    static class SharedDriver extends DefaultActor implements Driver {

        Map queues = [:].withDefault { [:] }

        String queue(final String routingKey, final Job job) {
            job.id = "${new Date().time}"
            queues[routingKey].get(job.id, job)
            println queues

            return job.id
        }

        JobStatus status(final String routingKey, final String  jobID) {
            return queues?.get(routingKey)?.get(jobID).status ?: JobStatus.PENDING
        }

        Object get(final String routingKey, final String jobID) {
            println "searching for ${jobID} with key ${routingKey} in ${queues}"
            return queues[routingKey][jobID]
        }

        Object get(final String routingKey) {
            if (!queues[routingKey]) {
               return
            }
            return queues[routingKey].values().first()
        }

        void act() {
            loop {
                react { Map map ->
                    reply this.invokeMethod(map.action, map.args)
                }
            }
        }
    }

    Driver start() {
        driverActor = new SharedDriver().start()
        this
    }

    String queue(final String routingKey, final Job job) {
        driverActor.sendAndWait([action: 'queue', args: [routingKey, job]])
    }

    JobStatus status(final String routingKey, final String  jobID) {
        driverActor.sendAndWait([action: 'status', args:[routingKey, jobID]])
    }

    Object get(final String routingKey, final String jobID) {
        return driverActor.sendAndWait([action: 'get', args:[routingKey, jobID]])
    }

    Object get(final String routingKey) {
        driverActor.sendAndWait([action: 'get', args:[routingKey]])
    }
}
