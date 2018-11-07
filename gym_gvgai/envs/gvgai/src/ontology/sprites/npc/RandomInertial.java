package ontology.sprites.npc;

import java.awt.Dimension;

import core.content.SpriteContent;
import core.vgdl.VGDLSprite;
import ontology.Types;
import tools.Vector2d;

/**
 * Created with IntelliJ IDEA. User: Diego Date: 21/10/13 Time: 18:13 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class RandomInertial extends RandomNPC {
    public RandomInertial() {
    }

    public RandomInertial(Vector2d position, Dimension size, SpriteContent cnt) {
        this.init(position, size);
        loadDefaults();
        this.parseParameters(cnt);
    }

    protected void loadDefaults() {
        super.loadDefaults();
        physicstype = Types.CONT;
    }

    public VGDLSprite copy() {
        RandomInertial newSprite = new RandomInertial();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(VGDLSprite target) {
        RandomInertial targetSprite = (RandomInertial) target;
        super.copyTo(targetSprite);
    }
}
