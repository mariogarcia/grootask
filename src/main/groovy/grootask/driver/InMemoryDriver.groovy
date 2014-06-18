package grootask.driver

import grootask.Job
import grootask.JobStatus

import groovyx.gpars.activeobject.ActiveObject
import groovyx.gpars.activeobject.ActiveMethod

@ActiveObject
class InMemoryDriver implements Driver {

    Driver driverActor
    Map<DriverQueue,String> queueReference = [:]
    Map queues = [:].withDefault { [:] }

    @ActiveMethod(blocking = true)
    String enqueue(final Job job) {
        job.id = job.id ?: "${new Date().time}"
        queues[queueReference[DriverQueue.INBOX]].get(job.id, job)
        queues[queueReference[DriverQueue.STATUS]].get(job.id, JobStatus.PENDING)

        return job.id
    }

    @ActiveMethod(blocking = true)
    JobStatus status(final String  jobID) {
        return queues?.get(queueReference[DriverQueue.STATUS])?.get(jobID).status ?: JobStatus.PENDING
    }

    @ActiveMethod(blocking = true)
    Object getFinished(final String jobID) {
        return queues[queueReference[DriverQueue.DONE]][jobID]
    }

    @ActiveMethod(blocking = true)
    Object getPending() {
        if (!queues[queueReference[DriverQueue.INBOX]]) {
           return
        }
        return queues[queueReference[DriverQueue.INBOX]].values().first()
    }

    @ActiveMethod
    void finish(final Job job) {
        job.status = JobStatus.DONE
        queues[queueReference[DriverQueue.DONE]].get(job.id, job)
        queues[queueReference[DriverQueue.STATUS]].get(job.id, JobStatus.DONE)
    }

    @ActiveMethod
    void setQueue(DriverQueue type, String name) {
        this.queueReference[type, name]
    }

}
