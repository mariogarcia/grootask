package grootask

import grootask.driver.Driver

import groovy.json.JsonOutput
import groovyx.gpars.dataflow.DataflowVariable

/**
 * The client should send the job's information to the broker. Any worker can then
 * take the job and start processing it.
 *
 * The client should be able to
 *
 * - Send the job asynchronously
 * - Send the job to a queue
 * - Schedule the job
 *
 */
class Client {

    final Driver driver

    Client(final Driver driver) {
        this.driver = driver
    }

    String enqueue(final Job job) {
        return driver.enqueue(job)
    }

    JobStatus status(final String  jobID) {
        return driver.status(jobID)
    }

    Object getFinished(final String jobID) {
        return driver.getFinished(jobID)
    }

}
