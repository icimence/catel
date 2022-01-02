package tech.pinto.catel.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CreditEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_credit_entry_id")
    private Long id;
    private double delta;
    @ManyToOne
    private Order order;
    @ManyToOne
    private User user;
    
    private LocalDateTime createdWhen = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CreditEntry that = (CreditEntry) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
