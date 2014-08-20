package agh.edu.pl.gmiejski.x003_squareBilard;

import agh.edu.pl.gmiejski.utils.ShaderLoader;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static java.util.stream.Collectors.toList;

/**
 * Created by Grzegorz Miejski on 8/19/2014.
 */
public class SquareBilard extends ApplicationAdapter {


    private Mesh translatedMesh;
    private ShaderProgram translatedShaderProgram;

    private OrthographicCamera cam;
    private float xPos, yPos;
    private Vector2 speed = new Vector2(0.05f, 0.05f);
    private float edgesize = 0.2f;

    Vector2 leftBot = new Vector2(-edgesize, -edgesize);
    Vector2 rightTop = new Vector2(edgesize, edgesize);


    @Override
    public void create() {
        translatedShaderProgram = ShaderLoader.createShader("core\\assets\\003_squareBilard\\vertex.glsl", "core\\assets\\003_squareBilard\\fragment.glsl");

        translatedMesh = new Mesh(true, 60, 0, new VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));

        Vector2 v1 = new Vector2(edgesize, -edgesize);
        Vector2 v2 = new Vector2(-edgesize, -edgesize*2);
        Vector2 v3 = new Vector2(edgesize, edgesize/2);

        saveEdgeVectors(v1, v2, v3);

        translatedMesh.setVertices(new float[]
                {v2.x, v2.y, v1.x, v1.y, v3.x, v3.y});
//                {100f, 100f, 250f, 100f, 250f, 250f});
//                {0f, 0f, 150f, 0f, 150f, 150f});
//                        250f, 250f, 100f, 250f, 100f, 100f});

//        translatedMesh.setVertices(new float[]
//                {0f, 0f, 0.5f, 0.5f, -0.5f, 0.5f});


//        cam = new OrthographicCamera();
//        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Matrix4 moveMatrix = calculateNewMoveMatrix();
        translatedShaderProgram.begin();
//        translatedShaderProgram.setUniformMatrix("u_projTrans", cam.combined);
        translatedShaderProgram.setUniformMatrix("u_moveMatrix", moveMatrix);
        translatedMesh.render(translatedShaderProgram, GL20.GL_TRIANGLES, 0, 3);
        translatedShaderProgram.end();
    }

    private Matrix4 calculateNewMoveMatrix() {
        xPos += speed.x;
        yPos += speed.y;
//        float edgesize = 150f / Gdx.graphics.getHeight();
        if (xPos < (-1 - leftBot.x) || xPos > (1 - rightTop.x)) {
            speed.x = -speed.x;
        }
        if (yPos < (-1 - leftBot.y) || yPos > (1 - rightTop.y)) {
            speed.y = -speed.y;
        }
//        if (xPos < -Gdx.graphics.getWidth()/2 || xPos > Gdx.graphics.getWidth()/2) {
//            speed.x = -speed.x;
//        }
//        if (yPos < -Gdx.graphics.getHeight()/2 || yPos > Gdx.graphics.getHeight()/2) {
//            speed.y = -speed.y;
//        }
        Matrix4 m = new Matrix4(new float[]{
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                xPos, yPos, 0f, 1f});

        return m;
    }

    private void saveEdgeVectors(Vector2... vectorsArray) {
        List<Vector2> vectors = Arrays.asList(vectorsArray);

        List<Float> sortedY = vectors.stream().sorted((o1, o2) -> Float.compare(o1.y, o2.y)).map(x -> x.y).collect(toList());
        List<Float> sortedX = vectors.stream().sorted((o1, o2) -> Float.compare(o1.x, o2.x)).map(x -> x.x).collect(toList());

        float bot = sortedY.get(0);
        float top = sortedY.get(sortedY.size()-1);
        float left = sortedX.get(0);
        float right = sortedX.get(sortedX.size()-1);

        leftBot = new Vector2(left, bot);
        rightTop = new Vector2(right, top);
    }
}
