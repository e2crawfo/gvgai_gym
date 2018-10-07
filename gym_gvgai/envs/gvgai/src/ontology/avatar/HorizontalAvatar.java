package ontology.avatar;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;

import core.vgdl.VGDLRegistry;
import core.vgdl.VGDLSprite;
import core.content.SpriteContent;
import core.game.Game;
import ontology.Types;
import tools.Direction;
import tools.Vector2d;


/**
 * Created with IntelliJ IDEA. User: Diego Date: 22/10/13 Time: 18:07 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class HorizontalAvatar extends MovingAvatar {
	public HorizontalAvatar() {
	}

	public HorizontalAvatar(Vector2d position, Dimension size, SpriteContent cnt) {
		this.init(position, size);
		loadDefaults();
		this.parseParameters(cnt);
	}

	protected void loadDefaults() {
		super.loadDefaults();
	}
	
    public boolean filterDirs(Direction dir) {
    	return dir == Types.DLEFT || dir == Types.DRIGHT;
    }

	public VGDLSprite copy() {
		HorizontalAvatar newSprite = new HorizontalAvatar();
		this.copyTo(newSprite);
		return newSprite;
	}

	public void copyTo(VGDLSprite target) {
		HorizontalAvatar targetSprite = (HorizontalAvatar) target;
		super.copyTo(targetSprite);
	}
}
