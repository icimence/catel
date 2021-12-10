package tech.pinto.catel.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_resident_id")
    private Long id;
    private String realName;
    private String idNo;
    private String phoneNumber;
    private LocalDate birthday;

    @ManyToOne
    private User owner;

    public Resident() {
        realName = "default_real_name";
        phoneNumber = "12345678910";
    }

    public Resident(String realName, String idNo, String phoneNumber, LocalDate birthday, User owner) {
        this.realName = realName;
        this.idNo = idNo;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Resident resident = (Resident) o;
        return id != null && Objects.equals(id, resident.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
