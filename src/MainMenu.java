import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame{
    
    public MainMenu(){
        setTitle("Tic-Tac-Toe");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // panel to the hold the title and the button
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel titleLabel = new JLabel("Tic-Tac-Toe", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        // vertical space between title and button
        mainPanel.add(Box.createRigidArea(new Dimension(0,50)));


        // Start Button
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN,20));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> {
            // hide main menu and open player choice screen
            dispose();
            new PlayerChoice();
        });

        mainPanel.add(startButton);

        add(mainPanel, BorderLayout.CENTER);

        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.add(mainPanel);
        add(outerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args){
        new MainMenu();
    }
}
