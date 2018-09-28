package ontology.avatar;

import java.awt.Dimension;

import core.vgdl.VGDLSprite;
import core.content.SpriteContent;
import core.game.Game;
import ontology.Types;
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

	public void postProcess() {
		// Define actions here first.
		if (actions.size() == 0) {
			actions.add(Types.ACTIONS.ACTION_LEFT);
			actions.add(Types.ACTIONS.ACTION_RIGHT);
		}

		super.postProcess();
	}

	protected void loadDefaults() {
		super.loadDefaults();
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
