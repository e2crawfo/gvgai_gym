package ontology.sprites.npc;

import core.vgdl.VGDLRegistry;
import core.vgdl.VGDLSprite;
import core.content.SpriteContent;
import core.game.Game;
import ontology.Types;
import tools.Direction;
import tools.Utils;
import tools.Vector2d;
import tools.pathfinder.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA. User: Diego Date: 21/10/13 Time: 18:14 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class GoalDirected extends RandomNPC {

    // parameters
    public double epsilon;
    public boolean path;
    public boolean los;
    public float maxDistance;
    public String chase;
    public String flee;

    // internal storage
    public boolean fleeing;
    public int[] ichase;
    public int[] iflee;
    public int[] all_itype;
    private Vector2d lastKnownTargetPosition;

    public GoalDirected() {
    }

    public GoalDirected(Vector2d position, Dimension size, SpriteContent cnt) {
        this.init(position, size);
        loadDefaults();
        this.parseParameters(cnt);
    }

    protected void loadDefaults() {
        super.loadDefaults();
        epsilon = 0.0;
        path = false;
        los = false;
        maxDistance = -1;
        chase = null;
        flee = null;
        fleeing = false;
        lastKnownTargetPosition = null;
    }

    public void postProcess() {
        super.postProcess();

        ichase = VGDLRegistry.GetInstance().explode_better(chase);
        iflee = VGDLRegistry.GetInstance().explode_better(flee);

        all_itype = new int[ichase.length + iflee.length];
        for (int i = 0; i < ichase.length; i++) {
            all_itype[i] = ichase[i];
        }
        for (int i = 0; i < iflee.length; i++) {
            all_itype[i + ichase.length] = iflee[i];
        }
        is_stochastic = true;
    }

    public boolean can_see(VGDLSprite s) {
        boolean canSee = false;
        if (prevAction == Types.DNONE || prevAction == Types.DNIL) {
            canSee = false;
        } else if (prevAction.equals(Types.DDOWN)) {
            if ((s.rect.x == rect.x && s.rect.y >= rect.y)) {
                canSee = true;
            }
        } else if (prevAction.equals(Types.DUP)) {
            if ((s.rect.x == rect.x && s.rect.y <= rect.y)) {
                canSee = true;
            }
        } else if (prevAction.equals(Types.DLEFT)) {
            if ((s.rect.x <= rect.x && s.rect.y == rect.y)) {
                canSee = true;
            }
        } else if (prevAction.equals(Types.DRIGHT)) {
            if ((s.rect.x >= rect.x && s.rect.y == rect.y)) {
                canSee = true;
            }
        }

        return canSee;
    }

    protected ArrayList<VGDLSprite> closestRelevantSprites(Game game){
        ArrayList<VGDLSprite> targets = new ArrayList<VGDLSprite>();
        double bestDist = Double.MAX_VALUE;
        int i = 0;
        for(int it : all_itype) {
            Iterator<VGDLSprite> spriteIt = game.getSubSpritesGroup(it);
            if (spriteIt == null) {
                continue;
            }
            while (spriteIt.hasNext()) {
                VGDLSprite s = spriteIt.next();
                if (!los || can_see(s)) {
                    // TODO: include path in distance calculation
                    double distance = this.physics.distance(rect, s.rect);

                    if (maxDistance >= 0 && distance > maxDistance) {
                        continue;
                    }

                    if (distance < bestDist) {
                        bestDist = distance;
                        targets.clear();
                        targets.add(s);
                        fleeing = i >= ichase.length;
                    } else if (distance == bestDist) {
                        targets.add(s);
                        fleeing = i >= ichase.length;
                    }
                }
            }

            i++;
        }

        return targets;
    }

    public void update(Game game) {
        super.updatePassive();

        ArrayList<VGDLSprite> targets = new ArrayList<VGDLSprite>();
        boolean do_random = game.getRandomGenerator().nextDouble() < epsilon;
        if (!do_random) {
            targets = closestRelevantSprites(game);
        }

        ArrayList<Direction> actions = new ArrayList<Direction>();
        if (targets.size() > 0) {
            if (fleeing) {
                double bestDist = Double.MIN_VALUE;
                for (Direction action : Types.DBASEDIRS) {
                    Rectangle r = new Rectangle(this.rect);
                    r.translate((int) action.x(), (int) action.y());
                    double minDist = Double.MAX_VALUE;
                    for(VGDLSprite target : targets) {
                        double newDist = this.physics.distance(r, target.rect);
                        if (newDist < minDist) {
                            minDist = newDist;
                        }
                    }
                    if (minDist > bestDist) {
                        bestDist = minDist;
                        actions.clear();
                        actions.add(action);
                    } else if (minDist == bestDist) {
                        actions.add(action);
                    }
                }
            } else {
                VGDLSprite target = targets.get(0);
                if (path) {
                    ArrayList<Node> planned_path = game.getPath(this.getPosition(), target.getPosition());

                    if (planned_path == null) {
                        if (lastKnownTargetPosition != null) {
                            planned_path = game.getPath(this.getPosition(), lastKnownTargetPosition);
                        }
                    } else {
                        lastKnownTargetPosition = target.getPosition().copy();
                    }

                    if (planned_path != null && planned_path.size() > 0) {
                        Vector2d v = planned_path.get(0).comingFrom;
                        actions.add(new Direction(v.x, v.y));
                    }
                } else {
                    double bestDist = Double.MAX_VALUE;
                    for (Direction action : Types.DBASEDIRS) {
                        Rectangle r = new Rectangle(this.rect);
                        r.translate((int) action.x(), (int) action.y());
                        double newDist = this.physics.distance(r, target.rect);

                        if (newDist < bestDist) {
                            bestDist = newDist;
                            actions.clear();
                            actions.add(action);
                        } else if (newDist == bestDist) {
                            actions.add(action);
                        }
                    }
                }
            }
        }

        if (actions.size() == 0) {
            actions.addAll(Arrays.asList(Types.DBASEDIRS));
        }

        Direction[] _actions = actions.toArray(new Direction[actions.size()]);
        Direction action = (Direction) Utils.choice(_actions, game.getRandomGenerator());
        this.physics.activeMovement(this, action, this.speed);
    }

    public VGDLSprite copy() {
        GoalDirected newSprite = new GoalDirected();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(VGDLSprite target) {
        GoalDirected targetSprite = (GoalDirected) target;

        targetSprite.fleeing = this.fleeing;
        targetSprite.ichase = this.ichase.clone();
        targetSprite.iflee = this.iflee.clone();
        targetSprite.all_itype = this.all_itype.clone();
        targetSprite.lastKnownTargetPosition = lastKnownTargetPosition != null ? lastKnownTargetPosition.copy() : null;

        targetSprite.chase = this.chase;
        targetSprite.flee = this.flee;
        targetSprite.maxDistance = this.maxDistance;
        targetSprite.epsilon = this.epsilon;
        targetSprite.path = this.path;
        targetSprite.los = this.los;

        super.copyTo(targetSprite);
    }

    @Override
    public ArrayList<String> getDependentSprites() {
        ArrayList<String> result = new ArrayList<String>();

        String[] stypes1 = chase.split(",");
        for (String s : stypes1)
            result.add(s);

        String[] stypes2 = flee.split(",");
        for (String s : stypes2)
            result.add(s);

        return result;
    }
}
