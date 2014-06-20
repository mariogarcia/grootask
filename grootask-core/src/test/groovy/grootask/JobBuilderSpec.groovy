package grootask

import static grootask.json.JSON.toJson
import static grootask.json.JSON.fromJson

import spock.lang.Specification
import groovyx.gpars.dataflow.Promise
import groovy.json.JsonSlurper

class JobBuilderSpec extends Specification {

    static class MyPlan extends AbstractPlan<Map,Integer> {
        Promise<Integer> execute() {}
    }

    void 'Building a Job instance' () {
        when: 'Building a job with'
        Job job =
            new JobBuilder().
                input([name:'name', age:22]).
                plan(MyPlan).build()
        and: 'Marshalling and Unmarshalling the object'
        String json = toJson(job)
        assert json instanceof String
        Job recoveredJob  = fromJson(json, Job)
        then: 'Data should have been serialized to JSON'
        with(recoveredJob.result) {
            name == 'name'
            age == 22
        }
        and: 'Plan class should be present too'
        job.plan == MyPlan
    }

}
