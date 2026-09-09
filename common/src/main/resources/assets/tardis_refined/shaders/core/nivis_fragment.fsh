#version 150

in vec4 vertexColor;
in vec2 texCoord0;
in vec4 normal;
in vec4 snowDir;
in vec4 lightMapColor;

out vec4 fragColor;

uniform sampler2D Sampler0;
uniform sampler2D SamplerSnow;
uniform vec4 SnowColor;
uniform mat3 IViewRotMat;

void main() {
    vec4 baseColor = texture(Sampler0, texCoord0) * vertexColor;

    // Discard transparent pixels
    if (baseColor.a < 0.1) {
        discard;
    }

    // Sample snow texture
    vec2 scaledTexCoord = texCoord0 * 16.0;
    vec4 snowTex = texture(SamplerSnow, scaledTexCoord);
    snowTex.a = 1.0;

    // Calculate snow direction factor
    float snowFactor = max(dot(normalize(snowDir.xyz), normalize(normal.xyz)), 0.0);
    float blendFactor = snowTex.a * smoothstep(0.6, 0.95, snowFactor);

    // Blend snow into base color
    vec4 blendedSnow = mix(baseColor, snowTex, blendFactor);
    vec4 finalColor = mix(blendedSnow, SnowColor, snowFactor);

    // Apply lightmap
    finalColor *= lightMapColor;
    fragColor = finalColor;

}
