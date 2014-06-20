package grootask

final class JobBuilder {

    Object input
    Class<AbstractPlan> planClass

    JobBuilder input(final Object object) {
       this.input = object
       this
    }

    JobBuilder plan(final Class<AbstractPlan> planClass) {
        this.planClass = planClass
        this
    }

    Job build() {
        return new Job(result: input, plan: planClass)
    }

}
