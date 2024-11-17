package web.service;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.DTO.MathFunctionsDTO;
import database.models.MathFunctionsEntity;
import database.repositories.MathFunctionsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MathFunctionsService {

    private final MathFunctionsRepository mathFunctionsRepository;

    @Autowired
    public MathFunctionsService(MathFunctionsRepository mathFunctionsRepository) {
        this.mathFunctionsRepository = mathFunctionsRepository;
    }

    @PersistenceContext
    private EntityManager entityManager;

    public MathFunctionsDTO create(MathFunctionsDTO dto_obj){
        MathFunctionsEntity entity = convertToEntity(dto_obj);
        MathFunctionsEntity createdentity = mathFunctionsRepository.save(entity);

        return convertToDto(createdentity);
    }

    public MathFunctionsDTO read(Long id) {
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


    private MathFunctionsDTO convertToDto(MathFunctionsEntity entity){
        MathFunctionsDTO dto_obj = new MathFunctionsDTO();

        dto_obj.setId(entity.getId());
        dto_obj.setName(entity.getName());
        dto_obj.setxTo(entity.getxTo());
        dto_obj.setxFrom(entity.getxFrom());
        dto_obj.setCount(entity.getCount());

        return dto_obj;
    }

    private MathFunctionsEntity convertToEntity(MathFunctionsDTO dto_obj){
        MathFunctionsEntity entity = new MathFunctionsEntity();

        entity.setId(dto_obj.getId());
        entity.setName(dto_obj.getName());
        entity.setxTo(dto_obj.getxTo());
        entity.setxFrom(dto_obj.getxFrom());
        entity.setCount(dto_obj.getCount());

        return entity;
    }

    public List<MathFunctionsDTO> findsByName(String name) {
        return this.mathFunctionsRepository.findByName(name).stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
