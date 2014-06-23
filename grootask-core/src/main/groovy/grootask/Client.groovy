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

    static ClientPlan {
        final Driver driver
        final Class<AbstractPlan> plan
        String jsonInput

        ClientPlan(Class<AbstractPlan> plan,Driver driver) {
            this.plan = plan
            this.driver = driver
        }

        ClientPlan withInput(Object input) {
            this.jsonInput = JSON.toJson(input)
        }

        JobResult get() {
            return driver.enqueue(job).get()
        }

    }

    final Driver driver

    Client(final Driver driver) {
        this.driver = driver
    }

    ClientPlan enqueue(final Job job) {
        new ClientPlan(job.plan)
    }

    JobStatus status(final String  jobID) {
        return driver.status(jobID)
    }

    Object getFinished(final String jobID) {
        return driver.getFinished(jobID)
    }

}
