package info.stepanoff.trsis.samples.service;

import info.stepanoff.trsis.samples.db.model.Access;
import info.stepanoff.trsis.samples.db.model.AccessID;
import info.stepanoff.trsis.samples.rest.model.AccessDTO;
import java.util.List;

public interface AccessService {
    AccessDTO create(Integer employeeID, Integer roomID);
    List<AccessDTO> readAll();
    AccessDTO read(AccessID id);
    boolean delete(AccessID id);
}
