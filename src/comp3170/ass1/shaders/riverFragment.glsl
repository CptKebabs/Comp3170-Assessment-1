#version 410

uniform vec4 u_waterColour;	// RGBA
uniform vec4 u_rippleColour;// RGBA
//uniform vec2 u_screenSize;  // screen dimensions in pixels

layout(location = 0) out vec4 o_colour;	// RGBA

void main() {
	if(int(gl_FragCoord.y) % 50 > 40){
		o_colour = u_rippleColour;
	}else{
	   o_colour = u_waterColour;
	}
}

