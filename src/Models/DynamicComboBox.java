
package Models;

//https://www.youtube.com/watch?v=H4d3SNxVka0&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=49
//11:20
public class DynamicComboBox {
    
    private int id;
    private String name;

    public DynamicComboBox() {
    }

    public DynamicComboBox(int id, String name) {
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
    
    
    //sobre escribimos el metodo
    @Override
    public String toString(){
        return this.getName();
    }
    
}
