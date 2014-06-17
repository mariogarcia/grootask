package grootask.driver

import grootask.Job
import grootask.JobStatus

class InMemoryDriver implements Driver {

    String queue(final String routingKey, final Job job) {
        println "queueing job"
        return "1"
    }

    JobStatus status(final String routingKey, final String  jobID) {
        println "status:$jobID"
        return JobStatus.PENDING
    }

    Object get(final String routingKey, final String jobID) {
        println "get:$jobID"
        return [name: 'modified John']
    }

}
