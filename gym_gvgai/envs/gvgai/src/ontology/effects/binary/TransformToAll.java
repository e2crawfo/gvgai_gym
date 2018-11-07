package ontology.effects.binary;

import core.vgdl.VGDLRegistry;
import core.vgdl.VGDLSprite;
import core.content.InteractionContent;
import core.game.Game;
import core.logging.Logger;
import core.logging.Message;
import ontology.effects.unary.TransformTo;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA. User: Diego Date: 23/10/13 Time: 15:21 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class TransformToAll extends TransformTo {
    // Transform all sprites of type `stype` to type `stypeTo`.
    // Does nothing to the sprites that cause the effect.
    public String stypeTo;
    public int itypeTo;

    public TransformToAll(InteractionContent cnt) throws Exception {
        super(cnt);
        itypeTo = VGDLRegistry.GetInstance().getRegisteredSpriteValue(stypeTo);
        if (itypeTo == -1) {
            throw new Exception("Undefined sprite " + stypeTo);
        }
    }

    @Override
    public void execute(VGDLSprite sprite1, VGDLSprite sprite2, Game game) {
        Iterator<VGDLSprite> spriteIt = game.getSpriteGroup(itype);
        if (spriteIt != null)
            while (spriteIt.hasNext()) {
                VGDLSprite s = spriteIt.next();
                VGDLSprite newSprite = game.addSprite(itypeTo, s.getPosition(), true);
                super.transformTo(newSprite, s, sprite2, game);
            }
    }

    @Override
    public ArrayList<String> getEffectSprites() {
        ArrayList<String> result = new ArrayList<String>();
        if (stype != null)
            result.add(stype);

        return result;
    }
}
