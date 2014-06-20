package grootask

import groovyx.gpars.dataflow.Promise

abstract class AbstractPlan<I,O> {

    final I input

    AbstractPlan(final I input) {
        this.input = input
    }

    abstract Promise<O> execute()

}
