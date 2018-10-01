package ontology.sprites.producer;

import java.awt.Dimension;
import java.util.ArrayList;

import core.vgdl.VGDLRegistry;
import core.vgdl.VGDLSprite;
import core.content.SpriteContent;
import core.game.Game;
import ontology.Types;
import tools.Direction;
import tools.Utils;
import tools.Vector2d;

/**
 * Created with IntelliJ IDEA. User: Diego Date: 21/10/13 Time: 18:26 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Bomber extends SpawnPoint {
	public String stypeMissile;
	private ArrayList<Integer> itypesMissile;
	public boolean random_movement;
	
	public Bomber() {
	}

	public Bomber(Vector2d position, Dimension size, SpriteContent cnt) {
		this.init(position, size);
		loadDefaults();
		this.parseParameters(cnt);

		int itypesArray[] = VGDLRegistry.GetInstance().explode_better(stypeMissile);
		itypesMissile = new ArrayList<>();
		for (Integer it : itypesArray) {
			itypesMissile.add(it);
		}

		is_stochastic = is_stochastic || random_movement || itypesMissile.size() > 1;
	}

	protected void loadDefaults() {
		super.loadDefaults();
		color = Types.ORANGE;
		is_static = false;
		orientation = Types.DRIGHT.copy();
		is_npc = true;
		is_stochastic = true;
		speed = 1.0;
		random_movement = false;
		stypeMissile = null;
	}
	
	public void update(Game game) {
		if (itypesMissile.size() > 0) {
			int type = game.getRandomGenerator().nextInt(itypesMissile.size());
			itype = itypesMissile.get(type);
		}
			
		if (random_movement) {
			Direction act = (Direction) Utils.choice(Types.DBASEDIRS, game.getRandomGenerator());
			this.physics.activeMovement(this, act, this.speed);
		}

		super.update(game);
	}
	
	/**
	 * Updates missile itype with newitype
	 * 
	 * @param itype    - current type of missile
	 * @param newitype - new type of missile to replace the first
	 */
	public void updateItype(int itype, int newitype) {
		int idx = itypesMissile.indexOf(itype);
		itypesMissile.set(idx, newitype);
	}

	public VGDLSprite copy() {
		Bomber newSprite = new Bomber();
		this.copyTo(newSprite);
		return newSprite;
	}

	public void copyTo(VGDLSprite target) {
		Bomber targetSprite = (Bomber) target;
		
		targetSprite.itypesMissile = new ArrayList<>();
		for (Integer it : this.itypesMissile) {
			targetSprite.itypesMissile.add(it);
		}
		targetSprite.random_movement = this.random_movement;
		
		super.copyTo(targetSprite);
	}
}
