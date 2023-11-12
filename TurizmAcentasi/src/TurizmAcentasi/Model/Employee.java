package TurizmAcentasi.Model;

public class Employee extends User{
    public Employee() {
    }

    public Employee(int id, String name, String username, String password, String type) {
        super(id, name, username, password, type);
    }
}
