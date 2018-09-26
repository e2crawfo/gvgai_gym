// package tracks.singleLearning.utils;
import core.competition.CompetitionParameters;
import tools.ElapsedWallTimer;
import tracks.ArcadeMachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static core.competition.CompetitionParameters.ROLLOUT_DIR;
import static core.competition.CompetitionParameters.ROLLOUT_FREQ;
import static core.competition.CompetitionParameters.ACTION_TIME;

/**
 * Created by dperez on 01/06/2017.
 */
public class DoRollout {

    public static void main(String[] args) throws Exception {
        /** Get arguments */
        Map<String, List<String>> params = new HashMap<>();
        List<String> options = null;
        for (int i = 0; i < args.length; i++) {
            final String a = args[i];
            System.out.println(a);
            if (a.charAt(0) == '-') {
                if (a.length() < 2) {
                    System.err.println("Error at argument " + a);
                    return;
                }
                options = new ArrayList<>();
                params.put(a.substring(1), options);
            } else if (options != null) {
                options.add(a);
            }
            else {
                System.err.println("Illegal parameter usage");
                return;
            }
        }

        // Available controllers:
        String sampleRandomController = "tracks.singlePlayer.simple.simpleRandom.Agent";
        String sampleOLMCTSController = "tracks.singlePlayer.advanced.sampleMCTS.Agent";
        String sampleOLETSController = "tracks.singlePlayer.advanced.olets.Agent";
        String repeatOLETS = "tracks.singlePlayer.tools.repeatOLETS.Agent";

        String game_file = params.get("game_file").get(0);
        String level_file = params.get("level_file").get(0);
        ROLLOUT_DIR = params.get("rollout_dir").get(0);
        int seed = Integer.parseInt(params.get("seed").get(0));
        String action_file = ROLLOUT_DIR + "/actions.txt";
        if (params.containsKey("freq")) {
            ROLLOUT_FREQ = Integer.parseInt(params.get("freq").get(0));
        }
        if (params.containsKey("action_time")) {
            ACTION_TIME = Integer.parseInt(params.get("action_time").get(0));
        }
        String agent = sampleOLMCTSController;
        if (params.containsKey("random_agent")) {
            agent = sampleRandomController;
        }
        boolean visuals = true;
        ArcadeMachine.runOneGame(game_file, level_file, visuals, agent, action_file, seed, 0);
    }
}