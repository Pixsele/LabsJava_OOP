package db.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import db.DTO.MathFunctionsDTO;
import db.models.MathFunctionsEntity;
import db.repositories.MathFunctionsRepository;
import db.service.MathFunctionsService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MathFunctionsServiceTest {

    @InjectMocks
    private MathFunctionsService mathFunctionsService;

    @Mock
    private MathFunctionsRepository mathFunctionsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        MathFunctionsDTO dto = new MathFunctionsDTO();
        dto.setName("add");
        dto.setxTo(10.0);
        dto.setxFrom(0.0);
        dto.setCount(100);

        MathFunctionsEntity entity = new MathFunctionsEntity();
        entity.setName("add");
        entity.setxTo(10.0);
        entity.setxFrom(0.0);
        entity.setCount(100);

        when(mathFunctionsRepository.save(any(MathFunctionsEntity.class))).thenReturn(entity);

        MathFunctionsDTO result = mathFunctionsService.create(dto);

        assertNotNull(result);
        assertEquals("add", result.getName());
        assertEquals(10.0, result.getxTo());
        assertEquals(0.0, result.getxFrom());
        assertEquals(100, result.getCount());
    }

    @Test
    void testRead() {
        Long id = 1L;
        MathFunctionsEntity entity = new MathFunctionsEntity();
        entity.setId(id);
        entity.setName("add");
        entity.setxTo(10.0);
        entity.setxFrom(0.0);
        entity.setCount(100);

        when(mathFunctionsRepository.findById(id)).thenReturn(Optional.of(entity));

        MathFunctionsDTO result = mathFunctionsService.read(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("add", result.getName());
    }

    @Test
    void testUpdate() {
        MathFunctionsDTO dto = new MathFunctionsDTO();
        dto.setId(1L);
        dto.setName("add");
        dto.setxTo(10.0);
        dto.setxFrom(0.0);
        dto.setCount(100);

        MathFunctionsEntity entity = new MathFunctionsEntity();
        entity.setId(1L);
        entity.setName("add");
        entity.setxTo(10.0);
        entity.setxFrom(0.0);
        entity.setCount(100);

        when(mathFunctionsRepository.existsById(1L)).thenReturn(true);
        when(mathFunctionsRepository.save(any(MathFunctionsEntity.class))).thenReturn(entity);

        MathFunctionsDTO result = mathFunctionsService.update(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("add", result.getName());
    }

    @Test
    void testDelete() {
        MathFunctionsDTO dto = new MathFunctionsDTO();
        dto.setId(1L);

        when(mathFunctionsRepository.existsById(1L)).thenReturn(true);

        mathFunctionsService.delete(dto);

        verify(mathFunctionsRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        MathFunctionsEntity entity = new MathFunctionsEntity();
        entity.setId(id);
        entity.setName("add");

        when(mathFunctionsRepository.existsById(id)).thenReturn(true);
        when(mathFunctionsRepository.getById(id)).thenReturn(entity);

        MathFunctionsDTO result = mathFunctionsService.getById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("add", result.getName());
    }

    @Test
    void testFindsByName() {
        String name = "add";
        MathFunctionsEntity entity = new MathFunctionsEntity();
        entity.setName(name);

        when(mathFunctionsRepository.findByName(name)).thenReturn(Collections.singletonList(entity));

        List<MathFunctionsDTO> result = mathFunctionsService.findsByName(name);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(name, result.get(0).getName());
    }

    @Test
    void testUpdateNonExistentFunction() {
        MathFunctionsDTO dto = new MathFunctionsDTO();
        dto.setId(999L);
        dto.setName("nonexistent");

        when(mathFunctionsRepository.existsById(dto.getId())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> mathFunctionsService.update(dto));
    }

    @Test
    void testDeleteNonExistentFunction() {
        MathFunctionsDTO dto = new MathFunctionsDTO();
        dto.setId(999L);

        when(mathFunctionsRepository.existsById(dto.getId())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> mathFunctionsService.delete(dto));
    }

    @Test
    void testReadNonExistentFunction() {
        Long id = 999L;

        when(mathFunctionsRepository.findById(id)).thenReturn(Optional.empty());

        MathFunctionsDTO result = mathFunctionsService.read(id);

        assert result == null; // Проверка на null, если функция не найдена
    }

    @Test
    void testGetByIdNonExistentFunction() {
        Long id = 999L;

        when(mathFunctionsRepository.existsById(id)).thenReturn(false);

        MathFunctionsDTO result = mathFunctionsService.getById(id);

        assert result == null; // Ожидаем null, если ID не существует
    }
}