/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.gameengine.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;
import java.util.Map;
import rpg.common.data.GameData;
import rpg.common.entities.Entity;
import rpg.common.events.Event;
import rpg.common.world.World;

/**
 *
 * @author Antonio
 */
public class SoundProcessor {

    private Map<Entity, Sound> walkingSounds;
    private Map<String, Sound> punchingSounds;
    private float timer = 0;

    public SoundProcessor() {
        walkingSounds = new HashMap<>();
        punchingSounds = new HashMap<>();
        punchingSounds.put("No_Hit", Gdx.audio.newSound(Gdx.files.internal("rpg/gameengine/woosh.mp3")));
        punchingSounds.put("Hit", Gdx.audio.newSound(Gdx.files.internal("rpg/gameengine/punch.mp3")));
    }

    public void loadSounds(World world) {
        for (Entity entity : world.getCurrentRoom().getEntities()) {
            if (!entity.getSounds().isEmpty() && !walkingSounds.containsKey(entity)) {
                Sound toLoad = Gdx.audio.newSound(Gdx.files.internal(entity.getSounds().get("GRASS").toString()));
                walkingSounds.put(entity, toLoad);
            }
        }
    }

    public void playSounds(GameData gameData, World world) {
        playWalkingSounds(world, gameData);
        playPunchSounds(world, gameData);
    }
    
    private void playWalkingSounds(World world, GameData gameData){
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
    
    private float calculatePlayRate(Entity entity){
        if(entity.getCurrentMovementSpeed() == 200){
            return 0.20f;
        }else{
            return 0.10f;
        }
    }
    
    private void playPunchSounds(World world, GameData gameData){
        for (Event event : gameData.getEvents()){
            punchingSounds.get("No_Hit").play();
        }
    }
}