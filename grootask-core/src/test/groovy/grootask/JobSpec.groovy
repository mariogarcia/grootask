package grootask

import static grootask.JobStatus.DONE
import static grootask.JobStatus.PENDING
import static grootask.JobStatus.WORKING

import grootask.aux.Input
import grootask.aux.Output

import grootask.driver.Driver
import grootask.driver.DriverBuilder
import grootask.driver.InMemoryDriver

import spock.lang.Specification

class JobSpec extends Specification {

    void 'Launching a job with a single task'() {
        given: 'Some input for passing to the job'
        Input input = new Input(name:'John')

        and: 'Configuration'
        URL configFile = new File('src/test/resources/grootask/Config.groovy').toURL()
        Configuration config = new ConfigurationBuilder(configFile).build()
        Driver driver = new DriverBuilder(config).build()

        and: 'A executor to launch the process'
        Client client = new Client(driver) // SIMPLE OBJECT
        Server server = new Server(driver).start() // ACTOR

        when: 'Sending the job to the broker'
        Job job = new Job(data: input, plan: PlanSpecSample)
        String jobId = client.enqueue(job)

        assert jobId
        while(client.status(jobId) in [PENDING, WORKING]) {
            Thread.sleep(2000)
        }

        then: 'The solution should have been processed successfully'
        client.getFinished(jobId)
        client.getFinished(jobId).result.name == 'modified John'

        and: 'The job should have been marked as DONE by the client local memory'
        client.status(jobId) == DONE
    }

}
