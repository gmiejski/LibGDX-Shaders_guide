//our attributes
attribute vec2 a_position;
attribute vec3 a_color;

//our camera matrix
uniform mat4 u_projTrans;

//send the color out to the fragment shader
varying vec4 vColor;

void main() {
    vColor = vec4(1.0f);
    gl_Position = u_projTrans * vec4(a_position.xy, 0.0, 1.0);
}