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
     * @return a String with the hash identifying the job in the entire system
     */
    String enqueue(final Job job)

    /**
     * This method returns the current status of the job represented by the ID
     * passed as parameter
     *
     * @param jobID
     * @return an instance of a JobStatus enumeration
     */
    JobStatus status(final String  jobID)

    /**
     * This method blocks until the result of the job identified by the id
     * passed as parameter
     *
     * @param jobID
     * @return the result of the execution of the job
     */
    Object getFinished(final String jobID)

    Object getPending()
    void finish(final Job job)

    void setQueue(final DriverQueue type, final String name)

}
