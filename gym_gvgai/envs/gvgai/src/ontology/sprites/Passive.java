package ontology.sprites;

import java.awt.Dimension;

import core.vgdl.VGDLSprite;
import core.content.SpriteContent;
import ontology.Types;
import tools.Vector2d;

/**
 * Created with IntelliJ IDEA. User: Diego Date: 17/10/13 Time: 12:44 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Passive extends VGDLSprite {
    public Passive() {
    }

    public Passive(Vector2d position, Dimension size, SpriteContent cnt) {
        this.init(position, size);
        loadDefaults();
        this.parseParameters(cnt);
    }

    protected void loadDefaults() {
        super.loadDefaults();
        color = Types.RED;
    }

    public VGDLSprite copy() {
        Passive newSprite = new Passive();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(VGDLSprite target) {
        Passive targetSprite = (Passive) target;
        super.copyTo(targetSprite);
    }
}
