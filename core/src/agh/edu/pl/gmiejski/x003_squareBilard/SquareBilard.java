package agh.edu.pl.gmiejski.x003_squareBilard;

import agh.edu.pl.gmiejski.utils.ShaderLoader;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

/**
 * Created by Grzegorz Miejski on 8/21/2014.
 */
public class SquareBilard extends ApplicationAdapter {

    public static final float COLOR_CHANGE_EACH_FRAME = 0.05f;
    private Mesh mesh;
    private ShaderProgram shaderProgram;

    private float xPos, yPos;

    private Vector2 speed = new Vector2(0.06f, -0.03f);

    Vector2 leftBot;
    Vector2 rightTop;

    Color currentColor = Color.WHITE;
    Random colorDensityRandomizer = new Random();

    @Override
    public void create() {
        xPos = 0.05f;
        yPos = -0.03f;

        shaderProgram = ShaderLoader.createShader("core\\assets\\003_squareBilard\\withoutCamera\\vertex.glsl", "core\\assets\\003_squareBilard\\withoutCamera\\fragment.glsl");

        mesh = new Mesh(true, 60, 0, new VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));

        float edgesize = 0.2f;
        Vector2 v1 = new Vector2(edgesize, -edgesize);
        Vector2 v2 = new Vector2(-edgesize, -edgesize * 2);
        Vector2 v3 = new Vector2(edgesize, edgesize / 2);

        saveEdgeVectors(v1, v2, v3);

        mesh.setVertices(new float[]{v2.x, v2.y, v1.x, v1.y, v3.x, v3.y});
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateColor();

        Matrix4 translationMatrix = calculateNewTranslationMatrix();

        shaderProgram.begin();
        shaderProgram.setUniformf("u_colorFlash", currentColor);
        shaderProgram.setUniformMatrix("u_moveMatrix", translationMatrix);
        mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, 3);
        shaderProgram.end();
    }

    private void updateColor() {
        currentColor.add(COLOR_CHANGE_EACH_FRAME, COLOR_CHANGE_EACH_FRAME, COLOR_CHANGE_EACH_FRAME, 0);
    }

    private Matrix4 calculateNewTranslationMatrix() {
        xPos += speed.x;
        yPos += speed.y;
        if (xPos < (-1 - leftBot.x) || xPos > (1 - rightTop.x)) {
            speed.x = -speed.x;
            currentColor = randomColor();
        }
        if (yPos < (-1 - leftBot.y) || yPos > (1 - rightTop.y)) {
            speed.y = -speed.y;
            currentColor = randomColor();
        }

        return new Matrix4(new float[]{
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                xPos, yPos, 0f, 1f});
    }

    private Color randomColor() {
        return new Color(colorDensityRandomizer.nextFloat(), colorDensityRandomizer.nextFloat(), colorDensityRandomizer.nextFloat(), 1);
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
}
