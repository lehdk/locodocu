package dk.abandonship.entities;

public class DocumentationTextFieldNode extends DocumentationNode {

    private String text;

    public DocumentationTextFieldNode(int id, String text) {
        super(id);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
