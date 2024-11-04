package sql.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sql.DTO.ResultProcessDTO;
import sql.service.ResultProcessService;

public class ResultProcessController {

    private ResultProcessService resultProcessService;

    @Autowired
    public ResultProcessController(ResultProcessService resultProcessService) {
        this.resultProcessService = resultProcessService;
    }

    @PostMapping
    public ResponseEntity<ResultProcessDTO> create(@RequestBody ResultProcessDTO dto_obj){
        ResultProcessDTO response = resultProcessService.create(dto_obj);
        return ResponseEntity.ok(response);
    }

    //TODO
    @GetMapping("/{id}")
    public ResponseEntity<ResultProcessDTO> read(@PathVariable long id){
        ResultProcessDTO response = resultProcessService.read(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultProcessDTO> update(@PathVariable Long id,@RequestBody ResultProcessDTO dto_obj){
        dto_obj.setId(id);
        ResultProcessDTO response = resultProcessService.update(dto_obj);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if(resultProcessService.getById(id) != null) {
            resultProcessService.delete(resultProcessService.getById(id));
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    
}
