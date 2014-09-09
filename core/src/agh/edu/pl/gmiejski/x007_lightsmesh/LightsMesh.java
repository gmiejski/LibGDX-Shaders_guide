package agh.edu.pl.gmiejski.x007_lightsmesh;

import agh.edu.pl.gmiejski.utils.ShaderLoader;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.Input.Keys;
import static java.lang.Math.cos;
import static java.lang.Math.sin;


public class LightsMesh extends ApplicationAdapter {
    private static final double ROT_SPEED = 0.03f;
    public static final float[] LIGHT_POS = new float[]{0.4f, 0.4f, 0.4f};

    private Mesh mesh;
    private ShaderProgram shaderProgram;

    private float xRot, yRot;

    @Override
    public void create() {

        shaderProgram = ShaderLoader.createShader("core\\assets\\007_lightsmesh\\vertex.glsl", "core\\assets\\007_lightsmesh\\fragment.glsl");
        mesh = new Mesh(true, 60, 0,
                new VertexAttribute(VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),
                new VertexAttribute(VertexAttributes.Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE),
                new VertexAttribute(VertexAttributes.Usage.Color, 3, ShaderProgram.COLOR_ATTRIBUTE));


        mesh.setVertices(new float[]{
                // front
                -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f,
                -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f,
                -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f,
                1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f,
                // right
                1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                1.0f, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                1.0f, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                1.0f, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                1.0f, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                1.0f, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                // back
                1.0f, 1.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, -1.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f,
                -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, -1.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f,
                -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f,
                -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f,
                // left
                -1.0f, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
                -1.0f, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
                -1.0f, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
                -1.0f, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
                -1.0f, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
                -1.0f, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
                // top
                -1.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,
                -1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,
                -1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,
                // bottom
                1.0f, -1.0f, -1.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f, 1.0f,
                1.0f, -1.0f, 1.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f, 1.0f,
                -1.0f, -1.0f, -1.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f, 1.0f,
                1.0f, -1.0f, 1.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f, 1.0f,
                -1.0f, -1.0f, 1.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f, 1.0f,
                -1.0f, -1.0f, -1.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f, 1.0f,
        });

        mesh.scale(0.25f, 0.25f, .25f);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_CULL_FACE);
        Gdx.gl.glCullFace(GL20.GL_BACK);
        Gdx.gl.glFrontFace(GL20.GL_CCW);
        updateRotationAngle();

        Matrix4 rotationMatrix;
        rotationMatrix = newRotationMatrix_XY();

        shaderProgram.begin();
        shaderProgram.setUniformMatrix("u_rotationMatrix", rotationMatrix);
        shaderProgram.setUniform3fv("u_LightPos", LIGHT_POS, 0, 3);
        mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, 36);
        shaderProgram.end();
    }

    private void updateRotationAngle() {
        if (input.isKeyPressed(Keys.UP)) {
            xRot += ROT_SPEED;
        } else if (input.isKeyPressed(Keys.DOWN)) {
            xRot -= ROT_SPEED;
        }
        if (input.isKeyPressed(Keys.RIGHT)) {
            yRot += ROT_SPEED;
        } else if (input.isKeyPressed(Keys.LEFT)) {
            yRot -= ROT_SPEED;
        }
        if (input.isKeyPressed(Keys.SPACE)) {
            resetTrianglePosition();
        }
    }

    private Matrix4 newRotationMatrix_X() {
        return new Matrix4(new float[]{
                1f, 0f, 0f, 0f,
                0f, (float) cos(xRot), -(float) sin(xRot), 0f,
                0f, (float) sin(xRot), (float) cos(xRot), 0f,
                0f, 0f, 0f, 1f});
    }

    private Matrix4 newRotationMatrix_Y() {
        return new Matrix4(new float[]{
                (float) cos(yRot), 0f, (float) sin(yRot), 0f,
                0f, 1f, 0f, 0f,
                (float) -sin(yRot), 0f, (float) cos(yRot), 0f,
                0f, 0f, 0f, 1f});
    }

    private Matrix4 newRotationMatrix_Z() {
        return new Matrix4(new float[]{
                (float) cos(yRot), (float) -sin(yRot), 0f, 0f,
                (float) sin(yRot), (float) cos(yRot), 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 0f, 1f});
    }

    /**
     * example of rotation matrix against 2 axis
     */
    private Matrix4 newRotationMatrix_XY() {
        return newRotationMatrix_X().mul(newRotationMatrix_Y());
    }

    private Matrix4 newRotationMatrix_YX() {
        return newRotationMatrix_X().mulLeft(newRotationMatrix_Y());
    }

    private void resetTrianglePosition() {
        xRot = 0;
        yRot = 0;
    }
}
