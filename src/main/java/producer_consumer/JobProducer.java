package producer_consumer;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobProducer extends Thread {

    private final Random random = new Random();
    private final Logger logger = LoggerFactory.getLogger(JobProducer.class);
    private final JobQueue jobs;

    public JobProducer(JobQueue jobs) {
        this.jobs = jobs;
    }

    @Override
    public void run() {

        while (true) {
            try {
                sleep(1000);

                int newJob = 60 * random.nextInt();

                logger.info("Creating a new job: size {}", newJob);

                this.jobs.queueJob(newJob);

                logger.info("Job created.");
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }
}