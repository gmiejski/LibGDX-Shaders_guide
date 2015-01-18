package agh.edu.pl.gmiejski.x004_inputAndRotation;

import agh.edu.pl.gmiejski.utils.ShaderLoader;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.List;

import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.Input.Keys;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.util.stream.Collectors.toList;

/**
 * Here we are joining 2 matrix operations together - rotation and translation on a single triangle
 * <p>
 * Use arrows to move the triangle and 'space' to reset its position
 */
public class InputAndRotation extends ApplicationAdapter {
    // we set starting rotation speed
    private static final double ROT_SPEED = 0.01f;

    private Mesh mesh;
    private ShaderProgram shaderProgram;

    private float xPos, yPos;
    private Vector2 speed = new Vector2(0.03f, 0.03f);

    Vector2 leftBot;
    Vector2 rightTop;

    // current rotation value
    private double rot = 0f;

    @Override
    public void create() {
        // we create a mesh and shader program as we did many times before
        shaderProgram = ShaderLoader.createShader("core\\assets\\004_inputAndRotation\\vertex.glsl", "core\\assets\\004_inputAndRotation\\fragment.glsl");
        mesh = new Mesh(true, 60, 0, new VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));

        Vector2 v1 = new Vector2(0, 0);
        Vector2 v2 = new Vector2(0.3f, 0);
        Vector2 v3 = new Vector2(0.3f, 0.3f);

        saveEdgeVectors(v1, v2, v3);

        mesh.setVertices(new float[]{v2.x, v2.y, v1.x, v1.y, v3.x, v3.y});
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // we change the rotation value on each render phase
        updateRotationAngle();

        // we create translation matrix based on user input (arrows moves the triangle)
        Matrix4 translationMatrix = newTranslationMatrix();

        // next we build a right rotation matrix.
        // rotation matrixes has to be understand first before we start using them
        // for a good starting point take a look here:
        // http://blog.db-in.com/cameras-on-opengl-es-2-x/

        // we have several types of rotation matrixes we can use. Select any of these to see how it changes triangle's movement!
        Matrix4 rotationMatrix;
//         rotationMatrix = newRotationMatrix_X();
//         rotationMatrix = newRotationMatrix_Y();
//         rotationMatrix = newRotationMatrix_Z();
        rotationMatrix = newRotationMatrix_XY();

        shaderProgram.begin();
        // We pass separately both matrixes, translation and rotation
        // Then in vertex shader those 2 are combined. Mind that the order of matrixes composition is important!
        shaderProgram.setUniformMatrix("u_moveMatrix", translationMatrix);
        shaderProgram.setUniformMatrix("u_rotationMatrix", rotationMatrix);
        mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, 3);
        shaderProgram.end();
    }

    private void updateRotationAngle() {
        rot += ROT_SPEED;
    }

    private Matrix4 newRotationMatrix_X() {
        return new Matrix4(new float[]{
                1f, 0f, 0f, 0f,
                0f, (float) cos(rot), -(float) sin(rot), 0f,
                0f, (float) sin(rot), (float) cos(rot), 0f,
                0f, 0f, 0f, 1f});
    }

    private Matrix4 newRotationMatrix_Y() {
        return new Matrix4(new float[]{
                (float) cos(rot), 0f, (float) sin(rot), 0f,
                0f, 1f, 0f, 0f,
                (float) -sin(rot), 0f, (float) cos(rot), 0f,
                0f, 0f, 0f, 1f});
    }

    private Matrix4 newRotationMatrix_Z() {
        return new Matrix4(new float[]{
                (float) cos(rot), (float) -sin(rot), 0f, 0f,
                (float) sin(rot), (float) cos(rot), 0f, 0f,
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


    private Matrix4 newTranslationMatrix() {

        // first we update translation parameters based on user input
        if (input.isKeyPressed(Keys.UP)) {
            yPos += speed.y;
        } else if (input.isKeyPressed(Keys.DOWN)) {
            yPos -= speed.y;
        }
        if (input.isKeyPressed(Keys.RIGHT)) {
            xPos += speed.x;
        } else if (input.isKeyPressed(Keys.LEFT)) {
            xPos -= speed.x;
        }

        // space resets triangles position
        if (input.isKeyPressed(Keys.SPACE)) {
            resetTrianglePosition();
        }

        // then we check if we didn't go too far
        if (xPos < (-1 - leftBot.x)) {
            xPos = -1 - leftBot.x;
        } else if (xPos > (1 - rightTop.x)) {
            xPos = 1 - rightTop.x;
        }
        if (yPos < (-1 - leftBot.y)) {
            yPos = -1 - leftBot.y;
        } else if (yPos > (1 - rightTop.y)) {
            yPos = 1 - rightTop.y;
        }

        // build final translation matrix to be passed to vertex shader
        return new Matrix4(new float[]{
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                xPos, yPos, 0f, 1f});
    }

    private void resetTrianglePosition() {
        xPos = 0;
        yPos = 0;
    }

    private void saveEdgeVectors(Vector2... vectorsArray) {
        List<Vector2> vectors = Arrays.asList(vectorsArray);

        List<Float> sortedY = vectors.stream().sorted((o1, o2) -> Float.compare(o1.y, o2.y)).map(x -> x.y).collect(toList());
        List<Float> sortedX = vectors.stream().sorted((o1, o2) -> Float.compare(o1.x, o2.x)).map(x -> x.x).collect(toList());

        float bot = sortedY.get(0);
        float top = sortedY.get(sortedY.size() - 1);
        float left = sortedX.get(0);
        float right = sortedX.get(sortedX.size() - 1);

        leftBot = new Vector2(left, bot);
        rightTop = new Vector2(right, top);
    }

    // TODO
    // change rotation matrixes that are being passed to vertex shader
    // try playing with order of matrix multiplication in vertex shader
}
