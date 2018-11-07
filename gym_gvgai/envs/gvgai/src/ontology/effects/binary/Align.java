package ontology.effects.binary;

import java.awt.Rectangle;

import core.content.InteractionContent;
import core.game.Game;
import core.logging.Logger;
import core.logging.Message;
import core.vgdl.VGDLSprite;
import ontology.effects.Effect;

/**
 * Created with IntelliJ IDEA. User: Diego Date: 04/11/13 Time: 15:56 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Align extends Effect {
    // Put sprite1 in same location as sprite2. Give them the same orientation is
    // orient=True.

    public boolean orient = true;

    public Align(InteractionContent cnt) {
        this.parseParameters(cnt);
        setStochastic();
    }

    @Override
    public void execute(VGDLSprite sprite1, VGDLSprite sprite2, Game game) {
        if (sprite1 == null || sprite2 == null) {
            Logger.getInstance().addMessage(
                    new Message(Message.WARNING, "Neither 1st not 2nd sprite can be EOS with Align interaction."));
            return;
        }
        if (orient) {
            sprite1.orientation = sprite2.orientation.copy();
        }
        sprite1.rect = new Rectangle(sprite2.rect.x, sprite2.rect.y, sprite1.rect.width, sprite1.rect.height);
    }
}
