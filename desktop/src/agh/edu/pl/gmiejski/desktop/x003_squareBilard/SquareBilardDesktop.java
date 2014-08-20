package agh.edu.pl.gmiejski.desktop.x003_squareBilard;

import agh.edu.pl.gmiejski.x002_uniforms.UniformedTriangle;
import agh.edu.pl.gmiejski.x003_squareBilard.SquareBilard;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by Grzegorz Miejski on 8/19/2014.
 */
public class SquareBilardDesktop {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 500;
        config.width = 500;
        config.x = 0;
        config.y = 0;
        new LwjglApplication(new SquareBilard(), config);
    }

}
