package rpg.collision;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import rpg.common.data.GameData;
import rpg.common.data.GameKeys;
import rpg.common.entities.Entity;
import rpg.common.world.Room;
import rpg.common.world.World;

/**
 *
 * @author Niels
 */
public class CollisionTest {

    private CollisionDetectionSystem collisionDetectionSystem;
    private Entity entity;
    private GameData gameData;
    private World world;

    @Before
    public void setUp() {
        collisionDetectionSystem = new CollisionDetectionSystem();
        
        world = new World();
        world.loadRooms();

        gameData = mock(GameData.class);
        GameKeys gameKeys = new GameKeys();
        when(gameData.getKeys()).thenReturn(gameKeys);
        when(gameData.getDeltaTime()).thenReturn(60f / 3600f);

        entity = new Entity();
        entity.getRoomPosition().set(0, 0);
        entity.getVelocity().set(50, 50);
        entity.getWorldPosition().set(world.getCurrentRoom().getX(), world.getCurrentRoom().getY());
        entity.setSize(50, 50);
        world.getCurrentRoom().addEntity(entity);
    }

    @After
    public void tearDown() {
        collisionDetectionSystem = null;
        entity = null;
        gameData = null;
        world = null;
    }

    @Test
    public void testCollisionDetection() {
        assertFalse(entity.getVelocity().getX() == 0);
        assertFalse(entity.getVelocity().getY() == 0);
        
        collisionDetectionSystem.process(gameData, world);
        
        assertTrue(entity.getVelocity().getX() == 0);
        assertTrue(entity.getVelocity().getY() == 0);
    }

}