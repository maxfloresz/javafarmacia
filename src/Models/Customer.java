//https://www.youtube.com/watch?v=a223i_-rUs8&list=PLffixYYr8M_uPiKk1VZOjhTHE8UGcQvKR&index=19

package Models;


public class Customer {

    private int id;
    private String full_name;
    private String address;
    private String telephone;
    private String email;
    private String created;
    private String updated;
    
    public Customer(){
    }

    public Customer(int id, String full_name, String address, String telephone, String email, String created, String updated) {
        this.id = id;
        this.full_name = full_name;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.created = created;
        this.updated = updated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
