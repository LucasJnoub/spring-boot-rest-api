package restful.api.main.domain.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Account account;
    @OneToOne(cascade = CascadeType.ALL)
    private Card card;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }



    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
