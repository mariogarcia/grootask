package grootask.driver

import grootask.Job
import grootask.JobStatus

interface Driver {

    /**
     * This method sends a Job to any of the available queues in the system and
     * immediately returns giving the ID of job in the entire system in order
     * to make possible to track the job afterwards.
     *
     * @param Job
     * @param routingKey
     * @return a String with the hash identifying the job in the entire system
     */
    String queue(final String routingKey, final Job job)

    /**
     * This method returns the current status of the job represented by the ID
     * passed as parameter
     *
     * @param jobID
     * @param routingKey
     * @return an instance of a JobStatus enumeration
     */
    JobStatus status(final String routingKey, final String  jobID)

    /**
     * This method blocks until the result of the job identified by the id
     * passed as parameter
     *
     * @param jobID
     * @param routingKey
     * @return the result of the execution of the job
     */
    Object get(final String routingKey, final String jobID)

    Object get(final String routingKey)

}
