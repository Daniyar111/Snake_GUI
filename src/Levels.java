import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * In this class we are starting to play our game. In this frame we have 3 buttons, which have their levels.
 * Depending on choosing map in the previous class, we refer to the map to which we want.
 * And depending on choosing level in this class, we get to chosen map with difficulty level.
 */
public class Levels extends JFrame{

    private JPanel panel = new JPanel();
    private JFrame frame = new JFrame();
    private JButton[] buttons = {new JButton("Easy"), new JButton("Medium"), new JButton("High")};
    private Maps display;
    private String mapType;
    public int waitToShow = 20, waitToHide;
    GameField game;
    public JLabel infoScore = new JLabel();

    private void goDisplay(String mapType, int level){
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(630, 653);
        setLocationRelativeTo(null);
        game = new GameField(Levels.this, mapType, level);
        add(game);
        setResizable(false);
        game.setLayout(null);
        setVisible(true);
        game.add(infoScore);
        drawScores();
        infoScore.setBounds(285, 1, 200, 20);
/**
 * Also our game has bonus food, which appears every 20 seconds.
 * When bonus food was showed, it will wait to be eaten by snake depending on chosen level (easy - longer, high - quicker)
 * If snake ate bonus food or , TimerTask will start counting again.
 */
        waitToHide = game.waitingDifference;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (waitToShow > 0){
                    waitToShow--;
                }
                else if(waitToShow <= 0 && !game.isBonusShowed) {
                    game.isBonusShowed = true;
                    game.showBonusFood();
                    waitToHide = game.waitingDifference;
                }
                else if (game.isBonusShowed && waitToHide > 0){
                    waitToHide--;
                }
                else if (waitToHide <= 0){
                    game.isBonusShowed = false;
                    game.hideBonusFood();
                    waitToShow = 20;
                }

            }
        }, 1000, 1000);
    }
    // Draw scores in every map.
    public void drawScores(){
        infoScore.setText("Score: " + game.score);
    }

    public Levels(final Maps main, String mapType){
        this.display = main;
        this.mapType = mapType;
        display();
        buttons[0].addActionListener(e -> {
            goDisplay(mapType, 1);
            frame.dispose();
        });

        buttons[1].addActionListener(e -> {
            goDisplay(mapType, 2);
            frame.dispose();
        });
        buttons[2].addActionListener(e -> {
            goDisplay(mapType, 3);
            frame.dispose();
        });
    }

    public void display(){
        frame.pack();
        Font font = new Font("Algerian", Font.PLAIN, 28);

        int difference = 40;
        for (JButton but: buttons) {
            panel.add(but);
            but.setBounds(215, 110 + difference, 200, 100);
            but.setFont(font);
            but.setBackground(new Color(223, 240, 110));
            but.setForeground(Color.BLACK);
            difference += 150;
        }

        ImageIcon image = new ImageIcon("levels.png");
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
