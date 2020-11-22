package producer_consumer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class App extends JDialog {

    private final JobQueue jobs = new JobQueue();
    private final List<JobConsumer> consumers = new ArrayList<>();
    private final List<JobProducer> producers = new ArrayList<>();

    public App() {
        super();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.add(createPanel());
    }

    public static void main(String[] args) {

        App app = new App();
        app.setSize(400, 250);
        app.setVisible(true);
    }

    private JPanel createPanel() {

        final JPanel panel = mainLayout();
        final JPanel firstRowPanel = producersLayout();
        final JPanel secondRowPanel = consumersLayout();
        final JPanel thirdRowPanel = createLayout(Color.blue, "Consumed job count:    ");

        final JTextField jobsCount = new JTextField(40);
        jobsCount.setEnabled(false);
        jobsCount.setMaximumSize(jobsCount.getPreferredSize());

        thirdRowPanel.add(jobsCount);
        thirdRowPanel.add(Box.createHorizontalGlue());

        this.jobs.addJobQueueListener(jobCount -> jobsCount.setText(String.valueOf(jobCount)));

        panel.add(firstRowPanel);
        panel.add(secondRowPanel);
        panel.add(thirdRowPanel);

        return panel;
    }

    private JPanel mainLayout() {

        final JPanel panel = new JPanel();
        panel.setBackground(Color.gray);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createRaisedBevelBorder());
        return panel;
    }

    private void startProducers(JButton addButton, JTextField fieldCount) {

        addButton.addActionListener(e -> {
            JobProducer newProducer = new JobProducer(jobs);
            producers.add(newProducer);
            fieldCount.setText(String.valueOf(producers.size()));
            newProducer.start();
        });
    }

    private JPanel producersLayout() {

        final JTextField fieldCount = new JTextField(40);
        final JButton addButton = new JButton(" + ");

        this.startProducers(addButton, fieldCount);
        addButton.setMaximumSize(addButton.getPreferredSize());

        fieldCount.setEnabled(false);
        fieldCount.setMaximumSize(fieldCount.getPreferredSize());

        final JPanel producersPanel = createLayout(Color.CYAN, "Producers:   ");
        producersPanel.add(fieldCount);
        producersPanel.add(addButton);
        producersPanel.add(Box.createHorizontalGlue());

        return producersPanel;
    }

    private void startConsumers(JButton addButton, JTextField fieldCount) {

        addButton.addActionListener(e -> {
            JobConsumer newConsumer = new JobConsumer(jobs);
            consumers.add(newConsumer);
            fieldCount.setText(String.valueOf(consumers.size()));
            newConsumer.start();
        });
    }

    private JPanel consumersLayout() {

        final JTextField fieldCount = new JTextField(40);
        fieldCount.setFont(new Font("Courier", Font.BOLD, 12));

        final JButton addButton = new JButton(" + ");

        this.startConsumers(addButton, fieldCount);
        addButton.setMaximumSize(addButton.getPreferredSize());

        fieldCount.setEnabled(false);
        fieldCount.setMaximumSize(fieldCount.getPreferredSize());

        final JPanel consumersPanel = createLayout(Color.YELLOW, "Consumers: ");
        consumersPanel.add(fieldCount);
        consumersPanel.add(addButton);
        consumersPanel.add(Box.createHorizontalGlue());

        return consumersPanel;
    }

    private JPanel createLayout(Color color, String s) {

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(color);
        panel.add(new JLabel(s));

        return panel;
    }
}