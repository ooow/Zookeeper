import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by goga on 14.12.15.
 */
public class GUI extends JFrame {
    private JButton b;
    private JTextArea l;

    public GUI() {
        super("Zookeeper client");
        setLayout(new GridLayout(2, 1, 10, 10));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        Font BigFont = new Font("Georgia", Font.BOLD, 25);
        b = new JButton();
        l = new JTextArea();
        add(b);
        add(l);
        b.setFont(BigFont);
        b.setText("Connect");
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zkLockTest zz = new zkLockTest(l);
                zz.run();
            }
        });
    }

    public static void main(String[] args) {
        new GUI();
    }
}