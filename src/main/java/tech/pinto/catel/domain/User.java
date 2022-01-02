package tech.pinto.catel.domain;

import lombok.*;
import org.hibernate.Hibernate;
import tech.pinto.catel.enums.UserType;
import tech.pinto.catel.enums.VipLevel;
import tech.pinto.catel.util.UtilRandom;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
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
    private String avatar = UtilRandom.ofAvatar();
    private LocalDateTime vipEnd = LocalDateTime.now();
    private UserType userType = UserType.Client;
    private VipLevel vipLevel = VipLevel.Nil;
    
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Resident> residents = new ArrayList<>();

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
