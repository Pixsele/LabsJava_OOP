package web.service;

import web.DTO.PointDTO;
import database.models.MathFunctionsEntity;
import database.models.PointEntity;
import database.repositories.MathFunctionsRepository;
import database.repositories.PointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PointServiceTest {

    @InjectMocks
    private PointService pointService;

    @Mock
    private PointRepository pointRepository;

    @Mock
    private MathFunctionsRepository mathFunctionsRepository;

    @Mock
    MathFunctionsEntity function;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(function.getId()).thenReturn(1L);
    }

    @Test
    void testCreate() {
        PointDTO dto = new PointDTO();
        dto.setX(1.0);
        dto.setY(2.0);
        dto.setFunction(1L);

        PointEntity entity = new PointEntity();
        entity.setFunction(function);
        entity.setId(1L);
        entity.setX(dto.getX());
        entity.setY(dto.getY());

        when(pointRepository.save(any())).thenReturn(entity);
        when(mathFunctionsRepository.findById(dto.getFunction())).thenReturn(Optional.ofNullable(function));
        when(pointRepository.save(any(PointEntity.class))).thenReturn(entity);

        PointDTO result = pointService.create(dto);

        assertNotNull(result);
        assertEquals(dto.getX(), result.getX());
        assertEquals(dto.getY(), result.getY());
        verify(pointRepository, times(1)).save(any(PointEntity.class));
    }

    @Test
    void testRead() {
        PointEntity entity = new PointEntity();
        entity.setId(1L);
        entity.setFunction(function);
        entity.setX(1.0);
        entity.setY(2.0);

        when(pointRepository.save(any())).thenReturn(entity);
        when(pointRepository.findById(1L)).thenReturn(Optional.of(entity));

        PointDTO result = pointService.read(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1.0, result.getX());
        assertEquals(2.0, result.getY());
    }

    @Test
    void testUpdate() {
        PointDTO dto = new PointDTO();
        dto.setId(1L);
        dto.setX(3.0);
        dto.setY(4.0);
        dto.setFunction(1L);

        PointEntity existingEntity = new PointEntity();
        existingEntity.setFunction(function);
        existingEntity.setId(1L);
        existingEntity.setX(1.0);
        existingEntity.setY(2.0);

        when(pointRepository.existsById(dto.getId())).thenReturn(true);
        when(pointRepository.save(any(PointEntity.class))).thenReturn(existingEntity);

        PointDTO result = pointService.update(dto);

        assertEquals(1.0, result.getX());
        assertEquals(2.0, result.getY());
    }

    @Test
    void testDelete() {
        PointDTO dto = new PointDTO();
        dto.setId(1L);

        when(pointRepository.existsById(dto.getId())).thenReturn(true);

        pointService.delete(dto);

        verify(pointRepository, times(1)).deleteById(dto.getId());
    }

    @Test
    void testGetById() {
        PointEntity entity = new PointEntity();
        entity.setFunction(function);
        entity.setId(1L);
        entity.setX(1.0);
        entity.setY(2.0);

        when(pointRepository.existsById(1L)).thenReturn(true);
        when(pointRepository.getById(1L)).thenReturn(entity);

        PointDTO result = pointService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindByFunction() {
        Long functionId = 1L;
        MathFunctionsEntity function = new MathFunctionsEntity();
        function.setId(functionId);

        PointEntity point1 = new PointEntity();
        point1.setFunction(function);

        point1.setX(1.0);
        point1.setY(2.0);

        PointEntity point2 = new PointEntity();

        point2.setFunction(function);

        point2.setX(3.0);
        point2.setY(4.0);

        when(mathFunctionsRepository.findById(functionId)).thenReturn(Optional.of(function));
        when(pointRepository.findByFunction(function)).thenReturn(Arrays.asList(point1, point2));

        List<PointDTO> result = pointService.findByFunction(functionId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1.0, result.get(0).getX());
        assertEquals(2.0, result.get(0).getY());
    }

    @Test
    void testFindByFunctionNotFound() {
        Long functionId = 999L;

        when(mathFunctionsRepository.findById(functionId)).thenReturn(Optional.empty());

        List<PointDTO> result = pointService.findByFunction(functionId);

        assertNull(result);
    }
}
