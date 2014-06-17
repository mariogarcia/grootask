package grootask

import groovyx.gpars.dataflow.DataflowVariable

class Client<O> {

    Configuration configuration

    Client() { }
    Client(final Configuration cfg) {
       this.configuration = cfg
    }

    DataflowVariable<O> execute(final Job job) {

    }

}
