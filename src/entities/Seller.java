package entities;

public class Seller {

    private String documentType;
    private int dni;
    private String name;
    private String lastName;


    public Seller(String documentType, int dni, String name, String lastName) {
        this.documentType = documentType;
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
    }

    public Seller() {
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
