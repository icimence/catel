package tech.pinto.catel.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.pinto.catel.domain.Comment;

import java.util.List;

public interface RepoComment extends JpaRepository<Comment, Long> {
    List<Comment> findByHotelId(long id);
}
