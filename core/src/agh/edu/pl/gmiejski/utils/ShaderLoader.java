package agh.edu.pl.gmiejski.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by Grzegorz Miejski on 8/18/2014.
 */
public class ShaderLoader {

    public static ShaderProgram createShader(String vertexShaderPath, String fragmentShaderPath) {
//        ShaderProgram.pedantic = false;
        ShaderProgram shader = new ShaderProgram(Gdx.files.internal(vertexShaderPath),
                Gdx.files.internal(fragmentShaderPath));
        String log = shader.getLog();
        if (!shader.isCompiled()) {
            throw new GdxRuntimeException(log);
        }
        if (log != null && log.length() != 0) {
            System.out.println("Shader Log: " + log);
        }
        return shader;
    }
}
