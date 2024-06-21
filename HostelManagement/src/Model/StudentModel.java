package Model;

public class StudentModel {
    private int host_ID; 
    private String name;
    private int age;
    private String username;
    private String password;
    private String host_type;

    public StudentModel() {
    }

    public StudentModel(int host_ID, String name, int age, String username, String password, String host_type) {
        this.host_ID = host_ID;
        this.name = name;
        this.age = age;
        this.username = username;
        this.password = password;
        this.host_type = host_type;
    }

    public int getHost_ID() {
        return host_ID;
    }

    public void setHost_ID(int host_ID) {
        this.host_ID = host_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getHost_type() {
        return host_type;
    }

    public void setHost_type(String host_type) {
        this.host_type = host_type;
    }
}
