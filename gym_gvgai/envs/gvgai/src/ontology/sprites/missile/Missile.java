package ontology.sprites.missile;

import java.awt.Dimension;

import core.content.SpriteContent;
import core.game.Game;
import core.vgdl.VGDLSprite;
import ontology.Types;
import tools.Direction;
import tools.Utils;
import tools.Vector2d;

/**
 * Created with IntelliJ IDEA. User: Diego Date: 21/10/13 Time: 17:35 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Missile extends VGDLSprite {
    public Missile() {
    }

    public Missile(Vector2d position, Dimension size, SpriteContent cnt) {
        this.init(position, size);
        loadDefaults();
        this.parseParameters(cnt);
    }

    protected void loadDefaults() {
        super.loadDefaults();
        speed = 1;
    }

    public void update(Game game) {
        if (orientation.equals(Types.DNONE)) {
            orientation = (Direction) Utils.choice(Types.DBASEDIRS, game.getRandomGenerator());
        }

        this.updatePassive();
    }

    public VGDLSprite copy() {
        Missile newSprite = new Missile();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(VGDLSprite target) {
        Missile targetSprite = (Missile) target;
        super.copyTo(targetSprite);
    }
}
