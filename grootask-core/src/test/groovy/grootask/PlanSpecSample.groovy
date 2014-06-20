package grootask

import static groovyx.gpars.dataflow.Dataflow.task

import grootask.aux.Input
import grootask.aux.Output

import groovy.transform.InheritConstructors

import groovyx.gpars.dataflow.Promise
import groovyx.gpars.dataflow.Dataflows
import groovyx.gpars.dataflow.DataflowVariable

@InheritConstructors
class PlanSpecSample extends AbstractPlan<Input,Output> {

    Promise<Output> execute() {

        Dataflows flow = new Dataflows()

        task {
            flow.changedString =  "modified ${input.name}"
        }

        task {
            flow.output =  new Output(name: flow.changedString)
        }

    }
}

