// a_position is a default name for position attribute (set programatically as ShaderProgram.POSITION_ATTRIBUTE in java code)
attribute vec2 a_position;

// camera matrix (set programatically in java code)
uniform mat4 u_projTrans;

// scale coefficient (set programatically in java code)
uniform float u_scale;

// color passed to fragment shader (set in main())
varying vec3 vColor;

void main() {
    // set color as a shade of gray basing on scale factor: vec3(float) := [float, float, float]
    vColor = vec3(u_scale);

    // compute vertex position on screen
    gl_Position = u_projTrans * vec4(a_position, 0.0, 1.0);

    // scale each vertex coordinate
    gl_Position.xy *= u_scale;
}