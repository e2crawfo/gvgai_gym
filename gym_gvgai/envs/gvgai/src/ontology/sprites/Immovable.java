package ontology.sprites;

import java.awt.Dimension;

import core.content.SpriteContent;
import core.vgdl.VGDLSprite;
import ontology.Types;
import tools.Vector2d;

/**
 * Created with IntelliJ IDEA. User: Diego Date: 17/10/13 Time: 12:39 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Immovable extends VGDLSprite {
    // A static object. In order to make something that objects can't move through,
    // need to use the `step_back` effect.

    public Immovable() {
    }

    public Immovable(Vector2d position, Dimension size, SpriteContent cnt) {
        this.init(position, size);
        loadDefaults();
        this.parseParameters(cnt);
    }

    public void postProcess() {
        super.postProcess();
    }

    protected void loadDefaults() {
        super.loadDefaults();
        color = Types.GRAY;
        is_static = true;
    }

    public VGDLSprite copy() {
        Immovable newSprite = new Immovable();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(VGDLSprite target) {
        Immovable targetSprite = (Immovable) target;
        super.copyTo(targetSprite);
    }
}
