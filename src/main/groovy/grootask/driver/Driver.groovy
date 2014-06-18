package grootask.driver

import grootask.Job
import grootask.JobStatus

interface Driver {

    String enqueue(final Job job)

    JobStatus status(final String  jobID)
    Object getFinished(final String jobID)
    Object getPending()

    void finish(final Job job)
    void setQueue(final DriverQueue type, final String name)

}
