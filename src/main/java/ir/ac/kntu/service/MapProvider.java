package ir.ac.kntu.service;

import ir.ac.kntu.Constants;
import ir.ac.kntu.Utils;
import ir.ac.kntu.data.GameMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapProvider {

    public List<GameMap> list() throws IOException {

        File[] files = Utils.getResourceFolderFiles(Constants.MAP_DIR);
        List<GameMap> maps = new ArrayList<>();
        for (File file : files) {
            List<String> lines = Utils.readLine(file);
            int rows = lines.size();
            char[][] signs = new char[rows][];
            for (int i = 0; i < rows; i++) {
                String line = lines.get(i);
                String[] rowSigns = line.split(",");
                signs[i] = new char[rowSigns.length];
                for (int j = 0; j < rowSigns.length; j++) {
                    signs[i][j] = rowSigns[j].charAt(0);
                }
            }
            maps.add(new GameMap(file.getName(), signs));
        }
        return maps;
    }
}
