package dk.abandonship.entities.documetationNodes;

public class CanvasDocumentationNode extends DocumentationNode {

    private byte[] imageData;

    public CanvasDocumentationNode(int id, byte[] imageData) {
        super(id);
        this.imageData = imageData;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
