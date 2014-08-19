package agh.edu.pl.gmiejski.x001_triangles;

import agh.edu.pl.gmiejski.utils.ShaderLoader;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Created by Grzegorz Miejski on 8/18/2014.
 */
public class DottedTriangle extends ApplicationAdapter {

    private Mesh mesh;

    private ShaderProgram shaderProgram;
    private OrthographicCamera cam;

    @Override
    public void create() {

        shaderProgram = ShaderLoader.createShader("core\\assets\\001_triangles\\dottedTriangle\\vertex.glsl", "core\\assets\\001_triangles\\dottedTriangle\\fragment.glsl");

        mesh = new Mesh(true, 10, 0, new VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));

        mesh.setVertices(new float[]{0.0f, 0.0f, 500f, 0.0f, 500f, 500.0f});

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shaderProgram.begin();
        shaderProgram.setUniformMatrix("u_projTrans", cam.combined);
        mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, 3);
        shaderProgram.end();
    }
}
