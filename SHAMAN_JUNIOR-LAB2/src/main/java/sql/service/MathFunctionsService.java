package sql.service;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.DTO.MathFunctionsDTO;
import sql.models.MathFunctionsEntity;
import sql.repositories.MathFunctionsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MathFunctionsService {

    @Autowired
    private MathFunctionsRepository mathFunctionsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public MathFunctionsDTO create(MathFunctionsDTO dto_obj){
        MathFunctionsEntity entity = convertToEntity(dto_obj);
        MathFunctionsEntity createdentity = mathFunctionsRepository.save(entity);

        return convertToDto(createdentity);
    }

    public MathFunctionsDTO read(long id) {
        return mathFunctionsRepository.findById(id).map(this::convertToDto).orElse(null);
    }

    public MathFunctionsDTO update(MathFunctionsDTO dto_obj){
        if(mathFunctionsRepository.existsById(dto_obj.getId())){
            MathFunctionsEntity entity = convertToEntity(dto_obj);
            MathFunctionsEntity updatedentity = mathFunctionsRepository.save(entity);

            return convertToDto(updatedentity);
        }
        throw new RuntimeException();
    }

    public void delete(MathFunctionsDTO dto_obj){
        if(mathFunctionsRepository.existsById(dto_obj.getId())){
            mathFunctionsRepository.deleteById(dto_obj.getId());

        } else{
            throw new RuntimeException();
        }
    }

    public MathFunctionsDTO getById(Long id){
        if(mathFunctionsRepository.existsById(id)){
            return convertToDto(mathFunctionsRepository.getById(id));
        }
        return null;
    }

    public List<MathFunctionsDTO> getByName(String name){
        return mathFunctionsRepository.findByFunctionName(name).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private MathFunctionsDTO convertToDto(MathFunctionsEntity entity){
        MathFunctionsDTO dto_obj = new MathFunctionsDTO();

        dto_obj.setId(entity.getId());
        dto_obj.setFunctionName(entity.getFunction_name());
        dto_obj.setFunctionType(entity.getFunction_type());
        dto_obj.setCreationTime(entity.getCreation_time());

        return dto_obj;
    }

    private MathFunctionsEntity convertToEntity(MathFunctionsDTO dto_obj){
        MathFunctionsEntity entity = new MathFunctionsEntity();

        entity.setId(dto_obj.getId());
        entity.setFunction_name(dto_obj.getFunctionName());
        entity.setFunction_type(dto_obj.getFunctionType());
        entity.setCreation_time(dto_obj.getCreationTime());

        return entity;
    }
}
