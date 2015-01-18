#ifdef GL_ES 
precision mediump float;
#endif

// sampler for texture number 0 (ship texture)
uniform sampler2D u_texture;

// texture coordnate of texture 0 (set in vertex shader)
varying vec2 v_texCoord0;

void main() {
    // bind texture "pixel" to one fragment using texture2D function
    gl_FragColor = texture2D(u_texture, v_texCoord0);
}