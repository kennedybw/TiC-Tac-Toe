import javax.swing.*;
import java.awt.*;

public class PlayerChoice extends JFrame {
    
    public PlayerChoice(){
        setTitle("Choose Your Side");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridLayout(2,1));
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Instruction label
        JLabel instructionLabel = new JLabel("Choose whether you want to be X or O", JLabel.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(instructionLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0,50)));


        // Buttons for X and O
        
        JButton xButton = new JButton("Play as X");
        JButton oButton = new JButton("Play as O");

        xButton.setFont(new Font("Arial", Font.PLAIN,20));
        oButton.setFont(new Font("Arial", Font.PLAIN, 20));

        xButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        oButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // handle the choice
        xButton.addActionListener(e -> {
                dispose();
                new TicTacToeAI('X');
        });

        // handle the choice
        oButton.addActionListener(e -> {
                dispose();
                new TicTacToeAI('O');
        });

        mainPanel.add(xButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
        mainPanel.add(oButton);


        JPanel outPanel = new JPanel(new GridBagLayout());
        outPanel.add(mainPanel);
        add(outPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args){
        new PlayerChoice();
    }
}
