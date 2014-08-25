#ifdef GL_ES
precision mediump float;
#endif

varying vec4 vColor;

void main() {
    float distanceFactor =sqrt(gl_FragCoord.x*gl_FragCoord.x +gl_FragCoord.y*gl_FragCoord.y) ;//distance(gl_FragCoord, vec2(250.0f,250.0f))/(250.0f*sqrt(2));
    vec3 color = vec3(distanceFactor);
    gl_FragColor = vec4(color, 1.0f);
}