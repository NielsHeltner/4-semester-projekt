package rpg.gameengine.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;
import java.util.Map;
import rpg.common.data.GameData;
import rpg.common.entities.Entity;
import rpg.common.events.Event;
import rpg.common.events.EventType;
import rpg.common.world.World;

public class SoundManager {

    private Map<Entity, Sound> walkingSounds;
    private Map<String, Sound> combatSounds;
    private Map<String, Sound> miscSounds;
    private float timer = 0;

    public SoundManager() {
        walkingSounds = new HashMap<>();
        combatSounds = new HashMap<>();
        miscSounds = new HashMap<>();
        loadCombatSounds();
        loadMiscSounds();
        playMusic();
    }
    
    private void loadCombatSounds(){
        combatSounds.put("NOHIT", Gdx.audio.newSound(Gdx.files.internal("rpg/gameengine/woosh.mp3")));
        combatSounds.put("HIT", Gdx.audio.newSound(Gdx.files.internal("rpg/gameengine/punch.mp3")));
    }
    
    private void loadMiscSounds() {
        miscSounds.put("COIN_PICKUP", Gdx.audio.newSound(Gdx.files.internal("rpg/gameengine/coinsound.wav")));
    }
    
    private void playMusic(){
        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("rpg/gameengine/Concentration.mp3"));
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
    }

    public void loadSounds(World world) {
        for (Entity entity : world.getCurrentRoom().getEntities()) {
            if (!entity.getSounds().isEmpty() && !walkingSounds.containsKey(entity)) {
                Sound toLoad = Gdx.audio.newSound(Gdx.files.internal(entity.getSounds().get("GRASS")));
                walkingSounds.put(entity, toLoad);
            }
        }
    }

    public void playSounds(GameData gameData, World world) {
        playWalkingSounds(world, gameData);
        playPunchSounds(world, gameData);
    }

    private void playWalkingSounds(World world, GameData gameData) {
        for (Entity entity : world.getCurrentRoom().getEntities()) {
            if (timer > calculatePlayRate(entity)) {
                if (!entity.getSounds().isEmpty() && entity.getVelocity().isMoving()) {
                    walkingSounds.get(entity).play();
                    System.out.println("LYD SPILLER");
                    timer = 0;
                }
            }
        }
        timer += gameData.getDeltaTime();
    }

    private float calculatePlayRate(Entity entity) {
        return 40 / entity.getCurrentMovementSpeed();
    }

    private void playPunchSounds(World world, GameData gameData) {
        for (Event event : gameData.getEvents()) {
            if(event.getType() == EventType.ATTACK){
                combatSounds.get("NOHIT").play();
                gameData.removeEvent(event);
            }
            if(event.getType() == EventType.PUNCH_HIT){
                combatSounds.get("HIT").play();
                gameData.removeEvent(event);
            }
            if(event.getType() == EventType.COIN_PICKUP){
                miscSounds.get("COIN_PICKUP").play();
                gameData.removeEvent(event);
            }
        }
    }
}
