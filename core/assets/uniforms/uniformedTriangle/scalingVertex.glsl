attribute vec2 a_position;
attribute vec3 a_color;

uniform mat4 u_projTrans;
uniform float u_scale;
uniform mat4 u_moveMatrix;

varying vec4 vColor;

void main() {
    vColor = vec4(1.0f);
    gl_Position = u_projTrans * vec4(a_position.xy, 0.0, 1.0);
    gl_Position.xyz *= u_scale; // scaling each vertex coordinate

}