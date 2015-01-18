package agh.edu.pl.gmiejski.x005_pyramid;

import agh.edu.pl.gmiejski.utils.ShaderLoader;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.Input.Keys;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Here we will construct our first 3D object - a multicolor pyramid made of triangles only!
 * Topic : cull facing
 * <p>
 * Use arrows to see the pyramid from different angles
 */
public class Pyramid extends ApplicationAdapter {

    public static final float PIRAMID_PEAK = 0.6f;
    private static final double ROT_SPEED = 0.03f;
    public static final Vector3 PYRAMID_TOP = new Vector3(0f, PIRAMID_PEAK, 0f);
    public static final float EDGE_SIZE = 0.3f;

    private Mesh mesh;
    private ShaderProgram shaderProgram;

    private float xRot, yRot;

    @Override
    public void create() {

        shaderProgram = ShaderLoader.createShader("core\\assets\\005_pyramid\\vertex.glsl", "core\\assets\\005_pyramid\\fragment.glsl");
        // we create a space for vertexes each holding 3 coordinations (X, Y and Z) and 3 values for color (RGB)
        mesh = new Mesh(true, 60, 0,
                new VertexAttribute(VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),
                new VertexAttribute(VertexAttributes.Usage.Color, 3, ShaderProgram.COLOR_ATTRIBUTE));

        // then we create vectors which holds vertexes positions
        // for 4 sides of pyramid we need only 2 vertexes coordinates, and the PYRAMID_TOP will serve as the third one
        Vector3 v1_1 = new Vector3(-EDGE_SIZE, 0, EDGE_SIZE);
        Vector3 v1_2 = new Vector3(EDGE_SIZE, 0, EDGE_SIZE);

        Vector3 v2_1 = new Vector3(-EDGE_SIZE, 0, -EDGE_SIZE);
        Vector3 v2_2 = new Vector3(-EDGE_SIZE, 0, EDGE_SIZE);

        Vector3 v3_1 = new Vector3(EDGE_SIZE, 0, -EDGE_SIZE);
        Vector3 v3_2 = new Vector3(-EDGE_SIZE, 0, -EDGE_SIZE);

        Vector3 v4_1 = new Vector3(EDGE_SIZE, 0, EDGE_SIZE);
        Vector3 v4_2 = new Vector3(EDGE_SIZE, 0, -EDGE_SIZE);

        // for triangles creating bottom of pyramid we need to specify all 3 vertexes
        Vector3 v5_1 = new Vector3(-EDGE_SIZE, 0, EDGE_SIZE);
        Vector3 v5_2 = new Vector3(-EDGE_SIZE, 0, -EDGE_SIZE);
        Vector3 v5_3 = new Vector3(EDGE_SIZE, 0, -EDGE_SIZE);

        Vector3 v6_1 = new Vector3(-EDGE_SIZE, 0, EDGE_SIZE);
        Vector3 v6_2 = new Vector3(EDGE_SIZE, 0, -EDGE_SIZE);
        Vector3 v6_3 = new Vector3(EDGE_SIZE, 0, EDGE_SIZE);

        // then we put our vertices to the mesh. Each made fro 6 values.
        // take a notice that when creating 3D object, there is a difference in drawing triangle in order A,B,C vertexes,
        // and building it from B,A,C, even though same vertices are taken
        // Each triangle have a front side and back side - read more about it - face culling:
        // https://www.opengl.org/wiki/Face_Culling
        mesh.setVertices(new float[]{
                v5_1.x, v5_1.y, v5_1.z, 0.3f, 0.3f, 0.3f,
                v5_2.x, v5_2.y, v5_2.z, 0.3f, 0.3f, 0.3f,
                v5_3.x, v5_3.y, v5_3.z, 0.3f, 0.3f, 0.3f,
                v6_1.x, v6_1.y, v6_1.z, 0.3f, 0.3f, 0.3f,
                v6_2.x, v6_2.y, v6_2.z, 0.3f, 0.3f, 0.3f,
                v6_3.x, v6_3.y, v6_3.z, 0.3f, 0.3f, 0.3f,
                v1_1.x, v1_1.y, v1_1.z, 1, 0, 0,
                v1_2.x, v1_2.y, v1_2.z, 1, 0, 0,
                PYRAMID_TOP.x, PYRAMID_TOP.y, PYRAMID_TOP.z, 1, 0, 0,
                v2_1.x, v2_1.y, v2_1.z, 0, 1, 0,
                v2_2.x, v2_2.y, v2_2.z, 0, 1, 0,
                PYRAMID_TOP.x, PYRAMID_TOP.y, PYRAMID_TOP.z, 0, 1, 0,
                v3_1.x, v3_1.y, v3_1.z, 0, 0, 1,
                v3_2.x, v3_2.y, v3_2.z, 0, 0, 1,
                PYRAMID_TOP.x, PYRAMID_TOP.y, PYRAMID_TOP.z, 0, 0, 1,
                v4_1.x, v4_1.y, v4_1.z, 1, 0, 1,
                v4_2.x, v4_2.y, v4_2.z, 1, 0, 1,
                PYRAMID_TOP.x, PYRAMID_TOP.y, PYRAMID_TOP.z, 1, 0, 1});
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // we enable the cull facing
        Gdx.gl.glEnable(GL20.GL_CULL_FACE);
        // we set it to back side
        Gdx.gl.glCullFace(GL20.GL_BACK);
        // we always create triangles counter clock-wise
        Gdx.gl.glFrontFace(GL20.GL_CCW);

        // we update rotation based on user input
        updateRotationAngle();

        Matrix4 rotationMatrix;
        rotationMatrix = newRotationMatrix_XY();

        shaderProgram.begin();
        shaderProgram.setUniformMatrix("u_rotationMatrix", rotationMatrix);
        mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, 18);
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

    // TODO
    // change the order of vertices put into the mesh ( from A,B,C coordinates, to B,A,C for example). See what happens then.
}
