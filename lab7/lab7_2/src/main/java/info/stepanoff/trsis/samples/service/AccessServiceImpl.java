package info.stepanoff.trsis.samples.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.stepanoff.trsis.samples.db.dao.AccessRepository;
import info.stepanoff.trsis.samples.db.model.Access;
import info.stepanoff.trsis.samples.db.model.AccessID;
import info.stepanoff.trsis.samples.rest.model.AccessDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessServiceImpl implements AccessService {
    private final AccessRepository accessRepository;
    private final ObjectMapper objectMapper;

    @Override
    public AccessDTO create(Integer employeeID, Integer roomID){
        return objectMapper.convertValue(accessRepository.save(new Access(employeeID, roomID)), AccessDTO.class);
    }

    @Override
    public List<AccessDTO> readAll(){
        return accessRepository.findAll().stream()
                .map(access -> objectMapper.convertValue(access, AccessDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AccessDTO read(AccessID id){
        return objectMapper.convertValue(accessRepository.findById(id), AccessDTO.class);
    }

    @Override
    public boolean delete(AccessID id){
        if (!accessRepository.existsById(id))
            return false;
        accessRepository.deleteById(id);
        return true;
    }
}
