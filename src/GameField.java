import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * This class is our main game class. It response for game. Main algorithm of game occurs in here.
 */
public class GameField extends JPanel implements ActionListener{

    private final int SIZE = 600; // Size of field
    private final int DOT_SIZE = 25; // Pixels of one dot
    private final int ALL_DOTS = 625; // Count of dots in the field

    private Image headImg, bodyImg;
    private Image food, foodBonus;
    private Image grass;
    private Image mapIm;
    private Timer timer;

    public int foodX, foodBonusX = -100;
    public int foodY, foodBonusY = -100;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int[][][] mapps;
    private int dots; // Initial size of snake

    private boolean right = true;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private boolean right1 = true;
    private boolean left1 = false;
    private boolean up1 = false;
    private boolean down1 = false;

    private String mapType;
    public int level;
    private Levels display;
    private int speed;
    private String mapDirectory;
    public int scoreDifference, waitingDifference;
    public boolean isBonusShowed;
    public int score = 0;

    /**
     * It depend on what did the user choose: map and level (I wrote in the previous class).
     * @param main - it calls only from the class Levels.
     * @param mapType - it choose map depending on user's choice.
     * @param level - it choose score in eating food and waiting bonus food for snake depending on user's choice.
     */
    public GameField(final Levels main, String mapType, int level){
        this.display = main;
        this.mapType = mapType;
        this.level = level;
        switch (level){
            case 1:
                scoreDifference = 5;
                waitingDifference = 20;
                speed = 4;
                break;
            case 2:
                scoreDifference = 15;
                waitingDifference = 15;
                speed = 6;
                break;
            default:
                scoreDifference = 30;

                waitingDifference = 10;
                speed = 8;
        }
        display.infoScore.setForeground(Color.white);
        switch (mapType){
            case "map1":
                mapDirectory = "1";
                display.infoScore.setForeground(Color.black);
                break;
            case "map2":
                mapDirectory = "2";
                break;
            case "map3":
                mapDirectory = "3";
                break;
            case "map4":
                mapDirectory = "4";
                break;
            case "map5":
                mapDirectory = "5";
                break;
            default:
                mapDirectory = "Classic";
        }
        this.mapps = map.getMap(mapDirectory);
        setBackground(Color.black);
        getImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
        setVisible(true);
    }


    //Todo: Snake!

    /**
     * These methods response for all of snake.
     */

    /**
     * This method is respawn of snake in field;
     */
    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++){
            x[i] = 125 - i * DOT_SIZE;
            y[i] = 100;

        }
        /**
         * This is snake's frequency of motion, depending on level(speed).
         */
        timer = new Timer(1000 / speed, this);
        timer.start();
        createFood();
    }

    /**
     * @return snake's array of one dot. (more precisely it's a snake's head)
     */
    private int[][] getRealPositionOfSnake(){
        int[][] posit = new int[2][2];
        posit[0][0] = x[0];
        posit[0][1] = x[0] + 25;
        posit[1][0] = y[0];
        posit[1][1] = y[0] + 25;
        return posit;
    }

    /**
     * This method see if snake's head is in collision(in map.java)(depend on maps), snake is dead.
     */
    public void moveSnake() {
        for (int[][] limit : mapps) {
            int[][] realPosition = getRealPositionOfSnake();
            if (left) {
                if ((realPosition[0][0] >= limit[0][0] && realPosition[0][0] <= limit[0][1]) && (realPosition[1][0] >= limit[1][0] && realPosition[1][1] <= limit[1][1])) {
                    inGame = false;
                }
            }
            if (right) {
                if ((realPosition[0][1] >= limit[0][0] && realPosition[0][1] <= limit[0][1]) && (realPosition[1][0] >= limit[1][0] && realPosition[1][1] <= limit[1][1])) {
                    inGame = false;
                }
            }
            if (up) {
                if ((realPosition[1][0] >= limit[1][0] && realPosition[1][0] <= limit[1][1]) && (realPosition[0][0] >= limit[0][0] && realPosition[0][1] <= limit[0][1])) {
                    inGame = false;
                }
            }
            if (down) {
                if ((realPosition[1][1] >= limit[1][0] && realPosition[1][1] <= limit[1][1]) && (realPosition[0][0] >= limit[0][0] && realPosition[0][1] <= limit[0][1])) {
                    inGame = false;
                }
            }
        }
        /**
         * This is snake's moving.
         */
        if (inGame){
            // body of snake
            for (int i = dots; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }
            /**
             * left1, right1, up1, down1 are independent variables from the movement of the snake to remove the bug when turning the snake.
             */
            // head of snake
            if (left) {
                x[0] -= DOT_SIZE;
                left1 = true;
                right1 = up1 = down1 = false;
            }
            if (right) {
                x[0] += DOT_SIZE;
                right1 = true;
                left1 = up1 = down1 = false;
            }
            if (up) {
                y[0] -= DOT_SIZE;
                up1 = true;
                right1 = left1 = down1 = false;
            }
            if (down) {
                y[0] += DOT_SIZE;
                down1 = true;
                right1 = up1 = left1 = false;
            }
        }
    }

    /**
     * This method is responsible for the suicide of snakes and rendering it from the beginning of the field to the end and vice versa.
     */
    public void checkCollisions(){
        checkFood();
        checkBonusFood();
        for(int i = dots; i > 0; i--){
            if (i > 3 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }
        if (x[0] >= ALL_DOTS){
            x[0] = 0;
        }
        if (x[0] < 0){
            x[0] = SIZE;
        }
        if (y[0] >= ALL_DOTS){
            y[0] = 0;
        }
        if (y[0] < 0){
            y[0] = SIZE;
        }
    }


    // Todo: Bonus Food!

    /**
     * These methods response for all of bonus food.
     */

    /**
     * This method is creat bonus food in the field randomly.
     */
    private void createBonusFood() {
        foodBonusX = new Random().nextInt(24) * DOT_SIZE;
        foodBonusY = new Random().nextInt(24) * DOT_SIZE;
    }

    /**
     * @return bonus food's array.
     */
    private int[][] getRealPositionBonusFood(){
        int[][] posit = new int[2][2];
        posit[0][0] = foodBonusX;
        posit[0][1] = foodBonusX + 25;
        posit[1][0] = foodBonusY;
        posit[1][1] = foodBonusY + 25;
        return posit;
    }

    /**
     * This method check if bonus food is in collision(in map.java)(depend on maps), it's rendered again randomly.
     */
    private boolean checkBonusFoodInMaps(){
        int[][] realPosition = getRealPositionBonusFood();
        for (int[][] limit : mapps) {
            if ((realPosition[0][0] >= limit[0][0] && realPosition[0][1] <= limit[0][1]) && (realPosition[1][0] >= limit[1][0] && realPosition[1][1] <= limit[1][1])) {
                createBonusFood();
                return false;
            }
        }
        return true;
    }

    /**
     * This method see if snake ate bonus food, bonus food will disappear for 20 seconds and will add more points in the score.
     */
    private void checkBonusFood() {
        if (x[0] == foodBonusX && y[0] == foodBonusY){
            dots++;
            hideBonusFood();
            display.waitToHide = 0;
            score += scoreDifference * 3;
            display.drawScores();
        }
    }

    /**
     * This method check if bonus food is in snake, bonus food will go out from the field or disappear(-100, -100).
     */
    public boolean checkBonusFoodInSnake(){
        for (int pos = 0; pos < dots; pos++){
            if ((x[pos] == foodBonusX) &&  (y[pos] == foodBonusY)) {
                foodBonusX = -100;
                foodBonusY = -100;
                return false;
            }
        }
        return true;
    }

    /**
     * This method see if bonus food appeared in snake or in walls of maps,
     * other(previous) methods will create bonus food randomly again until this one is creating not in the snake and walls.
     */
    public void showBonusFood(){
        boolean checked = checkBonusFoodInSnake();
        boolean checked2 = checkBonusFoodInMaps();
        while(!(checked && checked2) && (foodBonusX != -100 && foodBonusY != -100)){
            if (!checked) {
                checked = checkBonusFoodInSnake();
            }
            else{
                checked2 = checkBonusFoodInMaps();
            }
        }
        createBonusFood();
    }

    /**
     * This is where is the bonus food going when it disappear.
     */
    public void hideBonusFood(){
        foodBonusX = -100;
        foodBonusY = -100;
    }


    // Todo: Food!

    /**
     * These methods response for all of food.
     */

    /**
     * This method is creat bonus food in the field randomly.
     */
    private void createFood() {
        foodX = new Random().nextInt(24) * DOT_SIZE;
        foodY = new Random().nextInt(24) * DOT_SIZE;
    }

    /**
     * @return food's array.
     */
    private int[][] getRealPositionFood(){
        int[][] posit = new int[2][2];
        posit[0][0] = foodX;
        posit[0][1] = foodX + 25;
        posit[1][0] = foodY;
        posit[1][1] = foodY + 25;
        return posit;
    }

    /**
     * This method check if food is in collision(in map.java)(depend on maps), it's rendered again randomly.
     */
    private boolean checkFoodInMaps(){
        int[][] realPosition = getRealPositionFood();
        for (int[][] limit : mapps) {
            if ((realPosition[0][0] >= limit[0][0] && realPosition[0][1] <= limit[0][1]) && (realPosition[1][0] >= limit[1][0] && realPosition[1][1] <= limit[1][1])) {
                createFood();
                return false;
            }
        }
        return true;
    }

    /**
     * This method see if snake ate food, snake will grow up by 1 dot and will add points(depending on level) in the score.
     * And it again creates food.
     */
    private void checkFood() {
        if (x[0] == foodX && y[0] == foodY){
            dots++;
            createFood();
            score += scoreDifference;
            display.drawScores();
        }
    }

    /**
     * This method check if food is in snake.
     */
    public boolean checkFoodInSnake(){
        for (int pos = 0; pos < dots; pos++){
            if ((x[pos] == foodX) &&  (y[pos] == foodY)) {
                createFood();
                return false;
            }
        }
        return true;
    }

    // Todo: Images && Graphics && Listeners!

    /**
     * In this methods we get images.
     */
    public void getImages(){
        ImageIcon imageFood = new ImageIcon("food.png");
        food = imageFood.getImage();
        ImageIcon imageFoodBonus = new ImageIcon("bonus.png");
        foodBonus = imageFoodBonus.getImage();
        ImageIcon imageHead = new ImageIcon("snake/img/head.png");
        headImg = imageHead.getImage();
        ImageIcon imageBody = new ImageIcon("snake/img/body.png");
        bodyImg = imageBody.getImage();

        ImageIcon imageGrass = new ImageIcon("grass.png");
        grass = imageGrass.getImage();
        ImageIcon imageMap = new ImageIcon("./maps/img/" + mapDirectory + ".png");
        mapIm = imageMap.getImage();
    }

    /**
     * This method draws our game.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame){
            g.drawImage(grass, 0, 0,this);
            g.drawImage(food, foodX, foodY,this);
            g.drawImage(foodBonus, foodBonusX, foodBonusY,this);
            g.drawImage(mapIm, 0,0, this);
            for (int i = 0; i < dots; i++){
                if (i != 0){
                    g.drawImage(bodyImg, x[i], y[i], this);
                }
                else{
                    g.drawImage(headImg, x[i], y[i], this);
                }
            }
        }
        /**
         * If snake is dead we call class GameOver.
         */
        else{
            new GameOver(GameField.this);
            display.dispose();
        }
    }

    /**
     * This method repaint our game every moment(Timer) or when user drag our frame.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * This one see if food appeared in snake or in walls of maps,
         * other(previous) methods will create bonus food randomly again until this one is creating not in the snake and walls.
         */
        if (inGame){
            boolean checked = checkFoodInSnake();
            boolean checked2 = checkFoodInMaps();

            while(!(checked && checked2)){
                if (!checked) {
                    checked = checkFoodInSnake();
                }
                else{
                    checked2 = checkFoodInMaps();
                }
            }
            moveSnake();
            checkCollisions();
        }
        repaint();
    }

    /**
     * This subclass response what happened if the user click on the key(right, left, down, up).
     */
    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right1){
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left1){
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down1){
                up = true;
                left = false;
                right = false;
            }
            if (key == KeyEvent.VK_DOWN && !up1){
                down = true;
                left = false;
                right = false;
            }
        }
    }
}
