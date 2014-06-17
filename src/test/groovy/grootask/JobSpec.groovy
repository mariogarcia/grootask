package grootask

import grootask.aux.Input
import grootask.aux.Output

import spock.lang.Specification

class JobSpec extends Specification {

    void 'Launching a job with a single task'() {
        given: 'An instance of a job'
        Job job = new Job()

        and: 'A executor to launch the process'
        Client client = new Client()

        when:
        def result =
            client.plan(
                job.data(new Input(name: 'John')).
                    task(TaskSpecSample).
                    output(Output)
            )

        then: 'The solution should have been processed successfully'
        with(result.get()) {
            name == 'modified John'
        }
    }

}
