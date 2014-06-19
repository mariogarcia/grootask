package grootask

import groovyx.gpars.dataflow.DataflowVariable

abstract class AbstractPlan<I,O> {

    final I input

    AbstractPlan(final I input) {
        this.input = input
    }

    abstract DataflowVariable<O> execute()

}
