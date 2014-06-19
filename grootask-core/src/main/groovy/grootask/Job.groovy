package grootask

class Job {

    String id
    Class<AbstractPlan> plan
    JobStatus status = JobStatus.PENDING

    Object result
    Object data

}
