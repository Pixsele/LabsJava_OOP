package sql.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sql.DTO.ResultDTO;
import sql.service.ResultService;

@RestController
@RequestMapping("/result")
public class ResultController {

    private final ResultService resultService;

    @Autowired
    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public ResponseEntity<ResultDTO> create(@RequestBody ResultDTO dto_obj){
        ResultDTO response = resultService.create(dto_obj);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO> read(@PathVariable long id){
        ResultDTO response = resultService.read(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDTO> update(@PathVariable long id, @RequestBody ResultDTO dto_obj){
        dto_obj.setId(id);
        ResultDTO response = resultService.update(dto_obj);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        if(resultService.getById(id) != null) {
            resultService.delete(resultService.getById(id));
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
