package grootask

class Job {

    Class output
    Object singleData

    List<Task> taskList = []

    Job data(Object data) {
        this.singleData = data
        this
    }

    Job task(Task task) {
        this.taskList << task
        this
    }

    Job output(Class output) {
        this.output = output
        this
    }

}
