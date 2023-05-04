package dk.abandonship.entities;

import java.util.LinkedHashSet;
import java.util.Set;

public class Documentation {

    private int id;
    private String name;

    private Set<DocumentationNode> documentationNodes;

    public Documentation(int id, String name) {
        this.id = id;
        this.name = name;
        this.documentationNodes = new LinkedHashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DocumentationNode> getDocumentationNodes() {
        return documentationNodes;
    }

    public void setDocumentationNodes(Set<DocumentationNode> documentationNodes) {
        this.documentationNodes = documentationNodes;
    }

    public void addDocumentationNode(DocumentationNode node) {
        documentationNodes.add(node);
    }

    @Override
    public String toString() {
        return name;
    }
}
