package ir.ac.kntu.service;

import ir.ac.kntu.Constants;
import ir.ac.kntu.Utils;
import ir.ac.kntu.components.GameMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapProvider {

    public List<GameMap> list() throws IOException {

        File[] files = Utils.getResourceFolderFiles(Constants.MAP_DIR);
        List<GameMap> maps = new ArrayList<>();
        for (File file : files) {
            List<String> lines = readLine(file);
            int rows = lines.size();
            char[][] signs = new char[rows][];
            for (int i = 0; i < rows; i++) {
                String line = lines.get(i);
                String[] rowSigns = line.split(",");
                for (int j = 0; j < rowSigns.length; j++) {
                    signs[i][j] = rowSigns[j].charAt(0);
                }
            }
            maps.add(new GameMap(file.getName(), signs));
        }
        return maps;
    }

    private static List<String> readLine(File file) throws IOException {

        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } finally {
            if (br != null) {
                br.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
    }
}
