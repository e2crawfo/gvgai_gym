package ontology.effects.binary;

import java.util.ArrayList;
import java.util.Iterator;

import core.content.InteractionContent;
import core.game.Game;
import core.vgdl.VGDLRegistry;
import core.vgdl.VGDLSprite;
import ontology.effects.Effect;

/**
 * Created with IntelliJ IDEA. User: Diego Date: 03/12/13 Time: 16:17 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class SetSpeedForAll extends Effect {
    public String stype; // sets the speed to value for all sprites of type stype
    public int itype;
    public double value = 0;

    public SetSpeedForAll(InteractionContent cnt) throws Exception {
        is_stochastic = true;
        this.parseParameters(cnt);
        itype = VGDLRegistry.GetInstance().getRegisteredSpriteValue(stype);
        if (itype == -1) {
            throw new Exception("Undefined sprite " + stype);
        }
    }

    @Override
    public void execute(VGDLSprite sprite1, VGDLSprite sprite2, Game game) {

        ArrayList<Integer> subtypes = game.getSubTypes(itype);
        for (Integer i : subtypes) {
            Iterator<VGDLSprite> spriteIt = game.getSpriteGroup(i);
            if (spriteIt != null) {
                while (spriteIt.hasNext()) {
                    try {
                        VGDLSprite s = spriteIt.next();
                        s.speed = value;
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
