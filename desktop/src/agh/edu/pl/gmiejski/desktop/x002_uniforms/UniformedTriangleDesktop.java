package agh.edu.pl.gmiejski.desktop.x002_uniforms;

import agh.edu.pl.gmiejski.x002_uniforms.UniformedTriangle;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by Grzegorz Miejski on 8/19/2014.
 */
public class UniformedTriangleDesktop {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 500;
        config.width = 500;
        new LwjglApplication(new UniformedTriangle(), config);
    }

}
