package producer_consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobConsumer extends Thread {

    private final Logger logger = LoggerFactory.getLogger(JobConsumer.class);
    private final JobQueue jobQueue;
    private Integer assignedJob = 0;

    public JobConsumer(JobQueue jobQueue) {
        this.jobQueue = jobQueue;
    }

    @Override
    public void run() {

        while (true) {
            if (assignedJob == 0) {
                try {
                    if (jobQueue.getNextJob() == 0) {
                        logger.info("Idle, Time: {} ", System.currentTimeMillis());
                        sleep(3000);
                    }
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            } else {
                for (int i = assignedJob; i >= 0; i--) {
                    logger.info("Working... Time: {}", System.currentTimeMillis());
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                    }
                }
                assignedJob = 0;
                logger.info("Finished processing. Time: {}", System.currentTimeMillis());
            }
        }
    }
}
