package info.stepanoff.trsis.samples.db.dao;

import info.stepanoff.trsis.samples.db.model.Access;
import info.stepanoff.trsis.samples.db.model.AccessID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessRepository extends JpaRepository<Access, AccessID> {
    public List<Access> findAll();
}
