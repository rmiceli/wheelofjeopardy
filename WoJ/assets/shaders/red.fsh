#version 120

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;

void main(void) {
	gl_FragColor = texture2D(u_sampler2D, v_texCoord0) * v_color;	
}