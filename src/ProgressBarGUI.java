import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProgressBarGUI extends JFrame {

    private JProgressBar progressBar;
    private JButton startButton;
    private boolean started = false;
    private boolean isRunning;

    public ProgressBarGUI() {
        super("Progress Bar Example");

        Color backgroundColor = new Color(1, 175, 209);
        getContentPane().setBackground(backgroundColor);
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);

        startButton = new JButton("Start");
        startButton.setFont(new Font("Calibri", Font.PLAIN, 14));
        startButton.setBackground(new Color(235, 64, 52));
        startButton.setForeground(Color.white);
        startButton.setUI(new StyledButtonUI());
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!started) {
                    startProgressBar();
                }
                if (progressBar.getValue() > 70 && progressBar.getValue() < 80) {
                    System.out.println("Work");
                    isRunning = false;
                    startButton.setBackground(new Color(52, 235, 88));
                    JOptionPane.showMessageDialog(null, "Access granted!");
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(progressBar);
        panel.add(startButton);
        panel.setBackground(backgroundColor);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 100);
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void startProgressBar() {
        // startButton.setEnabled(false); // Disable the start button
        started = true;
        isRunning = true;
        progressBar.setValue(0); // Reset the progress bar

        // Simulate some time-consuming task
        Thread thread = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    if (!isRunning) break;
                    final int progress = i;
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            progressBar.setValue(progress);
                        }
                    });
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startButton.setEnabled(true); // Enable the start button when the task is completed
                isRunning = false;
            }
        });

        thread.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProgressBarGUI().setVisible(true);
            }
        });
    }
}
