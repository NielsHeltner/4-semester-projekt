package rpg.movement;

import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import rpg.common.data.GameData;
import rpg.common.world.World;
import rpg.common.entities.Entity;
import rpg.common.services.IEntityProcessingService;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)
})
public class MovementControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            float deltaTime = gameData.getDeltaTime();
            entity.getVelocity().scalar(deltaTime);
            if (entity.getVelocity().isDiagonal()) {
                entity.getVelocity().normalize(entity.getMovementSpeed());
            }
            if (entity.getVelocity().isMoving()) {
                entity.setDirection(entity.getVelocity().getAngle());
            }
            entity.getRoomPosition().add(entity.getVelocity());
            if(gameData.getWeapon(entity) != null) {
                gameData.getWeapon(entity).getRoomPosition().set(entity.getRoomPosition());
            }
        }
    }

}
