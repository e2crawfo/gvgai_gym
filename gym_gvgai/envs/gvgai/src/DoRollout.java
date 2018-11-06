
// package tracks.singleLearning.utils;
import static core.competition.CompetitionParameters.ACTION_TIME;
import static core.competition.CompetitionParameters.ROLLOUT_DIR;
import static core.competition.CompetitionParameters.ROLLOUT_FREQ;
import static core.competition.CompetitionParameters.DELAY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.io.File;
import java.nio.file.Paths;

import tracks.ArcadeMachine;

/**
 * Created by dperez on 01/06/2017.
 */
public class DoRollout {

	public static void main(String[] args) throws Exception {
		try {	
			/** Get arguments */
			Map<String, List<String>> params = new HashMap<>();
			List<String> options = null;
			for (final String a : args) {
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
				} else {
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
			String level_dir = params.get("level_dir").get(0);
			String rollout_dir = params.get("rollout_dir").get(0);
			int seed = Integer.parseInt(params.get("seed").get(0));
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
			if (params.containsKey("delay")) {
                   DELAY = Integer.parseInt(params.get("delay").get(0));
			}
			boolean visuals = true;

			File[] level_files = new File(level_dir).listFiles();
			Arrays.sort(level_files);
	
			int i = 0;
			for (File level_file : level_files) {
				if(!level_file.toString().contains("lvl")) {
					continue;
				}
					
				ROLLOUT_DIR = Paths.get(rollout_dir, String.format("rollout%05d", i)).toString();
				(new File(ROLLOUT_DIR)).mkdir();
				String action_file = ROLLOUT_DIR + "/actions.txt";
				ArcadeMachine.runOneGame(game_file, level_file.getAbsolutePath(), visuals, agent, action_file, seed+i, 0);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			System.exit(0);
		}
	}
}