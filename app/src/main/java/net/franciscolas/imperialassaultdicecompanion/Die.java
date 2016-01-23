package net.franciscolas.imperialassaultdicecompanion;

import java.util.ArrayList;
import java.util.List;

/**
 * A single die of Imperial Assault
 */
public class Die {
    private List<Face> faces = null;
    private String name = "";

    public Die() {
        faces = new ArrayList<>();
    }

    public List<Face> getFaces() {
        return faces;
    }

    public void setFaces(List<Face> faces) {
        this.faces = faces;
    }

    public void addFace(Face face) {
        this.faces.add(face);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
