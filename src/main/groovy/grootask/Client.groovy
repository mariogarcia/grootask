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

    Driver driver

    /**
     * This method sends a Job to any of the available queues in the system and
     * immediately returns giving the ID of job in the entire system in order
     * to make possible to track the job afterwards.
     *
     * @param Job
     * @param routingKey
     * @return a String with the hash identifying the job in the entire system
     */
    String queue(final String routingKey, final Job job) {
        return driver.queue(routingKey, job)
    }

    /**
     * This method returns the current status of the job represented by the ID
     * passed as parameter
     *
     * @param jobID
     * @param routingKey
     * @return an instance of a JobStatus enumeration
     */
    JobStatus status(final String routingKey, final String  jobID) {
        return driver.status(routingKey, jobID)
    }

    /**
     * This method blocks until the result of the job identified by the id
     * passed as parameter
     *
     * @param jobID
     * @param routingKey
     * @return the result of the execution of the job
     */
    Object get(final String routingKey, final String jobID) {
        return driver.get(routingKey, jobID)
    }

}
