package rw.util;

import rw.battle.Battle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class to assist reading in battle file
 *
 * @author Jonathan Hudson
 * @version 1.0
 */
public final class Reader {

    public static Battle loadBattle(File file) {
        Battle battle=null;
        try(Scanner scanner=new Scanner(file)){
            int rows= scanner.nextInt();
            int col=scanner.nextInt();
            battle=new Battle(rows,col);

            while (scanner.hasNextLine()){
                String line=scanner.nextLine().trim();
                if(!line.isEmpty()){
                    String[] parts=line.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
                    int row = Integer.parseInt(parts[0]);
                    int column=Integer.parseInt(parts[1]);
                    String type=parts[2];
                    String name=parts[3];
                    int Health=Integer.parseInt(parts[4]);
                    char weaponType=parts[4].charAt(0);
                }
            }
            return null;
        }
        catch (FileNotFoundException e){
            System.err.println("File not found: "+file.getPath());
            return null;
        }
    }
}
