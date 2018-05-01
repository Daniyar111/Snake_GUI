import java.io.*;

/**
 * This class read files from ./maps/img/bin/database. which have our maps' collisions
 * For example(0, 625 % 0, 25): x initial is 0, x final is 625; y initial is 0, y final is 25;
 * We split it in 3d array: 1) lines; 2) x initial and x final; 3) y initial and y final;
 */
public class map {
    public static BufferedReader fileR;
    public static int[][][] getMap(String dirMap){
        String[] getCode = readFile("maps/img/bin/" + dirMap + ".db");
        int[][][] limits = new int[getCode.length][2][2];
        String[] temp, x, y;
        int i = 0;
        for (String r: getCode){
            temp = r.split("%");
            x = temp[0].split(",");
            y = temp[1].split(",");
            try {
                if(!x[0].isEmpty()){
                    limits[i][0][0] = Integer.parseInt(x[0]);
                }
                if(!x[1].isEmpty()){
                    limits[i][0][1] = Integer.parseInt(x[1]);
                }
                if(!y[0].isEmpty()){
                    limits[i][1][0] = Integer.parseInt(y[0]);
                }
                if(!y[1].isEmpty()){
                    limits[i][1][1] = Integer.parseInt(y[1]);
                }
                i++;
            }
            catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        return limits;
    }

    public static String[] readFile(String directory){
        String line;
        String[] arr = {};
        try {
            int i = 0;
            fileR = new BufferedReader(new FileReader(directory));
            while ((fileR.readLine()) != null){
                i++;
            }
            arr = new String[i];
            fileR = new BufferedReader(new FileReader(directory));
            i = 0;
            while ((line = fileR.readLine()) != null){
                arr[i] = line;
                i++;
            }
            fileR.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return arr;
    }
}
