package dev.kason.catan.backend.board;

@SuppressWarnings("ClassCanBeRecord")
public class Port {

    private final Resource resource;
    private final Vertex[] vertices;

    public Port(Resource resource, Vertex[] vertices) {
        this.resource = resource;
        if (vertices.length != 2) {
            throw new IllegalArgumentException("Port must have 2 vertices");
        }
        for (Vertex vertex : vertices) {
            if (vertex == null) {
                throw new IllegalArgumentException("Port must have 2 non null vertices");
            }
        }
        this.vertices = vertices;
    }

    public boolean isGeneric() {
        return resource == null;
    }

    public boolean hasVertex(Vertex vertex) {
        for (Vertex v : vertices) {
            if (v == vertex) {
                return true;
            }
        }
        return false;
    }

}
