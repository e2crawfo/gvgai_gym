package core.vgdl;

import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;

import core.content.Content;
import core.content.GameContent;
import core.content.InteractionContent;
import core.content.ParameterContent;
import core.content.SpriteContent;
import core.content.TerminationContent;
import core.game.BasicGame;
import core.game.Game;
import core.game.GameSpace;
import core.logging.Logger;
import core.logging.Message;
import core.termination.MultiSpriteCounter;
import core.termination.MultiSpriteCounterSubTypes;
import core.termination.SpriteCounter;
import core.termination.StopCounter;
import core.termination.Termination;
import core.termination.Timeout;
import ontology.Types;
import ontology.avatar.MovingAvatar;
import ontology.avatar.oriented.AimedAvatar;
import ontology.avatar.oriented.BirdAvatar;
import ontology.avatar.oriented.CarAvatar;
import ontology.avatar.oriented.LanderAvatar;
import ontology.avatar.oriented.MissileAvatar;
import ontology.avatar.oriented.OngoingAvatar;
import ontology.avatar.oriented.OngoingShootAvatar;
import ontology.avatar.oriented.OngoingTurningAvatar;
import ontology.avatar.oriented.OrientedAvatar;
import ontology.avatar.oriented.PlatformerAvatar;
import ontology.avatar.oriented.ShootAvatar;
import ontology.avatar.oriented.ShootOnlyAvatar;
import ontology.avatar.oriented.SpaceshipAvatar;
import ontology.avatar.oriented.WizardAvatar;
import ontology.effects.Effect;
import ontology.effects.TimeEffect;
import ontology.effects.binary.AddTimer;
import ontology.effects.binary.Align;
import ontology.effects.binary.AttractGaze;
import ontology.effects.binary.BounceDirection;
import ontology.effects.binary.BounceForward;
import ontology.effects.binary.ChangeResource;
import ontology.effects.binary.CollectResource;
import ontology.effects.binary.CollectResourceIfHeld;
import ontology.effects.binary.DecreaseSpeedToAll;
import ontology.effects.binary.IncreaseSpeedToAll;
import ontology.effects.binary.KillBoth;
import ontology.effects.binary.KillIfFromAbove;
import ontology.effects.binary.KillIfFrontal;
import ontology.effects.binary.KillIfNotFrontal;
import ontology.effects.binary.KillIfOtherHasMore;
import ontology.effects.binary.PullWithIt;
import ontology.effects.binary.SetSpeedForAll;
import ontology.effects.binary.TeleportToExit;
import ontology.effects.binary.TransformIfCount;
import ontology.effects.binary.TransformToAll;
import ontology.effects.binary.TransformToSingleton;
import ontology.effects.binary.WallBounce;
import ontology.effects.binary.WallReverse;
import ontology.effects.binary.WallStop;
import ontology.effects.unary.AddHealthPoints;
import ontology.effects.unary.AddHealthPointsToMax;
import ontology.effects.unary.CloneSprite;
import ontology.effects.unary.FlipDirection;
import ontology.effects.unary.HalfSpeed;
import ontology.effects.unary.KillAll;
import ontology.effects.unary.KillIfAlive;
import ontology.effects.unary.KillIfFast;
import ontology.effects.unary.KillIfHasLess;
import ontology.effects.unary.KillIfHasMore;
import ontology.effects.unary.KillIfNotUpright;
import ontology.effects.unary.KillIfSlow;
import ontology.effects.unary.KillSprite;
import ontology.effects.unary.RemoveScore;
import ontology.effects.unary.ReverseDirection;
import ontology.effects.unary.ShieldFrom;
import ontology.effects.unary.Spawn;
import ontology.effects.unary.SpawnAbove;
import ontology.effects.unary.SpawnBehind;
import ontology.effects.unary.SpawnBelow;
import ontology.effects.unary.SpawnIfCounterSubTypes;
import ontology.effects.unary.SpawnIfHasLess;
import ontology.effects.unary.SpawnIfHasMore;
import ontology.effects.unary.SpawnLeft;
import ontology.effects.unary.SpawnRight;
import ontology.effects.unary.StepBack;
import ontology.effects.unary.SubtractHealthPoints;
import ontology.effects.unary.TransformTo;
import ontology.effects.unary.TransformToRandomChild;
import ontology.effects.unary.TurnAround;
import ontology.effects.unary.UndoAll;
import ontology.effects.unary.UpdateSpawnType;
import ontology.effects.unary.WaterPhysics;
import ontology.effects.unary.WrapAround;
import ontology.sprites.Conveyor;
import ontology.sprites.Door;
import ontology.sprites.Flicker;
import ontology.sprites.Immovable;
import ontology.sprites.OrientedFlicker;
import ontology.sprites.Passive;
import ontology.sprites.Resource;
import ontology.sprites.Spreader;
import ontology.sprites.missile.Missile;
import ontology.sprites.missile.RandomMissile;
import ontology.sprites.npc.GoalDirected;
import ontology.sprites.npc.RandomInertial;
import ontology.sprites.npc.RandomNPC;
import ontology.sprites.npc.Walker;
import ontology.sprites.npc.WalkerJumper;
import ontology.sprites.producer.Bomber;
import ontology.sprites.producer.Portal;
import ontology.sprites.producer.SpawnPoint;
import ontology.sprites.producer.SpawnPointMultiSprite;
import ontology.sprites.producer.SpriteProducer;
import tools.Vector2d;

/**
 * Created with IntelliJ IDEA. User: Diego Date: 22/10/13 Time: 15:33 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class VGDLFactory {
    /**
     * Available sprites for VGDL.
     */
    private String[] spriteStrings = new String[] { "Conveyor", "Flicker", "Immovable", "OrientedFlicker", "Passive",
            "Resource", "Spreader", "Missile", "RandomMissile", "Walker", "WalkerJumper", "RandomInertial", "RandomNPC",
            "GoalDirected", "Bomber", "Portal", "SpawnPoint", "SpriteProducer", "Door",
            "MovingAvatar", "MissileAvatar", "OrientedAvatar", "ShootAvatar",
            "OngoingAvatar", "OngoingTurningAvatar", "OngoingShootAvatar", "AimedAvatar",
            "PlatformerAvatar", "BirdAvatar", "SpaceshipAvatar", "CarAvatar", "WizardAvatar", "LanderAvatar",
            "ShootOnlyAvatar", "SpawnPointMultiSprite" };

    /**
     * Available Sprite classes for VGDL.
     */
    private Class[] spriteClasses = new Class[] { Conveyor.class, Flicker.class, Immovable.class, OrientedFlicker.class,
            Passive.class, Resource.class, Spreader.class, Missile.class, RandomMissile.class, Walker.class,
            WalkerJumper.class, RandomInertial.class, RandomNPC.class, GoalDirected.class, Bomber.class, Portal.class,
            SpawnPoint.class, SpriteProducer.class, Door.class,
            MovingAvatar.class, MissileAvatar.class, OrientedAvatar.class, ShootAvatar.class,
            OngoingAvatar.class, OngoingTurningAvatar.class, OngoingShootAvatar.class,
            AimedAvatar.class, PlatformerAvatar.class, BirdAvatar.class, SpaceshipAvatar.class, CarAvatar.class,
            WizardAvatar.class, LanderAvatar.class, ShootOnlyAvatar.class, SpawnPointMultiSprite.class, };

    /**
     * Available effects for VGDL.
     */
    private String[] effectStrings = new String[] { "stepBack", "turnAround", "killSprite", "killBoth", "killAll",
            "transformTo", "transformToSingleton", "transformIfCount", "wrapAround", "changeResource", "killIfHasLess",
            "killIfHasMore", "cloneSprite", "flipDirection", "reverseDirection", "shieldFrom", "undoAll", "spawn",
            "spawnIfHasMore", "spawnIfHasLess", "pullWithIt", "wallStop", "collectResource", "collectResourceIfHeld",
            "killIfOtherHasMore", "killIfFromAbove", "teleportToExit", "bounceForward", "attractGaze", "align",
            "subtractHealthPoints", "addHealthPoints", "transformToAll", "addTimer", "killIfFrontal",
            "killIfNotFrontal", "spawnBehind", "updateSpawnType", "removeScore", "increaseSpeedToAll",
            "decreaseSpeedToAll", "setSpeedForAll", "transformToRandomChild", "addHealthPointsToMax",
            "spawnIfCounterSubTypes", "bounceDirection", "wallBounce", "killIfSlow", "killIfAlive", "waterPhysics",
            "halfSpeed", "killIfNotUpright", "killIfFast", "wallReverse", "spawnAbove", "spawnLeft", "spawnRight",
            "spawnBelow" };

    /**
     * Available effect classes for VGDL.
     */
    private Class[] effectClasses = new Class[] { StepBack.class, TurnAround.class, KillSprite.class, KillBoth.class,
            KillAll.class, TransformTo.class, TransformToSingleton.class, TransformIfCount.class, WrapAround.class,
            ChangeResource.class, KillIfHasLess.class, KillIfHasMore.class, CloneSprite.class, FlipDirection.class,
            ReverseDirection.class, ShieldFrom.class, UndoAll.class, Spawn.class, SpawnIfHasMore.class,
            SpawnIfHasLess.class, PullWithIt.class, WallStop.class, CollectResource.class, CollectResourceIfHeld.class,
            KillIfOtherHasMore.class, KillIfFromAbove.class, TeleportToExit.class, BounceForward.class,
            AttractGaze.class, Align.class, SubtractHealthPoints.class, AddHealthPoints.class, TransformToAll.class,
            AddTimer.class, KillIfFrontal.class, KillIfNotFrontal.class, SpawnBehind.class, UpdateSpawnType.class,
            RemoveScore.class, IncreaseSpeedToAll.class, DecreaseSpeedToAll.class, SetSpeedForAll.class,
            TransformToRandomChild.class, AddHealthPointsToMax.class, SpawnIfCounterSubTypes.class,
            BounceDirection.class, WallBounce.class, KillIfSlow.class, KillIfAlive.class, WaterPhysics.class,
            HalfSpeed.class, KillIfNotUpright.class, KillIfFast.class, WallReverse.class, SpawnAbove.class,
            SpawnLeft.class, SpawnRight.class, SpawnBelow.class };

    /**
     * Available terminations for VGDL.
     */
    private String[] terminationStrings = new String[] { "MultiSpriteCounter", "SpriteCounter",
            "MultiSpriteCounterSubTypes", "Timeout", "StopCounter" };

    /**
     * Available termination classes for VGDL.
     */
    private Class[] terminationClasses = new Class[] { MultiSpriteCounter.class, SpriteCounter.class,
            MultiSpriteCounterSubTypes.class, Timeout.class, StopCounter.class };

    /**
     * Singleton reference to game/sprite factory
     */
    private static VGDLFactory factory;

    /**
     * Cache for registered games.
     */
    public static HashMap<String, Class> registeredGames;

    /**
     * Cache for registered sprites.
     */
    public static HashMap<String, Class> registeredSprites;

    /**
     * Cache for registered effects.
     */
    public static HashMap<String, Class> registeredEffects;

    /**
     * Cache for registered effects.
     */
    public static HashMap<String, Class> registeredTerminations;

    /**
     * Default private constructor of this singleton.
     */
    private VGDLFactory() {
    }

    /**
     * Initializes the maps for caching classes.
     */
    public void init() {
        registeredGames = new HashMap<String, Class>();
        registeredGames.put("BasicGame", BasicGame.class);
        registeredGames.put("GameSpace", GameSpace.class);

        registeredSprites = new HashMap<String, Class>();
        for (int i = 0; i < spriteStrings.length; ++i) {
            registeredSprites.put(spriteStrings[i], spriteClasses[i]);
        }

        registeredEffects = new HashMap<String, Class>();
        for (int i = 0; i < effectStrings.length; ++i) {
            registeredEffects.put(effectStrings[i], effectClasses[i]);
        }

        registeredTerminations = new HashMap<String, Class>();
        for (int i = 0; i < terminationStrings.length; ++i) {
            registeredTerminations.put(terminationStrings[i], terminationClasses[i]);
        }
    }

    /**
     * Returns the unique instance of this class.
     *
     * @return the factory that creates the game and the sprite objects.
     */
    public static VGDLFactory GetInstance() {
        if (factory == null) {
            factory = new VGDLFactory();
        }
        return factory;
    }

    /**
     * Creates a game, receiving a GameContent object
     *
     * @param content potential parameters for the class.
     * @return The game just created.
     */
    @SuppressWarnings("unchecked")
    public Game createGame(GameContent content) {
        try {
            Class gameClass = registeredGames.get(content.referenceClass);
            Constructor gameConstructor = gameClass.getConstructor(new Class[] { GameContent.class });
            return (Game) gameConstructor.newInstance(new Object[] { content });

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.out.println("Error creating game of class " + content.referenceClass);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error creating game of class " + content.referenceClass);
        }

        return null;
    }

    /**
     * Creates a new sprite with a given dimension in a certain position. Parameters
     * are passed as SpriteContent.
     *
     * @param game     Game that is creating the sprite.
     * @param content  parameters for the sprite, including its class.
     * @param position position of the object.
     * @param dim      dimensions of the sprite on the world.
     * @return the new sprite, created and initialized, ready for play!
     */
    @SuppressWarnings("unchecked")
    public VGDLSprite createSprite(Game game, SpriteContent content, Vector2d position, Dimension dim) {

        decorateContent(game, content);

        try {
            Class spriteClass = registeredSprites.get(content.referenceClass);
            Constructor spriteConstructor = spriteClass
                    .getConstructor(new Class[] { Vector2d.class, Dimension.class, SpriteContent.class });
            return (VGDLSprite) spriteConstructor.newInstance(new Object[] { position, dim, content });

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.out.println("Error creating sprite " + content.identifier + " of class " + content.referenceClass);
        } catch (NullPointerException e) {
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error creating sprite " + content.identifier + " of class " + content.referenceClass);
        }

        return null;
    }

    private void decorateContent(Game game, Content content) {
        try {
            HashMap<String, ParameterContent> paramsGameSpaceContent = game.getParameters();
            if (paramsGameSpaceContent != null) {
                content.decorate(paramsGameSpaceContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Parametrizing Content " + content.identifier);
        }
    }

    /**
     * Creates a new effect, with parameters passed as InteractionContent.
     *
     * @param content parameters for the effect, including its class.
     * @return the new effect, created and initialized, ready to be triggered!
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Effect createEffect(Game game, InteractionContent content) throws Exception {
        if (game != null) {
            decorateContent(game, content);
        }

        try {
            Class effectClass = registeredEffects.get(content.function);
            Constructor effectConstructor = effectClass.getConstructor(new Class[] { InteractionContent.class });
            Effect ef = (Effect) effectConstructor.newInstance(new Object[] { content });

            if (content.object1.equalsIgnoreCase("TIME") || content.object2[0].equalsIgnoreCase("TIME")) {
                return new TimeEffect(content, ef);
            }

            return ef;

        } catch (NoSuchMethodException e) {
            String message = "Error creating effect " + content.function + " between " + content.object1 + " and ";
            for (String obj : content.object2) {
                message += obj + " ";
            }
            message += "\n** Line: " + content.lineNumber + " ** " + content.line;
            throw new Exception(message);
        } catch (Exception e) {
            String message = "Error creating effect " + content.function + " between " + content.object1 + " and ";
            for (String obj : content.object2) {
                message += obj + " ";
            }
            message += "\n** Line: " + content.lineNumber + " ** " + content.line;
            throw new Exception(message);
        }
    }

    /**
     * Creates a new termination, with parameters passed as TerminationContent.
     *
     * @param content parameters for the termination condition, including its class.
     * @return the new termination, created and initialized, ready to be checked!
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Termination createTermination(Game game, TerminationContent content) throws Exception {
        decorateContent(game, content);

        try {
            Class terminationClass = registeredTerminations.get(content.identifier);
            Constructor terminationConstructor = terminationClass
                    .getConstructor(new Class[] { TerminationContent.class });
            Termination ter = (Termination) terminationConstructor.newInstance(new Object[] { content });
            return ter;

        } catch (NoSuchMethodException e) {
            throw new Exception(
                    "Line: " + content.lineNumber + " Error creating termination condition " + content.identifier);
        } catch (Exception e) {
            throw new Exception(
                    "Line: " + content.lineNumber + " Error creating termination condition " + content.identifier);
        }
    }

    /**
     * Parses the parameters from content, assigns them to variables in obj.
     *
     * @param content contains the parameters to read.
     * @param obj     object with the variables to assign.
     * @throws Exception
     */
    public void parseParameters(Content content, Object obj) {
        // Get all fields from the class and store it as key->field
        Field[] fields = obj.getClass().getFields();
        HashMap<String, Field> fieldMap = new HashMap<String, Field>();
        for (Field field : fields) {
            String strField = field.toString();
            int lastDot = strField.lastIndexOf(".");
            String fieldName = strField.substring(lastDot + 1).trim();

            fieldMap.put(fieldName, field);
        }
        Object objVal = null;
        Field cfield = null;
        // Check all parameters from content
        for (String parameter : content.parameters.keySet()) {
            String value = content.parameters.get(parameter);
            if (fieldMap.containsKey(parameter)) {

                try {
                    cfield = Types.processField(value);
                    objVal = cfield.get(null);
                } catch (Exception e) {
                    try {
                        if (!parameter.equalsIgnoreCase("scoreChange")
                                && !parameter.equalsIgnoreCase("scoreChangeIfKilled")) {
                            objVal = Integer.parseInt(value);
                        } else {
                            objVal = value;
                        }
                    } catch (NumberFormatException e1) {
                        try {
                            objVal = Double.parseDouble(value);
                        } catch (NumberFormatException e2) {
                            try {
                                if ((value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))
                                        && !parameter.equalsIgnoreCase("win") && !parameter.equalsIgnoreCase("hidden")
                                        && !parameter.equalsIgnoreCase("invisible")) {
                                    objVal = Boolean.parseBoolean(value);
                                } else {
                                    objVal = value;
                                }
                            } catch (NumberFormatException e3) {
                                objVal = value;
                            }
                        }
                    }
                }
                try {
                    fieldMap.get(parameter).set(obj, objVal);
                } catch (IllegalAccessException e) {
                    // TODO: Do it later

                } catch (Exception e) {
                    // TODO: Do it later
                }
            } else {
                // Ignore unknown fields in dependent Effects (TimeEffect).
                boolean warn = true;
                boolean isInteraction = content instanceof InteractionContent;
                if (isInteraction) {
                    boolean isTimeEffect = ((InteractionContent) content).object2[0].equalsIgnoreCase("TIME")
                            || ((InteractionContent) content).object1.equalsIgnoreCase("TIME")
                            || ((InteractionContent) content).line.contains("addTimer");
                    if (isTimeEffect) {
                        warn = false;
                    }
                }

                if (warn) {
                    Logger.getInstance().addMessage(new Message(Message.ERROR,
                            "Unknown field (" + parameter + "=" + value + ") from " + content.toString()));
                }
            }
        }

    }

    /**
     * Returns the value of an int field in the object specified
     *
     * @param obj       object that holds the field.
     * @param fieldName name of the field to retrieve.
     * @return the value, or -1 if the parameter does not exist or it is not an int.
     */
    public int requestFieldValueInt(Object obj, String fieldName) {
        // Get all fields from the class and store it as key->field
        Field[] fields = obj.getClass().getFields();
        for (Field field : fields) {
            String strField = field.getName();
            if (strField.equalsIgnoreCase(fieldName)) {
                try {
                    Object objVal = field.get(obj);
                    return ((Integer) objVal).intValue();
                } catch (Exception e) {
                    System.out.println("ERROR: invalid requested int parameter " + fieldName);
                    return -1;
                }
            }
        }
        return -1;
    }

}
