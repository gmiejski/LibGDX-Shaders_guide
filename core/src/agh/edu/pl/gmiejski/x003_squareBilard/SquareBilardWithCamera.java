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

import static java.util.stream.Collectors.toList;

/**
 * Created by Grzegorz Miejski on 8/21/2014.
 */
public class SquareBilardWithCamera extends ApplicationAdapter {

    public static float WIDTH = 500f;
    public static float HEIGHT = 500f;

    private Mesh mesh;
    private ShaderProgram shaderProgram;

    private OrthographicCamera cam;
    private float xPos, yPos;
    private Vector2 speed = new Vector2(WIDTH / 100.0f, HEIGHT / 100.0f);

    Vector2 leftBot;
    Vector2 rightTop;

    @Override
    public void create() {
        shaderProgram = ShaderLoader.createShader("core\\assets\\003_squareBilard\\withCamera\\vertex.glsl", "core\\assets\\003_squareBilard\\withCamera\\fragment.glsl");

        mesh = new Mesh(true, 60, 0, new VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));

        Vector2 v1 = new Vector2(100f, 250f);
        Vector2 v2 = new Vector2(250f, 300f);
        Vector2 v3 = new Vector2(200f, 200f);

        saveEdgeVectors(v1, v2, v3);

        mesh.setVertices(new float[]{v2.x, v2.y, v1.x, v1.y, v3.x, v3.y});

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Matrix4 translationMatrix = calculateNewTranslationMatrix();

        shaderProgram.begin();
        shaderProgram.setUniformMatrix("u_projTrans", cam.combined);
        shaderProgram.setUniformMatrix("u_moveMatrix", translationMatrix);
        mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, 3);
        shaderProgram.end();
    }

    /**
     * when camera projection takes place our screen coordinates can be considered as 0->500 on both X and Y
     */
    private Matrix4 calculateNewTranslationMatrix() {
        xPos += speed.x;
        yPos += speed.y;
        if (xPos < (-0 - leftBot.x) || xPos > (WIDTH - rightTop.x)) {
            speed.x = -speed.x;
        }
        if (yPos < (-0 - leftBot.y) || yPos > (HEIGHT - rightTop.y)) {
            speed.y = -speed.y;
        }

        return new Matrix4(new float[]{
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                xPos / WIDTH * 2, yPos / HEIGHT * 2, 0f, 1f});
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
