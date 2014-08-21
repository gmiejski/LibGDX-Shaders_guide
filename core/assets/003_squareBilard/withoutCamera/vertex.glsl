attribute vec2 a_position;
attribute vec3 a_color;

uniform mat4 u_projTrans;
uniform mat4 u_moveMatrix;
uniform vec4 u_colorFlash;


varying vec4 vColor;

void main() {
    //vColor = vec4(1.0f);
    vColor = u_colorFlash;
    gl_Position = u_moveMatrix * vec4(a_position.xy, 0.0, 1.0);
}