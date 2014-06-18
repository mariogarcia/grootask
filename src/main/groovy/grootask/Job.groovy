package grootask

class Job {

    String id
    Object result
    JobStatus status = JobStatus.PENDING
    Class output
    Object sharedData

    List<Class<AbstractTask>> taskList = []

    Job data(Object data) {
        this.sharedData = data
        this
    }

    Job task(Class<AbstractTask> taskClass) {
        this.taskList << taskClass
        this
    }

    Job output(Class output) {
        this.output = output
        this
    }

}
