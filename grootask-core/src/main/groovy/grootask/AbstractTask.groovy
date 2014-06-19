package grootask

import groovyx.gpars.dataflow.DataflowVariable

abstract class AbstractTask<I,O> {

    final I input

    AbstractTask(final I input) {
        this.input = input
    }

    abstract DataflowVariable<O> execute()

}
