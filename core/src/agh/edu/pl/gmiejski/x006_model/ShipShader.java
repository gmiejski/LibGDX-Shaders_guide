package agh.edu.pl.gmiejski.x006_model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * @author Grzegorz Miejski
 *         on 2014-09-07.
 */
public class ShipShader implements Shader{

    private ShaderProgram program;
    private Camera camera;
    private RenderContext renderContext;
    private Texture texture;

    @Override
    public void init () {
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
    public void dispose () {
        program.dispose();
    }

    private void bindTexture() {
        Gdx.graphics.getGL20().glActiveTexture(GL20.GL_TEXTURE0);
        texture.bind(0);
        program.setUniformi("u_texture", 0);
    }

}
