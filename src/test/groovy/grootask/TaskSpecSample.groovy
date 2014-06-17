package grootask

import grootask.aux.Input
import grootask.aux.Output

import groovy.transform.InheritConstructors
import groovyx.gpars.dataflow.DataflowVariable
import groovyx.gpars.activeobject.ActiveMethod
import groovyx.gpars.activeobject.ActiveObject

@ActiveObject
@InheritConstructors
class TaskSpecSample extends Task<Input,Output> {

    @ActiveMethod DataflowVariable<Output> execute() {
        return new Output(name: "modified ${input.name}")
    }

}