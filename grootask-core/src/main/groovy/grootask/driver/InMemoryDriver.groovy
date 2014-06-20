package grootask.driver

import static grootask.json.JSON.toJson
import static grootask.json.JSON.fromJson

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
        queues[inboxQueueName].get(job.id, toJson(job))
        queues[statusQueueName].get(job.id, JobStatus.PENDING)

        return job.id
    }

    @ActiveMethod(blocking = true)
    JobStatus status(final String  jobID) {
        return fromJson(queues?.get(statusQueueName)?.get(jobID),Job).status ?: JobStatus.PENDING
    }

    @ActiveMethod(blocking = true)
    Object getFinished(final String jobID) {
        return fromJson(queues[doneQueueName][jobID],Job)
    }

    @ActiveMethod(blocking = true)
    Object getPending() {
        if (!queues[inboxQueueName]) {
           return
        }
        return fromJson(queues[inboxQueueName].values().first(), Job)
    }

    @ActiveMethod
    void finish(final Job job) {
        job.status = JobStatus.DONE

        queues[inboxQueueName].remove(job.id)
        queues[doneQueueName].get(job.id, toJson(job))
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
