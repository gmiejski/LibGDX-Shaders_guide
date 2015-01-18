package agh.edu.pl.gmiejski.x001_triangles;

import agh.edu.pl.gmiejski.utils.ShaderLoader;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * In this example we build 2 triangles and add special effects to those including gradient.
 * <p>
 * If you haven't already - please take a look at DottedTriangle first.
 */
public class SimpleTriangles extends ApplicationAdapter {

    private Mesh mesh;

    private ShaderProgram shaderProgram;
    private OrthographicCamera cam;

    @Override
    public void create() {

        shaderProgram = ShaderLoader.createShader("core\\assets\\001_triangles\\simpleTriangles\\vertex.glsl", "core\\assets\\001_triangles\\simpleTriangles\\fragment.glsl");

        // now we build our mesh specifying that each vertex will hold 6 parameters, 2 for coordinates X and Y, and 4 specifying its color
        mesh = new Mesh(true, 60, 6, new VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE),
                new VertexAttribute(VertexAttributes.Usage.Color, 4, ShaderProgram.COLOR_ATTRIBUTE));

        // We store some libgdx colors as variables for future reference
        // Don't worry, if one of the triangles is white-black instead of blue. We're overriding its color in shader
        Color blue = Color.BLUE;
        Color red = Color.RED;

        // we set vertices for the mesh. Each row describes single vertex, so we have total of 6 vertexes
        // first 2 values specify X and Y coordinates, and others specify RGBA color value in that order
        mesh.setVertices(new float[]
                {0.0f, 0.0f, blue.r, blue.g, blue.b, blue.a,
                        500f, 0.0f, blue.r, blue.g, blue.b, blue.a,
                        0.0f, 500f, blue.r, blue.g, blue.b, blue.a,
                        250f, 250f, red.r, red.g, red.b, red.a,
                        400f, 400f, red.r, red.g, red.b, red.a,
                        100f, 400f, red.r, red.g, red.b, red.a});

        // we can set indexes by which vertexes from mesh should be taken during rendering. You can play with this a little bit
        mesh.setIndices(new short[]{0, 1, 2, 3, 4, 5});

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render() {
        // here we're during exactly the same thing as in the dotted triangles example
        // only difference is - we render 2 triangles at once, by specifying max vertexes taken as 6.

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shaderProgram.begin();
        shaderProgram.setUniformMatrix("u_projTrans", cam.combined);
        mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, 6);
        shaderProgram.end();
    }

    // TODO
    // try experimenting with colors in this code
    // try to change color in shaders itself to, for example - draw a blue triangle with gradient instead of white one.

}
