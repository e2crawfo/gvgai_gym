package tracks.multiPlayer.tools.heuristics;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservationMulti;
import ontology.Types;
import tools.Vector2d;

/**
 * Created with IntelliJ IDEA. User: ssamot Date: 11/02/14 Time: 15:44 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class SimpleStateHeuristic extends StateHeuristicMulti {

    double initialNpcCounter = 0;

    public SimpleStateHeuristic(StateObservationMulti stateObs) {

    }

    public double evaluateState(StateObservationMulti stateObs, int playerID) {
        Vector2d avatarPosition = stateObs.getAvatarPosition(playerID);
        ArrayList<Observation>[] npcPositions = stateObs.getNPCPositions(avatarPosition);
        ArrayList<Observation>[] portalPositions = stateObs.getPortalsPositions(avatarPosition);
        stateObs.getAvatarResources(playerID);

        stateObs.getNPCPositions();

        double won = 0;
        int oppID = (playerID + 1) % stateObs.getNoPlayers();
        Types.WINNER[] winners = stateObs.getMultiGameWinner();

        boolean bothWin = winners[playerID] == Types.WINNER.PLAYER_WINS && winners[oppID] == Types.WINNER.PLAYER_WINS;
        boolean meWins = winners[playerID] == Types.WINNER.PLAYER_WINS && winners[oppID] == Types.WINNER.PLAYER_LOSES;
        boolean meLoses = winners[playerID] == Types.WINNER.PLAYER_LOSES && winners[oppID] == Types.WINNER.PLAYER_WINS;
        if (meWins || bothWin) {
            won = 1000000000;
        } else if (meLoses) {
            return -999999999;
        }

//        if (winners[playerID] == Types.WINNER.PLAYER_WINS) {
//            won = 1000000000;
//        } else if (winners[playerID] == Types.WINNER.PLAYER_LOSES) {
//            return -999999999;
//        }

        double minDistance = Double.POSITIVE_INFINITY;
        int npcCounter = 0;
        if (npcPositions != null) {
            for (ArrayList<Observation> npcs : npcPositions) {
                if (npcs.size() > 0) {
                    minDistance = npcs.get(0).sqDist; // This is the (square) distance to the closest NPC.
                    npcCounter += npcs.size();
                }
            }
        }

        if (portalPositions == null) {

            double score = 0;
            if (npcCounter == 0) {
                score = stateObs.getGameScore(playerID) + won * 100000000;
            } else {
                score = -minDistance / 100.0 + -npcCounter * 100.0 + stateObs.getGameScore(playerID) + won * 100000000;
            }

            return score;
        }

        double minDistancePortal = Double.POSITIVE_INFINITY;
        Vector2d minObjectPortal = null;
        for (ArrayList<Observation> portals : portalPositions) {
            if (portals.size() > 0) {
                minObjectPortal = portals.get(0).position; // This is the closest portal
                minDistancePortal = portals.get(0).sqDist; // This is the (square) distance to the closest portal
            }
        }

        double score = 0;
        if (minObjectPortal == null) {
            score = stateObs.getGameScore() + won * 100000000;
        } else {
            score = stateObs.getGameScore() + won * 1000000 - minDistancePortal * 10.0;
        }

        return score;
    }

}
