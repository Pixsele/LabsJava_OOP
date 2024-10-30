package sql.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sql.DTO.ResultsByParametrDTO;
import sql.service.ResultByParametrService;

@RestController
@RequestMapping("/resultsbyparametr")
public class ResultsByParametrController {

    private final ResultByParametrService resultByParametrService;

    @Autowired
    public ResultsByParametrController(ResultByParametrService resultByParametrService) {
        this.resultByParametrService = resultByParametrService;
    }

    @PostMapping
    public ResponseEntity<ResultsByParametrDTO> create(@RequestBody ResultsByParametrDTO dto_obj){
        ResultsByParametrDTO response = resultByParametrService.create(dto_obj);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultsByParametrDTO> read(@PathVariable long id){
        ResultsByParametrDTO response = resultByParametrService.read(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultsByParametrDTO> update(@PathVariable long id, @RequestBody ResultsByParametrDTO dto_obj){
        dto_obj.setId(id);
        ResultsByParametrDTO response = resultByParametrService.update(dto_obj);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        if(resultByParametrService.getById(id) != null) {
            resultByParametrService.delete(resultByParametrService.getById(id));
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
