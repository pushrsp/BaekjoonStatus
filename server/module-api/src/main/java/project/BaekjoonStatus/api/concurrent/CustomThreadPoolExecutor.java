package project.BaekjoonStatus.api.concurrent;

import java.util.concurrent.PriorityBlockingQueue;

public class CustomThreadPoolExecutor {
    private final PriorityBlockingQueue<Job> jobQueue;
    private final Worker[] workers;

    public CustomThreadPoolExecutor(int numOfWorkers) {
        this.jobQueue = new PriorityBlockingQueue<>();
        this.workers = new Worker[numOfWorkers];

        for (int i = 0; i < numOfWorkers; i++) {
            this.workers[i] = new Worker("CustomThreadPoolExecutor-" + i);
            this.workers[i].start();
        }
    }

    public void addJob(Job job) {
        this.jobQueue.add(job);
    }

    public boolean isEmpty() {
        return this.jobQueue.isEmpty();
    }

    public Job poll() {
        return this.jobQueue.poll();
    }

    public Job peek() {
        return this.jobQueue.peek();
    }

    class Worker extends Thread {
        public Worker(String threadName) {
            super(threadName);
        }

        public void run() {
            while (true) {
                while (!isEmpty() && peek().canStart(System.currentTimeMillis())) {
                    Job job = poll();
                    job.execute();

                    if(job.isContinue()) {
                        addJob(job);
                    }
                }
            }
        }
    }
}
