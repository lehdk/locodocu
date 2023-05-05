package dk.abandonship.entities.documetationNodes;

public abstract class DocumentationNode {

    public static final int UNUSED_NODE_ID = -42;

    private int id;

    public DocumentationNode(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
