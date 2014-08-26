package agh.edu.pl.gmiejski.desktop.x005_pyramid;

import agh.edu.pl.gmiejski.x005_pyramid.Pyramid;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by Grzegorz Miejski on 8/25/2014.
 */
public class PyramidDesktop {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//        config.height = 500;
//        config.width = 500;
        config.x = 0;
        config.y = 0;
        new LwjglApplication(new Pyramid(), config);
    }

}
