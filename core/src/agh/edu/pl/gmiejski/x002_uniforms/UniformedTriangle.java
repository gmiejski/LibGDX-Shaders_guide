package agh.edu.pl.gmiejski.x002_uniforms;

import agh.edu.pl.gmiejski.utils.ShaderLoader;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

/**
 * Created by Grzegorz Miejski on 8/19/2014.
 */
public class UniformedTriangle extends ApplicationAdapter {


    private static final float MIN_SCALE = 0.05f;
    private static final float MAX_SCALE = 1f;
    private Mesh scalingMesh;
    private Mesh translatedMesh;

    private ShaderProgram scalingShaderProgram;
    private ShaderProgram translatedShaderProgram;

    private OrthographicCamera cam;
    private float u_scale;
    private float u_move;
    private float resizing = 0.01f;

    @Override
    public void create() {

        scalingShaderProgram = ShaderLoader.createShader("core\\assets\\002_uniforms\\uniformedTriangle\\scalingVertex.glsl", "core\\assets\\002_uniforms\\uniformedTriangle\\scalingFragment.glsl");
        translatedShaderProgram = ShaderLoader.createShader("core\\assets\\002_uniforms\\uniformedTriangle\\translatedVertex.glsl", "core\\assets\\002_uniforms\\uniformedTriangle\\translatedFragment.glsl");
        u_scale = 0.0f;

        scalingMesh = new Mesh(true, 60, 0, new VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));
        scalingMesh.setVertices(new float[]
                {250f, 250f, 400f, 400f, 100f, 400f});

        translatedMesh = new Mesh(true, 60, 0, new VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));
        translatedMesh.setVertices(new float[]
                {100f, 100f, 250f, 100f, 100f, 250f});

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        changeScale();
        Matrix4 moveMatrix = calculateNewMoveMatrix();

        scalingShaderProgram.begin();
        scalingShaderProgram.setUniformMatrix("u_projTrans", cam.combined);
        scalingShaderProgram.setUniformf("u_scale", u_scale);
        scalingMesh.render(scalingShaderProgram, GL20.GL_TRIANGLES, 0, 3);
        scalingShaderProgram.end();

        translatedShaderProgram.begin();
        translatedShaderProgram.setUniformMatrix("u_projTrans", cam.combined);
        translatedShaderProgram.setUniformMatrix("u_moveMatrix", moveMatrix);
        translatedMesh.render(translatedShaderProgram, GL20.GL_TRIANGLES, 0, 3);
        translatedShaderProgram.end();


    }

    private Matrix4 calculateNewMoveMatrix() {
        u_move += resizing;
        return new Matrix4(new float[]{
                1f, 0.0f, 0.0f, (float) Math.sin(u_move),
                0f, 1f, 0f, 1f - (float) Math.cos(u_move),
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f});
    }

    private void changeScale() {
        u_scale += resizing;
        if (u_scale > MAX_SCALE || (u_scale < MIN_SCALE && resizing < 0)) {
            resizing = -resizing;
        }
    }
}
