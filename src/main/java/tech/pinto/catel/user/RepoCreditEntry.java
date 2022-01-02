package tech.pinto.catel.user;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.pinto.catel.domain.CreditEntry;

import java.util.List;

public interface RepoCreditEntry extends JpaRepository<CreditEntry, Long> {
    List<CreditEntry> findAllByUserId(long id);
}
