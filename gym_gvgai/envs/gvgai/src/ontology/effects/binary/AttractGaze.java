package ontology.effects.binary;

import java.awt.Rectangle;

import core.content.InteractionContent;
import core.game.Game;
import core.logging.Logger;
import core.logging.Message;
import core.vgdl.VGDLSprite;
import ontology.Types;
import ontology.effects.Effect;

/**
 * Created with IntelliJ IDEA. User: Diego Date: 04/11/13 Time: 15:56 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class AttractGaze extends Effect {
    // Move sprite 1 so that it is looking in same direction as sprite 2,
    // and is "aligned" with sprite 2 (ie they are looking at the same point
    // in the distance)
    public boolean align = false;

    public AttractGaze(InteractionContent cnt) {
        this.parseParameters(cnt);
        setStochastic();
    }

    @Override
    public void execute(VGDLSprite sprite1, VGDLSprite sprite2, Game game) {
        if (sprite1 == null || sprite2 == null) {
            Logger.getInstance().addMessage(new Message(Message.WARNING,
                    "Neither 1st not 2nd sprite can be EOS with AttractGaze interaction."));
            return;
        }

        if (game.getRandomGenerator().nextDouble() < prob) {
            sprite1.orientation = sprite2.orientation.copy();

            if (align) {
                if (sprite1.orientation.equals(Types.DLEFT) || sprite1.orientation.equals(Types.DRIGHT)) {
                    // Need to align on the Y coordinate.
                    sprite1.rect = new Rectangle(sprite1.rect.x, sprite2.rect.y, sprite1.rect.width,
                            sprite1.rect.height);

                } else {
                    // Need to align on the X coordinate.
                    sprite1.rect = new Rectangle(sprite2.rect.x, sprite1.rect.y, sprite1.rect.width,
                            sprite1.rect.height);
                }
            }

        }
    }
}
