package web.models;

import database.models.MathFunctionsEntity;
import database.models.PointEntity;
import database.repositories.MathFunctionsRepository;
import database.repositories.PointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class MathFunctionsEntityTest {

    @Autowired
    private MathFunctionsRepository mathFunctionsRepository;

    @Autowired
    private PointRepository pointRepository;

    @Test
    public void testSaveMathFunctionWithPoints() {
        // Создаем точки
        PointEntity point1 = new PointEntity();
        point1.setX(1.0);
        point1.setY(2.0);

        PointEntity point2 = new PointEntity();
        point2.setX(3.0);
        point2.setY(4.0);

        // Создаем MathFunctionsEntity
        MathFunctionsEntity mathFunction = new MathFunctionsEntity();
        mathFunction.setName("Function 1");
        mathFunction.setxFrom(0.0);
        mathFunction.setxTo(5.0);
        mathFunction.setCount(2);
        mathFunction.setPoints(Arrays.asList(point1, point2));

        // Сохраняем MathFunctionsEntity
        MathFunctionsEntity savedEntity = mathFunctionsRepository.save(mathFunction);

        // Проверяем, что объект сохранен и его связь с точками работает
        assertNotNull(savedEntity.getId(), "ID should not be null after save");
        assertEquals(2, savedEntity.getPoints().size(), "Points size should be 2");

        // Проверяем, что сами точки сохранены
        for (PointEntity point : savedEntity.getPoints()) {
            assertNotNull(point.getId(), "Point ID should not be null after save");
        }
    }
}