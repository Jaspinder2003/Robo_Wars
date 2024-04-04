/**
 * Name: Jaspinder Singh Maan
 * Tutorial : TUT 15
 * UCID: 30209953
 */
package rw.util;

import rw.battle.Battle;
import rw.battle.Entity;
import rw.battle.Maximal;
import rw.battle.PredaCon;
import rw.battle.Wall;
import rw.enums.WeaponType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.IntStream;

public class Writer {

    public static void saveBattle(Battle battle, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            // Write rows and columns
            writer.write(battle.getRows() + "\n" + battle.getColumns() + "\n");

            // Iterate over each cell
            IntStream.range(0, battle.getRows()).forEach(row -> {
                IntStream.range(0, battle.getColumns()).forEach(column -> {
                    Entity entity = battle.getEntity(row, column);
                    try {
                        if (entity instanceof Maximal) {
                            Maximal maximal = (Maximal) entity;
                            // Format: row,column,MAXIMAL,symbol,name,health,weaponStrength,armorStrength
                            writer.write(String.format("%d,%d,MAXIMAL,%s,%s,%d,%d,%d\n",
                                    row, column, maximal.getSymbol(), maximal.getName(), maximal.getHealth(),
                                    maximal.weaponStrength(), maximal.armorStrength()));
                        } else if (entity instanceof PredaCon) {
                            PredaCon predaCon = (PredaCon) entity;
                            // Format: row,column,PREDACON,symbol,name,health,weaponType
                            writer.write(String.format("%d,%d,PREDACON,%s,%s,%d,%s\n",
                                    row, column, predaCon.getSymbol(), predaCon.getName(), predaCon.getHealth(),
                                    predaCon.getWeaponType().toString().charAt(0)));
                        } else if (entity instanceof Wall) {
                            // Format: row,column,WALL
                            writer.write(String.format("%d,%d,WALL\n", row, column));
                        } else {
                            // Empty cell
                            writer.write(String.format("%d,%d\n", row, column));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });
        } catch (IOException e) {
            System.err.println("Error writing to file: " + file.getPath());
            e.printStackTrace();
        }
    }
}
