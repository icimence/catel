package tech.pinto.catel.user;

import lombok.*;
import org.hibernate.Hibernate;
import tech.pinto.catel.enums.UserType;
import tech.pinto.catel.enums.VipType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_id")
    private Long id;
    private String email;
    private String password;
    private String username;
    private double credit = 100;
    private String avatar = null;
    private LocalDateTime vipEnd = LocalDateTime.now();
    private UserType userType = UserType.Client;
    private VipType vipType = VipType.Nil;

    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
