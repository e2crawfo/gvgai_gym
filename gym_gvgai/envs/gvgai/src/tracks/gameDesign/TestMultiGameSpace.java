package tracks.gameDesign;

import java.util.Random;

import tools.Utils;
import tracks.DesignMachine;

/**
 * Created with IntelliJ IDEA. User: Diego Date: 04/10/13 Time: 16:29 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class TestMultiGameSpace {

    public static void main(String[] args) {
        String sampleRHEA = "tracks.multiPlayer.advanced.sampleRHEA.Agent";
        int seed = new Random().nextInt();

        // Load available games
        String spGamesCollection = "examples/all_games_gd2p.csv";
        String[][] games = Utils.readGames(spGamesCollection);

        // Game and level to play
        int gameIdx = 0;
        int levelIdx = 0; // level names from 0 to 4 (game_lvlN.txt).
        String gameName = games[gameIdx][1];
        String game = games[gameIdx][0];
        String level1 = game.replace(gameName, gameName + "_lvl" + levelIdx);

        /** Game Spaces stuff starts here **/

        // Reads VGDL and loads game with parameters.
        DesignMachine dm = new DesignMachine(game);

        // 0: Assigns values to parameters to play the game. Two ways: random and
        // explicit.
        // 0.a: Creating an individual at random:
        int[] individual = new int[dm.getNumDimensions()];
        for (int i = 0; i < individual.length; ++i) {
            individual[i] = new Random().nextInt(dm.getDimSize(i));
        }

        // 0.b: Creating a new individual with an int[]:
        // Each parameter will take a value = "lower_bound + i*increment" in the order
        // defined in VGDL
//        int[] individual = new int[]{0,1,2,14,1,4,9,1,5,5,2,4};

        // We can print a report with the parameters and values:
        dm.printValues(individual);

        // 1. Play as a human.
        dm.playGame2P(individual, game, level1, seed);

        // 2. Play with controllers.
//        dm.runOneGame(individual, game, level1, true, controllers, recordActionsFile, seed, 0);

        // 3. Plays validationReps times, reports averaged results; visuals off
//        double wins = 0.0, scores = 0.0;
//        double validationReps = 10;
//        for (int i = 0; i < validationReps; ++i) {
//            double[] results = dm.runOneGame(individual, game, level1, false, controllers, recordActionsFile, seed, 0);
//
//            wins += results[0];
//            scores += results[1];
//        }
//        System.out.printf("%.2f%%, average score: %.2f", 100 * wins / validationReps, scores / validationReps);
    }
}
