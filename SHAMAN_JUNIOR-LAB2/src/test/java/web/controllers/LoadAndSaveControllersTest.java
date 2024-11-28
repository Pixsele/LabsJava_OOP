package web.controllers;

import database.models.MathFunctionsEntity;
import database.models.PointEntity;
import database.repositories.MathFunctionsRepository;
import exceptions.LoadFunctionExecption;
import function.ArrayTabulatedFunction;
import function.api.TabulatedFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import web.DTO.MathFunctionsDTO;
import web.core.FunctionRepository;
import web.service.MathFunctionsService;
import web.service.PointService;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class LoadAndSaveControllersTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private FunctionRepository functionRepository;

    @MockBean
    private MathFunctionsService mathFunctionsService;

    @MockBean
    private PointService pointService;

    @Autowired
    private MockMvc mockMvc;

    private LoadAndSaveControllers controller;
    private HttpSession session;
    private Model model;
    private MathFunctionsRepository mathFunctionsRepository;
    private TabulatedFunction mockFunction;

    @BeforeEach
    public void setUp() {
        mathFunctionsRepository = mock(MathFunctionsRepository.class);
        controller = new LoadAndSaveControllers(mathFunctionsService,pointService);
        controller.mathFunctionsRepository = mathFunctionsRepository;
        session = mock(HttpSession.class);
        model = mock(Model.class);
        mockFunction = mock(TabulatedFunction.class);
    }

    @Test
    public void testLoadSuccess() {
        String target = "test";
        Long id = 1L;

        // Mocking data
        MathFunctionsEntity mockEntity = mock(MathFunctionsEntity.class);
        PointEntity point1 = new PointEntity();
        PointEntity point2 = new PointEntity();
        point1.setX(1.0);
        point1.setY(1.0);
        point2.setX(2.0);
        point2.setY(2.0);

        List<PointEntity> list = new ArrayList<>();
        list.add(point1);
        list.add(point2);

        when(mathFunctionsRepository.findById(id)).thenReturn(java.util.Optional.of(mockEntity));
        when(mockEntity.getPoints()).thenReturn(list);

        String result = controller.load(target, id, model, session);

        verify(session).setAttribute(eq(target + "Func"), any(ArrayTabulatedFunction.class));
        assertEquals("redirect:/tabulated-operations", result);
    }

    @Test
    public void testLoadFailsWithTooFewPoints() {
        String target = "test";
        Long id = 1L;

        MathFunctionsEntity mockEntity = mock(MathFunctionsEntity.class);
        PointEntity point1 = new PointEntity();
        point1.setX(1.0);
        point1.setY(1.0);
        List<PointEntity> list = new ArrayList<>();
        list.add(point1);

        when(mathFunctionsRepository.findById(id)).thenReturn(java.util.Optional.of(mockEntity));
        when(mockEntity.getPoints()).thenReturn(list);

        assertThrows(LoadFunctionExecption.class, () ->
                controller.load(target, id, model, session)
        );
    }

    @Test
    public void testLoadFunctionNotFound() {
        String target = "test";
        Long id = 1L;

        when(mathFunctionsRepository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThrows(NullPointerException.class, () ->
                controller.load(target, id, model, session)
        );
    }

    @Test
    public void testSaveSuccess() {
        String saveTarget = "test";
        String funcName = "Sample Function";

        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);
        TabulatedFunction mockFunction = mock(TabulatedFunction.class);
        MathFunctionsService mathFunctionsService = mock(MathFunctionsService.class);
        PointService pointService = mock(PointService.class);

        when(session.getAttribute(saveTarget + "Func")).thenReturn(mockFunction);
        when(mockFunction.getCount()).thenReturn(2);
        when(mockFunction.leftBound()).thenReturn(0.0);
        when(mockFunction.rightBound()).thenReturn(10.0);
        when(mockFunction.getX(0)).thenReturn(1.0);
        when(mockFunction.getY(0)).thenReturn(2.0);
        when(mockFunction.getX(1)).thenReturn(3.0);
        when(mockFunction.getY(1)).thenReturn(4.0);

        MathFunctionsDTO savedFunction = new MathFunctionsDTO();
        savedFunction.setId(100L);
        when(mathFunctionsService.create(any())).thenReturn(savedFunction);

        LoadAndSaveControllers controller = new LoadAndSaveControllers(mathFunctionsService,pointService);
        String result = controller.save(saveTarget, funcName, model, session);

        verify(mathFunctionsService).create(any());
        verify(pointService, times(2)).create(any());
        verify(session).getAttribute(saveTarget + "Func");

        assertEquals("redirect:/tabulated-operations", result);
    }

    @Test
    public void testSaveThrowsExceptionWhenFunctionIsEmpty() {
        String saveTarget = "test";
        String funcName = "Sample Function";

        HttpSession session = mock(HttpSession.class);

        when(session.getAttribute(saveTarget + "Func")).thenReturn(null);

        LoadAndSaveControllers controller = new LoadAndSaveControllers(mathFunctionsService,pointService);

        assertThrows(IllegalArgumentException.class, () ->
                controller.save(saveTarget, funcName, null, session)
        );
    }
}