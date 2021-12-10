package tech.pinto.catel.user;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.pinto.catel.domain.CreditEntry;

public interface RepoCreditEntry extends JpaRepository<CreditEntry, Long> {
}
