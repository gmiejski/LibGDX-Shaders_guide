package agh.edu.pl.gmiejski.desktop.x001_triangles;

import agh.edu.pl.gmiejski.x001_triangles.SimpleTriangles;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class SimpleTrianglesDesktop {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 500;
        config.width = 500;
        new LwjglApplication(new SimpleTriangles(), config);
    }
}
