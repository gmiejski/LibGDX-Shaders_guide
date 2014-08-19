#ifdef GL_ES
precision mediump float;
#endif

//input from vertex shader
varying vec4 vColor;

void main() {


    if (gl_FragCoord.x + gl_FragCoord.y < 500.0 ) {
        float distanceFactor = distance(gl_FragCoord, vec2(250.0f,250.0f))/(250.0f*sqrt(2));
        vec3 color = vec3(distanceFactor);
        gl_FragColor = vec4(color, 1.0f);
    } else {
        gl_FragColor = vColor;
    }

}