package vendingmachine.app.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    protected String id;

    public BaseEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
