package rw.util;

import rw.battle.Battle;
import rw.battle.Maximal;
import rw.battle.PredaCon;
import rw.battle.Wall;
import rw.enums.WeaponType;

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
                    String[] parts=line.split(",");
                    int row = Integer.parseInt(parts[0]);
                    int column=Integer.parseInt(parts[1]);

                    if(parts.length>3){
                        String type=parts[2];
                        String name=parts[3];
                        char symbol=parts[3].charAt(0);
                        int Health=Integer.parseInt(parts[5]);
                        char weaponType=parts[4].charAt(0);
                        if(type.equals("MAXIMAL")){
                            int weaponStrength = Integer.parseInt(parts[6]);
                            int armorStrength = Integer.parseInt(parts[7]);
                            Maximal maximal = new Maximal(symbol, name, Health, weaponStrength, armorStrength);
                            battle.addEntity(row, column, maximal);
                        }
                        if(type.equals("PREDACON")){
                            char weaponChar = parts[6].charAt(0); // Get the first character of the weapon type
                            WeaponType W = WeaponType.getWeaponType(weaponChar);
                            PredaCon predaCon = new PredaCon(symbol, name, Health, W);
                            battle.addEntity(row, column, predaCon);
                        }
                    } else if (parts.length==3) {
                        String type=parts[2];
                        if(type.equals("WALL")){
                            battle.addEntity(row, column, Wall.getWall());
                        }
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found: " + file.getPath());
        }
        return battle;
    }
}
