package agh.edu.pl.gmiejski.x001_triangles;

import agh.edu.pl.gmiejski.utils.ShaderLoader;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * This is the first tutorial, which is simply showing how libgdx can be used with shaders to build a fancy triangle.
 * There are some comments with corresponding shaders files.
 * <p>
 * Methods coming from ApplicationAdapter will not be described verbosely, as libgdx has better descriptions for those.
 */
public class DottedTriangle extends ApplicationAdapter {

    // holds vertexes together with their attributes
    private Mesh mesh;

    // holds vertex and fragment shaders files
    private ShaderProgram shaderProgram;

    private OrthographicCamera cam;

    @Override
    public void create() {
        // first we load both vertex and fragment shaders into our program
        shaderProgram = ShaderLoader.createShader("core\\assets\\001_triangles\\dottedTriangle\\vertex.glsl", "core\\assets\\001_triangles\\dottedTriangle\\fragment.glsl");

        // then we set something called Mesh. It's responsible for holding vertices together with their attributes
        // here we create a space for holding max 3 vertices, as that's how many we need to build a triangle
        // then we specify each vertex attribute - here we build a 2D triangle, so we need to specify 2 coordinates for each triangle ( X and Y coordinates)
        mesh = new Mesh(true, 3, 0, new VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));

        // After declaring what we will store, we now set 6 coordinates into mesh as vertices.
        // Each pair of coordinates will build a different vertex, as we set earlier that each vertex consists of 2 coordinates.
        // So we will receive 3 vertexes: {0,0} , {500,0} , {500,500}
        mesh.setVertices(new float[]{0.0f, 0.0f, 500f, 0.0f, 500f, 500.0f});

        // we create a simple camera to know where we're rendering our triangle
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render() {
        // first we clear the view to drraw new stuff
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // all drawing must happen after .begin()
        shaderProgram.begin();

        // we set uniform matrix to be bound to shaders using name u_projTrans -> check vertex.glsl to see how it's being used
        shaderProgram.setUniformMatrix("u_projTrans", cam.combined);

        // we render our triangle - or rather notify shader program, to draw our triangle
        // we use GL20.GL_TRIANGLES to join 3 vertexes together into a triangle - check other options in GL20 class
        // we then specify offset - from which vertex will we start drawing this triangle
        // and number of vertexes we will use to build draw
        mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, 3);
        shaderProgram.end();
    }

    // TODO
    // you can try experimenting on your own now:
    // add new vertices to mesh and increase its size accordingly during Mesh creation itself
    // change shape we are rendering in mesh.render, and put more vertices by increasing last parameter
}
