// Vignette effect fragment shader
varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform vec4 u_viewport;   // x, y, width, height

//vec2 getUV(vec2 pos, vec2 offset) {
//    return (2.0 * (pos.xy + offset) - u_resolution.xy) / u_resolution.y;
//}

vec2 projectToViewportUV(vec2 pos) {
    vec2 uv;
    uv.x = (pos.x) / (2.0 * u_viewport.z);
    uv.y = (pos.y) / (2.0 * u_viewport.w);
    uv.x -= u_viewport.x / (u_viewport.z);
    uv.y -= u_viewport.y / (u_viewport.w);
    return uv;
}

void main() {
    // fetch sprite texel color
    vec4 colour = texture2D(u_texture, v_texCoords) * v_color;

    // gl_FragColor = vec4(getUV(gl_FragCoord.xy, vec2(0.0)), 0.0, 1.0);
    //gl_FragColor = colour;
    // gl_FragColor = center(gl_FragCoord.xy);

    vec2 uv = projectToViewportUV(gl_FragCoord.xy);
    float dist = length(uv - vec2(0.5, 0.5));
    float vignette = smoothstep(0.0, 0.8, dist);
    colour.rgb *= (1.0 - vignette * 0.9);
    gl_FragColor = colour;
    //gl_FragColor = vec4(uv, 0.0, 1.0);
}
