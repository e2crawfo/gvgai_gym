package ontology.sprites;

import java.awt.Dimension;

import core.content.SpriteContent;
import core.vgdl.VGDLSprite;
import tools.Vector2d;

/**
 * Created by diego on 17/02/14.
 */
public class Door extends Immovable {
    // Seems to have no distinguishing features...

    public Door() {
    }

    public Door(Vector2d position, Dimension size, SpriteContent cnt) {
        this.init(position, size);
        loadDefaults();
        this.parseParameters(cnt);
    }

    public void postProcess() {
        super.postProcess();
    }

    protected void loadDefaults() {
        super.loadDefaults();
        portal = true;
    }

    public VGDLSprite copy() {
        Door newSprite = new Door();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(VGDLSprite target) {
        Door targetSprite = (Door) target;
        super.copyTo(targetSprite);
    }
}
