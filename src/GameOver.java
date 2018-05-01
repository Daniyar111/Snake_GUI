import javax.swing.*;
import java.awt.*;

/**
 * This class is called if snake is dead.
 * This frame have 2 buttons and one label. Label contains user's score.
 * The first button refers to class Menu and user can start game again.
 * The second button leaves the game.
 */
public class GameOver{
    private JPanel panel = new JPanel();
    private JFrame frame = new JFrame();
    private JButton goTo = new JButton("Go to Menu");
    private JButton exit = new JButton("Exit");
    private JLabel text2 = new JLabel();
    private GameField display;

    public GameOver(final GameField main){
        this.display = main;
        display();
        text2.setText("Your score: " + display.score);
        goTo.addActionListener(e -> {
            new Menu();
            frame.dispose();
        });
        exit.addActionListener(e -> System.exit(0));
    }
    public void display(){
        frame.pack();
        Font font = new Font("Algerian", Font.PLAIN, 28);
        Font font2 = new Font("Algerian", Font.PLAIN, 18);

        panel.add(text2);
        text2.setFont(font);
        text2.setForeground(Color.WHITE);
        text2.setBounds(200, 400, 250, 50);

        panel.add(goTo);
        goTo.setBounds(88, 470, 200, 50);
        goTo.setFont(font2);
        goTo.setBackground(Color.WHITE);
        goTo.setForeground(Color.BLACK);

        panel.add(exit);
            exit.setBounds(338, 470, 200, 50);
        exit.setFont(font2);
        exit.setBackground(Color.WHITE);
        exit.setForeground(Color.BLACK);

        ImageIcon image = new ImageIcon("gameOver.png");
        JLabel label = new JLabel(image);
        panel.add(label).setBounds(0, 0, 625, 625);

        frame.add(panel);
        panel.setLayout(null);
        frame.setSize(625, 650);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
}
