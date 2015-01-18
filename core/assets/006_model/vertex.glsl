// a_position is a default name for position attribute (set programatically as ShaderProgram.POSITION_ATTRIBUTE in java code)
attribute vec3 a_position;
// coordinates of texture 0 "pixel"
attribute vec2 a_texCoord0;

// projection view transformation matrix (set programatically in java code)
uniform mat4 u_projViewTrans;

// world transformation matrix (set programatically in java code)
uniform mat4 u_worldTrans;
// move matrix
uniform mat4 u_moveMatrix;

// texture coordinate passed to vertex shader (set in main())
varying vec2 v_texCoord0;
 
void main() {
    // pass texture coordinate to fragment shader
    v_texCoord0 = a_texCoord0;

    // compute vertex position on screen
    gl_Position = u_projViewTrans * u_worldTrans * u_moveMatrix * vec4(a_position, 1.0);
}