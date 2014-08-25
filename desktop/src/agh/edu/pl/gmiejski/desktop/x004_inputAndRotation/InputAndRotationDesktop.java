package agh.edu.pl.gmiejski.desktop.x004_inputAndRotation;

import agh.edu.pl.gmiejski.x004_inputAndRotation.InputAndRotation;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by Grzegorz Miejski on 8/19/2014.
 */
public class InputAndRotationDesktop {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//        config.height = 500;
//        config.width = 500;
        config.x = 0;
        config.y = 0;
        new LwjglApplication(new InputAndRotation(), config);
    }

}
