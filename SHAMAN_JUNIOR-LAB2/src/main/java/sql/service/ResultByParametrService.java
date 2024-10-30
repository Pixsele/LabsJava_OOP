package sql.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.DTO.ResultsByParametrDTO;
import sql.models.ResultsByParametrEntity;
import sql.repositories.MathFunctionsRepository;
import sql.repositories.ResultsByParametrRepository;


@Service
public class ResultByParametrService {

    @Autowired
    ResultsByParametrRepository resultsByParametrRepository;

    @Autowired
    MathFunctionsRepository mathFunctionsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ResultsByParametrDTO create(ResultsByParametrDTO dto_obj){
        ResultsByParametrEntity entity = convertToEntity(dto_obj);
        ResultsByParametrEntity createdentity = resultsByParametrRepository.save(entity);

        return convertToDto(createdentity);
    }

    public ResultsByParametrDTO read(long id) {
        return resultsByParametrRepository.findById(id).map(this::convertToDto).orElse(null);
    }

    public ResultsByParametrDTO update(ResultsByParametrDTO dto_obj){
        if(resultsByParametrRepository.existsById(dto_obj.getId())){
            ResultsByParametrEntity entity = convertToEntity(dto_obj);
            ResultsByParametrEntity updatedentity = resultsByParametrRepository.save(entity);

            return convertToDto(updatedentity);
        }
        throw new RuntimeException();
    }

    public void delete(ResultsByParametrDTO dto_obj){
        if(resultsByParametrRepository.existsById(dto_obj.getId())){
            resultsByParametrRepository.deleteById(dto_obj.getId());

        } else{
            throw new RuntimeException();
        }
    }

    public ResultsByParametrDTO getById(Long id){
        if(resultsByParametrRepository.existsById(id)){
            return convertToDto(resultsByParametrRepository.getById(id));
        }
        return null;
    }

    private ResultsByParametrDTO convertToDto(ResultsByParametrEntity entity){
        ResultsByParametrDTO dto_obj = new ResultsByParametrDTO();

        dto_obj.setId(entity.getId());
        dto_obj.setFunctionId(entity.getMathFunction().getId());
        dto_obj.setCreationTime(entity.getTimestamp());
        dto_obj.setParametr(entity.getParametr());

        return dto_obj;
    }

    private ResultsByParametrEntity convertToEntity(ResultsByParametrDTO dto_obj){
        ResultsByParametrEntity entity = new ResultsByParametrEntity();

        entity.setId(dto_obj.getId());
        entity.setMathFunction(mathFunctionsRepository.findById(dto_obj.getFunctionId()).orElse(null));
        entity.setTimestamp(dto_obj.getCreationTime());
        entity.setParametr(dto_obj.getParametr());

        return entity;
    }
}
