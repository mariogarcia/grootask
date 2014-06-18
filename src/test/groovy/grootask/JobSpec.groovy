package grootask

import static grootask.JobStatus.DONE
import static grootask.JobStatus.PENDING
import static grootask.JobStatus.WORKING

import grootask.aux.Input
import grootask.aux.Output
import grootask.driver.InMemoryDriver

import spock.lang.Specification

class JobSpec extends Specification {

    void 'Launching a job with a single task'() {
        given: 'An instance of a job'
        Job job = new Job()

        and: 'Some input'
        Input input = new Input(name:'John')

        and: 'Configuration'
        Configuration config =
            new Configuration(driverInstance: new InMemoryDriver().start())

        and: 'A executor to launch the process'
        Client client = new ClientBuilder(config).build() // SIMPLE OBJECT
        Server server = new ServerBuilder(config).build().start() // ACTOR

        when: 'Sending the job to the broker'
        String jobId =
            client.queue(
                'priority-queue',
                job.data(input).
                    output(Output).
                    task(TaskSpecSample)
            )
        assert jobId
        while(client.status('priority-queue', jobId) in [PENDING, WORKING]) { Thread.sleep(2000) }

        then: 'The solution should have been processed successfully'
        client.get('priority-queue', jobId).result.name == 'modified John'

        and: 'The job should have been marked as DONE by the client local memory'
        client.status('priority-queue', jobId) == DONE
    }

}
