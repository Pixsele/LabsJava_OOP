package sql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.DTO.ProcessDTO;
import sql.DTO.ResultProcessDTO;
import sql.models.ResultProcessEntity;
import sql.repositories.ProcessRepository;
import sql.repositories.ResultProcessRepository;


@Service
public class ResultProcessService {

    @Autowired
    private ResultProcessRepository resultProcessRepository;
    @Autowired
    private ProcessRepository processRepository;

    public ResultProcessDTO create(ResultProcessDTO dto_obj){
        ResultProcessEntity entity = convertToEntity(dto_obj);
        ResultProcessEntity createdentity = resultProcessRepository.save(entity);

        return convertToDto(createdentity);
    }

    public ResultProcessDTO read(Long id) {
        return resultProcessRepository.findById(id).map(this::convertToDto).orElse(null);
    }

    public ResultProcessDTO update(ResultProcessDTO dto_obj){
        if(resultProcessRepository.existsById(dto_obj.getId())){
            ResultProcessEntity entity = convertToEntity(dto_obj);
            ResultProcessEntity updatedentity = resultProcessRepository.save(entity);

            return convertToDto(updatedentity);
        }
        throw new RuntimeException();
    }

    public void delete(ResultProcessDTO dto_obj){
        if(resultProcessRepository.existsById(dto_obj.getId())){
            resultProcessRepository.deleteById(dto_obj.getId());

        } else{
            throw new RuntimeException();
        }
    }

    private ResultProcessDTO convertToDto(ResultProcessEntity entity){
        ResultProcessDTO dto_obj = new ResultProcessDTO();

        dto_obj.setId(entity.getId());
        dto_obj.setProcessId(entity.getProcess().getId());
        dto_obj.setResult(entity.getResult());

        return dto_obj;
    }

    private ResultProcessEntity convertToEntity(ResultProcessDTO dto_obj){
        ResultProcessEntity entity = new ResultProcessEntity();

        entity.setId(dto_obj.getId());
        entity.setProcess(processRepository.findById(dto_obj.getId()).orElse(null));
        entity.setResult(dto_obj.getResult());

        return entity;
    }

    public ResultProcessDTO getById(Long id){
        if(resultProcessRepository.existsById(id)){
            return convertToDto(resultProcessRepository.getById(id));
        }
        return null;
    }
}
