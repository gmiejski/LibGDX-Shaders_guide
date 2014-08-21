package agh.edu.pl.gmiejski.x003_squareBilard;

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

import static java.util.stream.Collectors.toList;

/**
 * Created by Grzegorz Miejski on 8/19/2014.
 */
public class SquareBilard extends ApplicationAdapter {


    private Mesh translatedMesh;
    private ShaderProgram translatedShaderProgram;

    private float xPos, yPos;
    private Vector2 speed = new Vector2(0.05f, 0.05f);
    private float edgesize = 0.2f;

    Vector2 leftBot = new Vector2(-edgesize, -edgesize);
    Vector2 rightTop = new Vector2(edgesize, edgesize);


    @Override
    public void create() {
        translatedShaderProgram = ShaderLoader.createShader("core\\assets\\003_squareBilard\\withoutCamera\\vertex.glsl", "core\\assets\\003_squareBilard\\withoutCamera\\fragment.glsl");

        translatedMesh = new Mesh(true, 60, 0, new VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));

        Vector2 v1 = new Vector2(edgesize, -edgesize);
        Vector2 v2 = new Vector2(-edgesize, -edgesize * 2);
        Vector2 v3 = new Vector2(edgesize, edgesize / 2);

        saveEdgeVectors(v1, v2, v3);

        translatedMesh.setVertices(new float[]{v2.x, v2.y, v1.x, v1.y, v3.x, v3.y});
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Matrix4 translationMatrix = calculateNewTranslationMatrix();

        translatedShaderProgram.begin();
        translatedShaderProgram.setUniformMatrix("u_moveMatrix", translationMatrix);
        translatedMesh.render(translatedShaderProgram, GL20.GL_TRIANGLES, 0, 3);
        translatedShaderProgram.end();
    }

    private Matrix4 calculateNewTranslationMatrix() {
        xPos += speed.x;
        yPos += speed.y;
        if (xPos < (-1 - leftBot.x) || xPos > (1 - rightTop.x)) {
            speed.x = -speed.x;
        }
        if (yPos < (-1 - leftBot.y) || yPos > (1 - rightTop.y)) {
            speed.y = -speed.y;
        }

        return new Matrix4(new float[]{
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                xPos, yPos, 0f, 1f});
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
