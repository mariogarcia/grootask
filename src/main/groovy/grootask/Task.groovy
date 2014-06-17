package grootask

import groovyx.gpars.activeobject.ActiveObject
import groovyx.gpars.activeobject.ActiveMethod
import groovyx.gpars.dataflow.DataflowVariable

abstract class Task<I,O> {

    final I input

    Task(final I input) {
        this.input = input
    }

    abstract DataflowVariable<O> execute()

}
