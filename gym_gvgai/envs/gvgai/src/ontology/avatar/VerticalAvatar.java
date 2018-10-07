package ontology.avatar;

import core.content.SpriteContent;
import core.game.Game;
import core.vgdl.VGDLSprite;
import ontology.Types;
import tools.Direction;
import tools.Vector2d;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA. User: Diego Date: 22/10/13 Time: 18:07 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class VerticalAvatar extends MovingAvatar {
	public VerticalAvatar() {
	}

	public VerticalAvatar(Vector2d position, Dimension size, SpriteContent cnt) {
		this.init(position, size);
		loadDefaults();
		this.parseParameters(cnt);
	}

	protected void loadDefaults() {
		super.loadDefaults();
	}
	
    public boolean filterDirs(Direction dir) {
    	return dir == Types.DUP || dir == Types.DDOWN;
    }

	public VGDLSprite copy() {
		VerticalAvatar newSprite = new VerticalAvatar();
		this.copyTo(newSprite);
		return newSprite;
	}

	public void copyTo(VGDLSprite target) {
		VerticalAvatar targetSprite = (VerticalAvatar) target;
		super.copyTo(targetSprite);
	}
}
