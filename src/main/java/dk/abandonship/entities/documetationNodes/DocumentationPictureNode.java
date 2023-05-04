package dk.abandonship.entities.documetationNodes;

import javafx.scene.image.Image;

public class DocumentationPictureNode extends DocumentationNode{
    private String pictureTittle;
    private Image images;

    public DocumentationPictureNode(int id, String pictureTittle, Image images) {
        super(id);
        this.pictureTittle = pictureTittle;
        this.images = images;
    }

    public String getPictureTittle() {
        return pictureTittle;
    }

    public void setPictureTittle(String pictureTittle) {
        this.pictureTittle = pictureTittle;
    }

    public Image getImages() {
        return images;
    }

    public void setImages(Image images) {
        this.images = images;
    }
}
