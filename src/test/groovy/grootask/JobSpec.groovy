package grootask

import static grootask.JobStatus.DONE
import static grootask.JobStatus.PENDING
import static grootask.JobStatus.WORKING

import grootask.aux.Input
import grootask.aux.Output

import spock.lang.Specification

class JobSpec extends Specification {

    void 'Launching a job with a single task'() {
        given: 'An instance of a job'
        Job job = new Job()

        and: 'Some input'
        Input input = new Input(name:'John')

        and: 'A executor to launch the process'
        // TODO driver params
        Configuration config = new Configuration(driverClass: InMemoryDriver)
        Client client = new ClientBuilder(configuration).build() // SIMPLE OBJECT
        Server server = new ServerBuilder(configuration).build() // ACTOR

        when: 'Sending the job to the broker'
        String jobId =
            client.queue(
                'inbox',
                job.data(immutableData).
                    output(Output).
                    task(A,B,C)
            )

        then: 'The job need some time to finish'
        client.status(jobId) in [PENDING, WORKING]

        and: 'The solution should have been processed successfully'
        with(client.get(jobId)) {
            name == 'modified John'
        }

        and: 'The job should have been marked as DONE by the client local memory'
        client.status(jobId) == DONE
    }

}
