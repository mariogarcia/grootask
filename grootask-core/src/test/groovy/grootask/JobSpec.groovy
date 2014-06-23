package grootask

import static grootask.JobStatus.DONE
import static grootask.JobStatus.PENDING
import static grootask.JobStatus.WORKING

import grootask.aux.Input
import grootask.aux.Output

import grootask.driver.Driver
import grootask.driver.DriverBuilder
import grootask.driver.InMemoryDriver

import spock.lang.Ignore
import spock.lang.Specification

class JobSpec extends Specification {

    void 'Launching a blocking job with a single task'() {
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
        JobResult blockingResult =
            client.enqueue(plan).withInput(input).get()

        then: 'The solution should have been processed successfully'
        blockingResult.data.name == 'modified John'

        and: 'The job should have been marked as DONE by the client local memory'
        blockingResult.status == DONE
    }

}
