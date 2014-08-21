package agh.edu.pl.gmiejski.desktop.x003_squareBilard;

import agh.edu.pl.gmiejski.x003_squareBilard.SquareBilardWithCamera;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by Grzegorz Miejski on 8/21/2014.
 */
public class SquareBilardWithCameraDesktop extends ApplicationAdapter {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int)SquareBilardWithCamera.WIDTH;
        config.height = (int)SquareBilardWithCamera.HEIGHT;
        config.x = 0;
        config.y = 0;
        new LwjglApplication(new SquareBilardWithCamera(), config);
    }
}
