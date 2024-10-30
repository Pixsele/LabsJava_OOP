package sql.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sql.DTO.MathFunctionsDTO;
import sql.service.MathFunctionsService;

import java.util.List;

@RestController
@RequestMapping("/functions")
public class MathFunctionsContoreller {

    private final MathFunctionsService mathFunctionsService;

    @Autowired
    public MathFunctionsContoreller(MathFunctionsService mathFunctionsService) {
        this.mathFunctionsService = mathFunctionsService;
    }

    @PostMapping
    public ResponseEntity<MathFunctionsDTO> create(@RequestBody MathFunctionsDTO dto_obj){
        MathFunctionsDTO response = mathFunctionsService.create(dto_obj);
        return ResponseEntity.ok(response);
    }

    //TODO
    @GetMapping("/{id}")
    public ResponseEntity<MathFunctionsDTO> read(@PathVariable long id){
        MathFunctionsDTO response = mathFunctionsService.read(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MathFunctionsDTO> update(@PathVariable Long id,@RequestBody MathFunctionsDTO dto_obj){
        dto_obj.setId(id);
        MathFunctionsDTO response = mathFunctionsService.update(dto_obj);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if(mathFunctionsService.getById(id) != null) {
            mathFunctionsService.delete(mathFunctionsService.getById(id));
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<MathFunctionsDTO>> searchByName(@RequestParam String name){
            List<MathFunctionsDTO> response = mathFunctionsService.getByName(name);
            return ResponseEntity.ok(response);
    }
}
