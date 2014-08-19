package agh.edu.pl.gmiejski.desktop.triangles;

import agh.edu.pl.gmiejski.triangles.DottedTriangle;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by Grzegorz Miejski on 8/18/2014.
 */
public class DottedTriangleDesktop {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 500;
        config.width = 500;
        new LwjglApplication(new DottedTriangle(), config);
    }
}
