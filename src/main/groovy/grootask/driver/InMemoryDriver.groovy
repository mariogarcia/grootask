package grootask.driver

import grootask.Job
import grootask.JobStatus

class InMemoryDriver implements Driver {

    String queue(final Job job) {
        println "queueing job"
    }

    JobStatus status(final String  jobID) {
        println "status:$jobID"
    }

    Object get(final String jobID) {
        println "get:$jobID"
    }

}
