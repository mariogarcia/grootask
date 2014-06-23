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
        Job job = new Job(data: input, plan: PlanSpecSample)
        String jobId = client.enqueue(job)

        JobResult blockingJobResult =
            client.
                enqueue(plan).
                withData(input).
                get()

        then: 'The solution should have been processed successfully'
        blockingJobResult.data.name == 'modified John'

        and: 'The job should have been marked as DONE by the client local memory'
        blockingJobResult.status == DONE
    }

    void 'Launching a non blocking job with a single task'() {
        given: 'Some input for passing to the job'
        Input input = new Input(name:'John')

        and: 'Configuration'
        URL configFile = new File('src/test/resources/grootask/Config.groovy').toURL()
        Configuration config = new ConfigurationBuilder(configFile).build()
        Driver driver = new DriverBuilder(config).build()

        and: 'A executor to launch the process'
        Client client = new Client(driver) // SIMPLE OBJECT
        Server server = new Server(driver).start() // ACTOR

        when: 'Preparing values we will be checking later'
        CountdownLatch countDown = new CountDownLatch(1)
        String name = EMPTY
        JobStatus status = JobStatus.PENDING
        Closure<Void> assignVariables = { JobResult result ->
            name = result.name
            status = result.status
            // Altough this is an asynchronous call we want to
            // test it, that's why I'm using CountdownLatch
            countDown.latch()
        }

        and: 'Sending the job to the broker'
        JobSubscription subscription =
            client.
                enqueue(plan).
                withData(input).
                then(assignVariables)
        then: 'We should have get an instance of JobSubscription'
        subscription instanceof JobSubscription

        and: 'The solution should have been processed successfully'
        name == 'modified John'

        and: 'The job should have been marked as DONE by the client local memory'
        status == DONE
    }
}
