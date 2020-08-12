/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enitities;

/**
 *
 * @author alaaKholi
 */
public class Book {
    private Integer id;
    private String name;
    private String description;

    public Book() {
    }

    public Book(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @Override
//    public String toString() {
//        return String.format("%-5s %-10s", id, name);
//    }
//    

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", name=" + name + ", description=" + description + '}';
    }
    
    
}
