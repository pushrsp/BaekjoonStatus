package project.BaekjoonStatus.api.concurrent;

public class Job implements Comparable<Job> {
    private final int offset;
    private final JobCallback callback;

    private int maxTry;
    private long delay;
    private JobStatus status = JobStatus.READY;
    private long currMs = System.currentTimeMillis();

    public Job(long delay, int offset, JobCallback callback) {
        this.delay = delay;
        this.offset = Math.max(1, offset);
        this.callback = callback;
    }

    private void setStatus(JobStatus status) {
        this.status = status;
    }

    private void after() {
        this.delay *= Math.max(1, this.offset);
        this.currMs += this.delay;
        this.maxTry--;

        if(maxTry == 0) {
            System.out.println("RETRY");
        }
    }

    public void setMaxTry(int maxTry) {
        this.maxTry = Math.max(0, maxTry);
    }

    public long getCurrMs() {
        return this.currMs;
    }

    public boolean isContinue() {
        return isError() && this.maxTry > 0;
    }

    public boolean isError() {
        return status == JobStatus.ERROR;
    }

    public boolean canStart(long currMs) {
        return this.currMs <= currMs;
    }

    public void execute() {
        try {
            callback.execute();
            setStatus(JobStatus.DONE);
        } catch (Throwable e) {
            setStatus(JobStatus.ERROR);
        } finally {
            after();
        }
    }

    @Override
    public int compareTo(Job o) {
        return Long.compare(this.currMs, o.getCurrMs());
    }
}
