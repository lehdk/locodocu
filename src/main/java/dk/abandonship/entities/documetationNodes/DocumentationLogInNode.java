package dk.abandonship.entities.documetationNodes;

public class DocumentationLogInNode extends DocumentationNode{

    private String device;
    private String username;
    private String password;

    public DocumentationLogInNode(int id, String device, String username, String password) {
        super(id);
        this.device = device;
        this.username = username;
        this.password = password;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
