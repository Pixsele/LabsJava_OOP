package sql.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.DTO.MathFunctionsDTO;
import sql.DTO.PointDTO;
import sql.models.MathFunctionsEntity;
import sql.models.PointEntity;
import sql.repositories.MathFunctionsRepository;
import sql.repositories.PointRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointService {

    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private MathFunctionsRepository mathFunctionsRepository;

    public PointDTO create(PointDTO dto_obj){
        PointEntity entity = convertToEntity(dto_obj);
        PointEntity createdentity = pointRepository.save(entity);

        return convertToDto(createdentity);
    }

    public PointDTO read(Long id) {
        return pointRepository.findById(id).map(this::convertToDto).orElse(null);
    }

    public PointDTO update(PointDTO dto_obj){
        if(pointRepository.existsById(dto_obj.getId())){
            PointEntity entity = convertToEntity(dto_obj);
            PointEntity updatedentity = pointRepository.save(entity);

            return convertToDto(updatedentity);
        }
        throw new RuntimeException();
    }

    public void delete(PointDTO dto_obj){
        if(pointRepository.existsById(dto_obj.getId())){
            pointRepository.deleteById(dto_obj.getId());

        } else{
            throw new RuntimeException();
        }
    }

    public PointDTO getById(Long id) {
        if (pointRepository.existsById(id)) {
            return convertToDto(pointRepository.getById(id));
        }
        return null;
    }

    private PointDTO convertToDto(PointEntity entity){
        PointDTO dto_obj = new PointDTO();

        dto_obj.setId(entity.getId());
        dto_obj.setFunction(entity.getFunction().getId());
        dto_obj.setX(entity.getX());
        dto_obj.setY(entity.getY());

        return dto_obj;
    }

    private PointEntity convertToEntity(PointDTO dto_obj){
        PointEntity entity = new PointEntity();

        entity.setId(dto_obj.getId());
        entity.setFunction(mathFunctionsRepository.findById(dto_obj.getFunction()).orElse(null));
        entity.setX(dto_obj.getX());
        entity.setY(dto_obj.getY());

        return entity;
    }

    public List<PointDTO> findByFunction(Long id) {
        MathFunctionsEntity function = this.mathFunctionsRepository.findById(id).orElse(null);
        if (function == null) {
            return null;
        }

        return this.pointRepository.findByFunction(function)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
