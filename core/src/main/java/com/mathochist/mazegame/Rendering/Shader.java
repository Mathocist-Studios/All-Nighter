package com.mathochist.mazegame.Rendering;

import com.badlogic.gdx.files.FileHandle;

public class Shader {

    private String vertexShaderCode;
    private String fragmentShaderCode;

    public Shader(FileHandle vertexShaderFile, FileHandle fragmentShaderFile) {
        this.vertexShaderCode = vertexShaderFile.readString();
        this.fragmentShaderCode = fragmentShaderFile.readString();
    }

    public String getVertexShaderCode() {
        return vertexShaderCode;
    }

    public void setVertexShaderCode(String vertexShaderCode) {
        this.vertexShaderCode = vertexShaderCode;
    }

    public String getFragmentShaderCode() {
        return fragmentShaderCode;
    }

    public void setFragmentShaderCode(String fragmentShaderCode) {
        this.fragmentShaderCode = fragmentShaderCode;
    }

}
