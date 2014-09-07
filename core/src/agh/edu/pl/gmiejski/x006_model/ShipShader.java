package agh.edu.pl.gmiejski.x006_model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;

import static com.badlogic.gdx.Gdx.input;

/**
 * @author Grzegorz Miejski
 *         on 2014-09-07.
 */
public class ShipShader implements Shader {

    private ShaderProgram program;
    private Camera camera;
    private RenderContext renderContext;
    private Texture texture;

    private float xPos, yPos;
    private Vector2 speed = new Vector2(0.1f, 0.1f);


    @Override
    public void init() {
        String vert = Gdx.files.internal("core\\assets\\006_model\\vertex.glsl").readString();
        String frag = Gdx.files.internal("core\\assets\\006_model\\fragment.glsl").readString();
        program = new ShaderProgram(vert, frag);
        if (!program.isCompiled())
            throw new GdxRuntimeException(program.getLog());

        texture = new Texture("core\\assets\\006_model\\data\\ship.png");
    }

    @Override
    public void render(Renderable renderable) {
        bindTexture();

        program.setUniformMatrix("u_worldTrans", renderable.worldTransform);
        renderable.mesh.render(program,
                renderable.primitiveType,
                renderable.meshPartOffset,
                renderable.meshPartSize);
    }

    @Override
    public void begin(Camera camera, RenderContext context) {
        this.camera = camera;
        this.renderContext = context;
        program.begin();
        program.setUniformMatrix("u_projViewTrans", camera.combined);
        program.setUniformMatrix("u_moveMatrix", newTranslationMatrix());
        context.setDepthTest(GL20.GL_LEQUAL);
        context.setCullFace(GL20.GL_BACK);
    }

    @Override
    public void end() {
        program.end();
    }

    @Override
    public int compareTo(Shader other) {
        return 0;
    }

    @Override
    public boolean canRender(Renderable instance) {
        return false;
    }

    @Override
    public void dispose() {
        program.dispose();
    }

    private void bindTexture() {
        Gdx.graphics.getGL20().glActiveTexture(GL20.GL_TEXTURE0);
        texture.bind(0);
        program.setUniformi("u_texture", 0);
    }

    private Matrix4 newTranslationMatrix() {
        if (input.isKeyPressed(Input.Keys.UP)) {
            yPos += speed.y;
        } else if (input.isKeyPressed(Input.Keys.DOWN)) {
            yPos -= speed.y;
        }
        if (input.isKeyPressed(Input.Keys.RIGHT)) {
            xPos += speed.x;
        } else if (input.isKeyPressed(Input.Keys.LEFT)) {
            xPos -= speed.x;
        }
        if (input.isKeyPressed(Input.Keys.SPACE)) {
            resetPosition();
        }

        return new Matrix4(new float[]{
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                xPos, yPos, 0f, 1f});
    }

    private void resetPosition() {
        xPos = 0f;
        yPos = 0f;
    }
}
