#version 330

layout (location = 0) in vec3 position;

uniform float rotation;

out vec3 pos;

void main()
{
	pos = vec3(position.x+rotation, position.y+rotation, position.z);
	gl_Position = vec4(pos, 1.0f);
}