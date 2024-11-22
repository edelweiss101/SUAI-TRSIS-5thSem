package info.stepanoff.trsis.samples.db.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import info.stepanoff.trsis.samples.db.model.Access;
import info.stepanoff.trsis.samples.db.model.AccessID;

import java.util.List;

@Repository
public interface AccessRepository extends CrudRepository<Access, AccessID> {
    public List<Access> findAll();
}
