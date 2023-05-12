package dk.abandonship.entities.documetationNodes;

public class DocumentationPictureNode extends DocumentationNode {
    private String pictureTitle;

    private byte[] imageData;

    public DocumentationPictureNode(int id, String pictureTitle, byte[] imageData) {
        super(id);
        this.pictureTitle = pictureTitle;
        this.imageData = imageData;
    }

    public String getPictureTitle() {
        return pictureTitle;
    }

    public void setPictureTitle(String pictureTitle) {
        this.pictureTitle = pictureTitle;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
