package ontology.sprites.npc;

import core.vgdl.VGDLSprite;
import core.content.SpriteContent;
import core.game.Game;
import ontology.Types;
import tools.Direction;
import tools.Utils;
import tools.Vector2d;

import java.awt.*;

/**
 * Created by Diego on 24/02/14.
 */
public class RandomPathAltChaser extends PathAltChaser {
	// Same as PathAltChaser, but takes random moves with probability `epsilon`

	public double epsilon;

	public RandomPathAltChaser() {
	}

	public RandomPathAltChaser(Vector2d position, Dimension size, SpriteContent cnt) {
		this.init(position, size);
		loadDefaults();
		this.parseParameters(cnt);
	}

	protected void loadDefaults() {
		super.loadDefaults();
		epsilon = 0.0;
	}

	public void postProcess() {
		super.postProcess();
	}

	public void update(Game game) {
		double roll = game.getRandomGenerator().nextDouble();
		if (roll < epsilon) {
			super.updatePassive();
			Direction act = (Direction) Utils.choice(Types.DBASEDIRS, game.getRandomGenerator());
			this.physics.activeMovement(this, act, this.speed);
		} else {
			super.update(game);
		}
	}

	public VGDLSprite copy() {
		RandomPathAltChaser newSprite = new RandomPathAltChaser();
		this.copyTo(newSprite);
		return newSprite;
	}

	public void copyTo(VGDLSprite target) {
		RandomPathAltChaser targetSprite = (RandomPathAltChaser) target;
		targetSprite.epsilon = this.epsilon;
		super.copyTo(targetSprite);
	}

}
