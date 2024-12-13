package info.stepanoff.trsis.samples.db.dao;

import info.stepanoff.trsis.samples.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
}
