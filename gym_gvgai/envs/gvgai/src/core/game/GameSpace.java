package core.game;

import java.util.HashMap;

import core.content.GameContent;
import core.content.ParameterContent;

/**
 * Created by dperez on 21/01/2017.
 */
public class GameSpace extends BasicGame {

    /**
     * Default constructor for a basic game.
     *
     * @param content Contains parameters for the game.
     */
    public GameSpace(GameContent content) {
        super(content);
        parameters = new HashMap<>();
    }

    /**
     * Builds a level, receiving a file name.
     *
     * @param gamelvl file name containing the level.
     */
    @Override
    public void buildLevel(String gamelvl, int randomSeed) {

        // Need to extract my parameters here.
        super.buildLevel(gamelvl, randomSeed);
    }

    public void addParameterContent(ParameterContent pc) {
        if (parameters == null) {
            parameters = new HashMap<>();
        }
        parameters.put(pc.identifier, pc);
    }

    @Override
    public HashMap<String, ParameterContent> getParameters() {
        return parameters;
    }
}
