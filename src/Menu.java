import javax.swing.*;
import java.awt.*;

/**
 * We are creating a frame Menu with one button, which refers to class Maps.
 */
public class Menu extends JFrame{
    private JPanel panel = new JPanel();
    private JFrame frame = new JFrame();
    private JButton newGame = new JButton("New Game");

    public Menu(){
        display();
        newGame.addActionListener(e -> {
            new Maps(Menu.this);
            frame.dispose();
        });

    }
    public void display(){
        frame.pack();

        panel.add(newGame);
        Font font = new Font("Algerian", Font.PLAIN, 30);
        newGame.setFont(font);
        newGame.setBounds(215, 470, 200, 100);
        newGame.setBackground(new Color(85, 101, 9));
        newGame.setForeground(Color.BLACK);

        ImageIcon image = new ImageIcon("menu.png");
        JLabel label = new JLabel(image);
        panel.add(label).setBounds(0,0, 625,625);



        frame.add(panel);
        panel.setLayout(null);
        frame.setSize(625, 650);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }


    public static void main(String[] args) {
        new Menu();
    }
}
