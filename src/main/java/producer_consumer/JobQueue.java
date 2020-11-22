package producer_consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class JobQueue {

    private final Logger logger = LoggerFactory.getLogger(JobQueue.class);
    private final LinkedList<Integer> jobs = new LinkedList<>();
    private JobQueueListener listener;

    public JobQueue() {
        super();
    }

    public void addJobQueueListener(JobQueueListener listener) {

        this.listener = listener;
    }

    public interface JobQueueListener {

        void jobQueueChanged(int newSize) throws InterruptedException;
    }

    public synchronized void queueJob(int job) throws InterruptedException {

        synchronized (this) {
            this.jobs.add(job);
            if (this.listener != null) {
                this.listener.jobQueueChanged(this.jobs.size());
            }
            logger.info("Queued job {}. Time: {}", job, System.currentTimeMillis());
        }
    }

    public synchronized Integer getNextJob() throws InterruptedException {

        synchronized (this) {
            logger.info("Getting next job. Time: {}", System.currentTimeMillis());

            if (this.jobs.isEmpty()) {
                return 0;
            }

            Integer job = this.jobs.removeFirst();

            if (this.listener != null) {
                this.listener.jobQueueChanged(this.jobs.size());
            }

            logger.info("Next job retrivied, {}", job);
            return job;
        }
    }
}
