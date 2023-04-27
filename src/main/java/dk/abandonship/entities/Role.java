package dk.abandonship.entities;

public class Role {

    private int id;
    private String name;

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Two role objects are equal if their ids are the same.
     * @param obj The object to compare with.
     * @return If they are the same roles.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Role other = (Role) obj;

        return getId() == other.getId();
    }
}
