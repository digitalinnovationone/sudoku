package br.com.dio;

import br.com.dio.ui.custom.screen.MainScreen;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class UIMain {

    public static void main(String[] args) {
        final java.util.Map<String, String> gameConfig;
        final int BOARD_LIMIT = 9;
        gameConfig = new java.util.HashMap<>();
        // Preenche todas as posições com 0,false
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (int j = 0; j < BOARD_LIMIT; j++) {
                gameConfig.put(j + "," + i, "0,false");
            }
        }
        // Se houver argumentos, sobrescreve as posições informadas
        if (args.length > 0) {
            for (String arg : args) {
                String[] parts = arg.split(";");
                String[] pos = parts[0].split(",");
                // formato linha,coluna;valor,fixed -> pos[0]=linha, pos[1]=coluna
                String key = pos[1] + "," + pos[0]; // coluna,linha
                gameConfig.put(key, parts[1]);
            }
        }
        var mainsScreen = new MainScreen(gameConfig);
        mainsScreen.buildMainScreen();
    }

}