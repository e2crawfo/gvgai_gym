package ontology.sprites;

import java.awt.Dimension;

import core.content.SpriteContent;
import core.game.Game;
import core.vgdl.VGDLSprite;
import tools.Vector2d;

/**
 * Created with IntelliJ IDEA. User: Diego Date: 17/10/13 Time: 12:45 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Flicker extends VGDLSprite {
    // An object that dies after `limit` update steps.

    public int limit;
    public int age;

    public Flicker() {
    }

    public Flicker(Vector2d position, Dimension size, SpriteContent cnt) {
        this.init(position, size);
        loadDefaults();
        this.parseParameters(cnt);
    }

    protected void loadDefaults() {
        super.loadDefaults();
        limit = 1;
        age = 0;
    }

    public void update(Game game) {
        super.update(game);

        if (age > limit) {
            game.killSprite(this, false);
        }
        age++;
    }

    public VGDLSprite copy() {
        Flicker newSprite = new Flicker();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(VGDLSprite target) {
        Flicker targetSprite = (Flicker) target;
        targetSprite.limit = this.limit;
        targetSprite.age = this.age;
        super.copyTo(targetSprite);
    }
}
