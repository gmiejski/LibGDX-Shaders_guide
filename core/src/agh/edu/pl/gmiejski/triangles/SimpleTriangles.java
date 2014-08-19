package agh.edu.pl.gmiejski.triangles;

import agh.edu.pl.gmiejski.utils.ShaderLoader;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by Grzegorz Miejski on 8/17/2014.
 */
public class SimpleTriangles extends ApplicationAdapter {

    private Mesh mesh;

    private ShaderProgram shaderProgram;
    private OrthographicCamera cam;

    @Override
    public void create() {

        shaderProgram = ShaderLoader.createShader("core\\assets\\triangles\\simpleTriangles\\vertex.glsl", "core\\assets\\triangles\\simpleTriangles\\fragment.glsl");

        mesh = new Mesh(true, 60, 6, new VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE),
                new VertexAttribute(VertexAttributes.Usage.Color, 4, ShaderProgram.COLOR_ATTRIBUTE));

        Color blue = Color.BLUE;
        Color red = Color.RED;

        mesh.setVertices(new float[]
                {0.0f, 0.0f, blue.r, blue.g, blue.b, blue.a,
                        500f, 0.0f, blue.r, blue.g, blue.b, blue.a,
                        0.0f, 500f, blue.r, blue.g, blue.b, blue.a,
                        250f, 250f, red.r, red.g, red.b, red.a,
                        400f, 400f, red.r, red.g, red.b, red.a,
                        100f, 400f, red.r, red.g, red.b, red.a});
        mesh.setIndices(new short[]{0, 1, 2, 3, 4, 5});

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shaderProgram.begin();
        shaderProgram.setUniformMatrix("u_projTrans", cam.combined);
        mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, 6);
        shaderProgram.end();

    }
}
