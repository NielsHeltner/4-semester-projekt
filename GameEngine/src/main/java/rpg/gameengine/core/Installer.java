package rpg.gameengine.core;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {
    
    private static Game game;

    @Override
    public void restored() {
        game = new Game();
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RPG";
        cfg.width = 640;
        cfg.height = 480;
        cfg.useGL30 = false;
        cfg.resizable = false;
        
        new LwjglApplication(game, cfg);
    }

}