package sql.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.DTO.MathFunctionsDTO;
import sql.DTO.ResultDTO;
import sql.models.ResultEntity;
import sql.repositories.ResultRepository;
import sql.repositories.ResultsByParametrRepository;

@Service
public class ResultService {

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    ResultsByParametrRepository resultsByParametrRepository;

    @PersistenceContext
    private EntityManager entityManager;
    
    public ResultDTO create(ResultDTO dto_obj){
        ResultEntity entity = convertToEntity(dto_obj);
        ResultEntity createdentity = resultRepository.save(entity);

        return convertToDto(createdentity);
    }

    public ResultDTO read(long id) {
        return resultRepository.findById(id).map(this::convertToDto).orElse(null);
    }

    public ResultDTO update(ResultDTO dto_obj){
        if(resultRepository.existsById(dto_obj.getId())){
            ResultEntity entity = convertToEntity(dto_obj);
            ResultEntity updatedentity = resultRepository.save(entity);

            return convertToDto(updatedentity);
        }
        throw new RuntimeException();
    }

    public void delete(ResultDTO dto_obj){
        if(resultRepository.existsById(dto_obj.getId())){
            resultRepository.deleteById(dto_obj.getId());

        } else{
            throw new RuntimeException();
        }
    }

    public ResultDTO getById(Long id){
        if(resultRepository.existsById(id)){
            return convertToDto(resultRepository.getById(id));
        }
        return null;
    }
    
    
    private ResultDTO convertToDto(ResultEntity entity){
        ResultDTO dto_obj = new ResultDTO();

        dto_obj.setId(entity.getId());
        dto_obj.setResultByParametr(entity.getResultsByParametr().getId());
        dto_obj.setResultValue(entity.getResult_value());

        return dto_obj;
    }

    private ResultEntity convertToEntity(ResultDTO dto_obj){
        ResultEntity entity = new ResultEntity();

        entity.setId(dto_obj.getId());
        entity.setResultsByParametr(resultsByParametrRepository.findById(dto_obj.getResultByParametr()).orElse(null));
        entity.setResult_value(dto_obj.getResultValue());

        return entity;
    }
}
