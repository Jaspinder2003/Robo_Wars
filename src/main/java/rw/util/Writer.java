package rw.util;

import rw.battle.Battle;
import rw.battle.Entity;
import rw.battle.Maximal;
import rw.battle.PredaCon;
import rw.battle.Wall;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {

    public static void saveBattle(Battle battle, File file) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            int rows = battle.getRows();
            int columns = battle.getColumns();
            pw.println(rows + "\n" + columns);

            // Iterate over each position in the battle grid
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    Entity entity = battle.getEntity(i, j);
                    if (entity != null) { // Check if there's an entity at the current position
                        String line = entityToLine(entity, i, j);
                        if (line != null) {
                            pw.println(line);
                        }
                    }
                }
            }
        } // PrintWriter is auto-closed here, thanks to try-with-resources
    }


    private static String entityToLine(Entity entity, int row, int column) {
        if (entity instanceof Maximal) {
            Maximal maximal = (Maximal) entity;

            return String.format("%d,%d,MAXIMAL,%c,%s,%d,%d,%d", row, column, maximal.getSymbol(),
                    maximal.getName(), maximal.getHealth(), maximal.weaponStrength(), maximal.armorStrength());
        } else if (entity instanceof PredaCon) {
            PredaCon predaCon = (PredaCon) entity;
            return String.format("%d,%d,PREDACON,%c,%s,%d,%s", row, column, predaCon.getSymbol(),
                    predaCon.getName(), predaCon.getHealth(), predaCon.getWeaponType().name());
        } else if (entity instanceof Wall) {
            return String.format("%d,%d,WALL", row, column);
        }
        // Return null if it's an unknown entity or empty space
        return null;
    }
}
