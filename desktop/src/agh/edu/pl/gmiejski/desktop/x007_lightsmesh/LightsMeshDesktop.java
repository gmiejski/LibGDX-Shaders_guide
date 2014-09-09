package agh.edu.pl.gmiejski.desktop.x007_lightsmesh;

import agh.edu.pl.gmiejski.x007_lightsmesh.LightsMesh;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by Grzegorz Miejski on 8/25/2014.
 */
public class LightsMeshDesktop {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//        config.height = 500;
//        config.width = 500;
        config.x = 0;
        config.y = 0;
        new LwjglApplication(new LightsMesh(), config);
    }

}
