package sql.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sql.DTO.MathFunctionsDTO;
import sql.DTO.PointDTO;
import sql.service.MathFunctionsService;
import sql.service.PointService;

import java.util.List;

@RestController
@RequestMapping("/point")
public class PointController {
    
    private PointService pointService;

    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @PostMapping
    public ResponseEntity<PointDTO> create(@RequestBody PointDTO dto_obj){
        PointDTO response = pointService.create(dto_obj);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PointDTO> read(@PathVariable long id){
        PointDTO response = pointService.read(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PointDTO> update(@PathVariable Long id,@RequestBody PointDTO dto_obj){
        dto_obj.setId(id);
        PointDTO response = pointService.update(dto_obj);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if(pointService.getById(id) != null) {
            pointService.delete(pointService.getById(id));
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<PointDTO>> findPointsByFunction(@RequestParam Long id) {
        List<PointDTO> points = pointService.findByFunction(id);

        if (points == null || points.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content если точек нет
        }

        return ResponseEntity.ok(points); // Возвращаем 200 OK с точками
    }
}
