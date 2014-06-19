package grootask

import grootask.aux.Input
import grootask.aux.Output

import groovy.transform.InheritConstructors

import groovyx.gpars.dataflow.DataflowVariable
import groovyx.gpars.activeobject.ActiveObject
import groovyx.gpars.activeobject.ActiveMethod


@ActiveObject
@InheritConstructors
class PlanSpecSample extends AbstractPlan<Input,Output> {
    @ActiveMethod
    DataflowVariable<Output> execute() {
        return new Output(name: "modified ${input.name}")
    }
}

