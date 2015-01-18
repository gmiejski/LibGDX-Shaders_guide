package agh.edu.pl.gmiejski.x006_model;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;

/**
 * In this tutorial we load a 3D model and move it around the screen.
 * <p>
 * We also bind textures to vertex and fragment shader
 */
public class ShipModelApplication extends InputAdapter implements ApplicationListener {
    public PerspectiveCamera cam;
    public CameraInputController camController;
    public ShipShader ship;
    public RenderContext renderContext;
    public Model model;
    public Environment environment;
    public Renderable renderable;

    @Override
    public void create() {
        // first we create an environment. Here we set the light type and color
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        // we set a perspective camera, its position, direction and depth of view
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 0f, 10f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 300f; // object further than 300f distance will not be rendered
        cam.update();

        // we set camera controller for simple camera movement using mouse
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

        // Next we load our model from a obj file. If you want to learn about .obj format search in the internet.
        // Generally .obj is a description of vertexes and together with .mtl file and .png we know where to render each part of the .png file
        ModelLoader modelLoader = new ObjLoader();
        model = modelLoader.loadModel(Gdx.files.internal("core\\assets\\006_model\\data\\ship.obj"));

        // we take the first block part from the model. The structure depends on how the model has bean saved (how .obj and .mtl files are configured).
        NodePart blockPart = model.nodes.get(0).parts.get(0);

        // we create a new renderable - our new renderable in this case
        renderable = new Renderable();
        blockPart.setRenderable(renderable);
        renderable.environment = environment;
        renderable.worldTransform.idt();

        // this time we're not using shader programs in our Main as it's been moved to ShipShader - we use render context instead.
        renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));

        // create new ship shader for rendering
        ship = new ShipShader();
        // init for loading its shaders and texture
        ship.init();
    }

    @Override
    public void render() {
        camController.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        //now instead of shader program we first begin rendering context
        renderContext.begin();

        // then we begin rendering our ship shader program
        ship.begin(cam, renderContext);

        // we now match our shader program with a renerable loaded from the model .obj and render it using our ship shader
        ship.render(renderable);
        ship.end();
        renderContext.end();
    }

    @Override
    public void dispose() {
        // we shouldn't forget about disposing our model and ship shader
        ship.dispose();
        model.dispose();
    }

    @Override
    public void resume() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }
}