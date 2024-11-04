package sql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.DTO.MathFunctionsDTO;
import sql.DTO.ProcessDTO;
import sql.models.ProcessEntity;
import sql.repositories.MathFunctionsRepository;
import sql.repositories.ProcessRepository;

@Service
public class ProcessService {

    @Autowired
    private ProcessRepository processRepository;
    @Autowired
    private MathFunctionsRepository mathFunctionsRepository;

    public ProcessDTO create(ProcessDTO dto_obj){
        ProcessEntity entity = convertToEntity(dto_obj);
        ProcessEntity createdentity = processRepository.save(entity);

        return convertToDto(createdentity);
    }

    public ProcessDTO read(Long id) {
        return processRepository.findById(id).map(this::convertToDto).orElse(null);
    }

    public ProcessDTO update(ProcessDTO dto_obj){
        if(processRepository.existsById(dto_obj.getId())){
            ProcessEntity entity = convertToEntity(dto_obj);
            ProcessEntity updatedentity = processRepository.save(entity);

            return convertToDto(updatedentity);
        }
        throw new RuntimeException();
    }

    public void delete(ProcessDTO dto_obj){
        if(processRepository.existsById(dto_obj.getId())){
            processRepository.deleteById(dto_obj.getId());

        } else{
            throw new RuntimeException();
        }
    }


    private ProcessDTO convertToDto(ProcessEntity entity){
        ProcessDTO dto_obj = new ProcessDTO();

        dto_obj.setId(entity.getId());
        dto_obj.setFunction(entity.getFunction().getId());
        dto_obj.setType(entity.getType());
        dto_obj.setStartTime(entity.getStartTime());
        dto_obj.setEndTime(entity.getEndTime());

        return dto_obj;
    }

    private ProcessEntity convertToEntity(ProcessDTO dto_obj){
        ProcessEntity entity = new ProcessEntity();

        entity.setId(dto_obj.getId());
        entity.setFunction(mathFunctionsRepository.findById(dto_obj.getId()).orElse(null));
        entity.setType(dto_obj.getType());
        entity.setStartTime(dto_obj.getStartTime());
        entity.setEndTime(dto_obj.getEndTime());

        return entity;
    }

    public ProcessDTO getById(Long id){
        if(processRepository.existsById(id)){
            return convertToDto(processRepository.getById(id));
        }
        return null;
    }
}
