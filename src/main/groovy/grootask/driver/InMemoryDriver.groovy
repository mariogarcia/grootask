package grootask.driver

import grootask.Job
import grootask.JobStatus
import grootask.Configuration

import groovyx.gpars.activeobject.ActiveObject
import groovyx.gpars.activeobject.ActiveMethod

@ActiveObject
class InMemoryDriver implements Driver {

    final Configuration configuration
    Map queues = [:].withDefault { [:] }

    InMemoryDriver(final Configuration configuration) {
        this.configuration = configuration
    }

    @ActiveMethod(blocking = true)
    String enqueue(final Job job) {
        job.id = job.id ?: "${new Date().time}"
        queues[inboxQueueName].get(job.id, job)
        queues[statusQueueName].get(job.id, JobStatus.PENDING)

        return job.id
    }

    @ActiveMethod(blocking = true)
    JobStatus status(final String  jobID) {
        return queues?.get(statusQueueName)?.get(jobID).status ?: JobStatus.PENDING
    }

    @ActiveMethod(blocking = true)
    Object getFinished(final String jobID) {
        return queues[doneQueueName][jobID]
    }

    @ActiveMethod(blocking = true)
    Object getPending() {
        if (!queues[inboxQueueName]) {
           return
        }
        return queues[inboxQueueName].values().first()
    }

    @ActiveMethod
    void finish(final Job job) {
        job.status = JobStatus.DONE
        queues[doneQueueName].get(job.id, job)
        queues[statusQueueName].get(job.id, JobStatus.DONE)
    }

    private String getInboxQueueName() {
        return this.configuration.queues[DriverQueue.INBOX]
    }

    private String getStatusQueueName() {
        return this.configuration.queues[DriverQueue.STATUS]
    }

    private String getFailingQueueName() {
        return this.configuration.queues[DriverQueue.FAILING]
    }

    private String getDoneQueueName() {
        return this.configuration.queues[DriverQueue.DONE]
    }

}
