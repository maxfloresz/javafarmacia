//https://www.youtube.com/watch?v=rbo6tUf0hZE&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=23
package Models;


public class Category {
    //Creamos las variables
    private int id;
    private String name;
    private String created;
    private String updated;

    public Category() {
    }

    public Category(int id, String name, String created, String updated) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.updated = updated;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
    
    
}
