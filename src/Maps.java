import javax.swing.*;
import java.awt.*;

/**
 * Then we are creating a frame Maps, which is linked by class Menu.
 * Here we are creating 6 buttons, which have maps' names and their links.
 * Every button refers to class Levels.
 */
public class Maps{
    private JPanel panel = new JPanel();
    private JFrame frame = new JFrame();
    private JButton[] buttons = {new JButton("Classic"), new JButton("Rectangle"), new JButton("DoubleRec"), new JButton("BrickRoom"), new JButton("Plus++"), new JButton("C in C")};
    private Menu display;


    public Maps(final Menu main){
        this.display = main;
        display();
        buttons[0].addActionListener(e -> {
            new Levels(Maps.this,"classic");
            frame.dispose();
        });
        buttons[1].addActionListener(e -> {
            new Levels(Maps.this,"map1");
            frame.dispose();
        });
        buttons[2].addActionListener(e -> {
            new Levels(Maps.this,"map2");
            frame.dispose();
        });
        buttons[3].addActionListener(e -> {
            new Levels(Maps.this,"map3");
            frame.dispose();
        });
        buttons[4].addActionListener(e -> {
            new Levels(Maps.this,"map4");
            frame.dispose();
        });
        buttons[5].addActionListener(e -> {
            new Levels(Maps.this,"map5");
            frame.dispose();
        });

    }
    public void display(){
        frame.pack();
        frame.setTitle("Choose map");
        Font font = new Font("Algerian", Font.PLAIN, 13);

        panel.add(buttons[0]);
        buttons[0].setBounds(95, 85, 114,40);
        buttons[1].setBounds(415, 85, 114,40);
        buttons[2].setBounds(95, 295, 114,40);
        buttons[3].setBounds(415, 295, 114,40);
        buttons[4].setBounds(95, 505, 114,40);
        buttons[5].setBounds(415, 505, 114,40);
        for (JButton but: buttons){
            panel.add(but);
            but.setFont(font);
            but.setBackground(new Color(215,204,17));
            but.setForeground(Color.BLACK);
        }

        ImageIcon image = new ImageIcon("maps.png");
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
