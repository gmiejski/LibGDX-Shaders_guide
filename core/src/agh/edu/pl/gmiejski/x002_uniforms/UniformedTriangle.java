package agh.edu.pl.gmiejski.x002_uniforms;

import agh.edu.pl.gmiejski.utils.ShaderLoader;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

/**
 * Here we will draw 2 triangles moving on the screen using uniforms
 * Uniforms are simply parameters send to shaders programs as arguments.
 * <p>
 */
public class UniformedTriangle extends ApplicationAdapter {

    private static final float MIN_SCALE = 0.05f;
    private static final float MAX_SCALE = 1f;

    // We create 2 programs, one that will scale triangle from big to small, and the other with will translate triangles - move them around
    private ShaderProgram scalingShaderProgram;
    private ShaderProgram translatedShaderProgram;

    // We will also use 2 meshes to differentiate both triangles
    private Mesh scalingMesh;
    private Mesh translatedMesh;

    private OrthographicCamera cam;
    private float u_scale;
    private float u_move;
    private float resizing = 0.01f;

    @Override
    public void create() {
        // we load 2 shaders pairs into corresponding shaders programs
        scalingShaderProgram = ShaderLoader.createShader("core\\assets\\002_uniforms\\uniformedTriangle\\scalingVertex.glsl", "core\\assets\\002_uniforms\\uniformedTriangle\\scalingFragment.glsl");
        translatedShaderProgram = ShaderLoader.createShader("core\\assets\\002_uniforms\\uniformedTriangle\\translatedVertex.glsl", "core\\assets\\002_uniforms\\uniformedTriangle\\translatedFragment.glsl");

        // we set the scale argument for shader to 0 at start
        u_scale = 0.0f;

        // we create 2 meshes, each containing 6 values describing 3 vertexes each
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

        // on each render we change the scale argument send to shader program
        changeScale();

        // we create a new matrix which will contain all information needed by shader to move triangle on screen on each render
        // understanding matrixes operations is crucial for working with shaders, that's why you should read a great explanation of those topics
        // best one I've found is this one: http://blog.db-in.com/cameras-on-opengl-es-2-x/
        Matrix4 moveMatrix = calculateNewMoveMatrix();

        // we draw scalling triangle
        scalingShaderProgram.begin();
        // we pass uniform matrix to the shader responsible for projection
        scalingShaderProgram.setUniformMatrix("u_projTrans", cam.combined);
        // we pass float uniform by name "u_scale" with given value
        // it can be accessed in shader by name "u_scale"
        scalingShaderProgram.setUniformf("u_scale", u_scale);
        scalingMesh.render(scalingShaderProgram, GL20.GL_TRIANGLES, 0, 3);
        scalingShaderProgram.end();

        translatedShaderProgram.begin();
        translatedShaderProgram.setUniformMatrix("u_projTrans", cam.combined);
        // only difference is that we pass additional matrix to vertex shader to move the triangle
        // important thing is, that original triangle position set in mesh always stays the same
        // so if we want to move the triangle a bit more than in the previous render operation
        // we need to use previous translation matrix and change it by this difference we want to move the triangle on the next render phase
        // So calling it simply - triangleNewPosition = positionFromMesh + translationMatrixModification, where positionFromMesh is static
        translatedShaderProgram.setUniformMatrix("u_moveMatrix", moveMatrix);
        translatedMesh.render(translatedShaderProgram, GL20.GL_TRIANGLES, 0, 3);
        translatedShaderProgram.end();
    }

    private Matrix4 calculateNewMoveMatrix() {
        u_move += resizing;
        // this is how translation matrix looks like
        // here we're using cos and sin functions to move the triangle forward and backward in a not straight line
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

    // TODO
    // - try to change translation matrix, to make it move in a line from left to right, then from up to down and again from left to right and so on
    // - try adding translation matrix to scaling triangle. You will need to properly modify vertex shader for this purpose

}
