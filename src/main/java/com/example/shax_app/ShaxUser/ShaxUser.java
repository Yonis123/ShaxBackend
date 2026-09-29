package com.example.shax_app.ShaxUser;


import jakarta.persistence.*;

@Entity(name="ShaxUser")
@Table(
        name = "shax_user",
        uniqueConstraints = {
  
        }
)
public class ShaxUser {

    @Id
    @SequenceGenerator(
            name = "shax_user_sequence",
            sequenceName = "shax_user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "shax_user_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private long id;

    @Column(
            name = "user_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String userName;

    @Column(
            name = "elo",
            nullable = false,
            columnDefinition = "INT"
    )
    private int elo;

    public ShaxUser(String userName, int elo) {
        this.elo = elo;
        this.userName = userName;
       
    }

    public ShaxUser() {

    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", elo=" + elo +
                '}';
    }
}
