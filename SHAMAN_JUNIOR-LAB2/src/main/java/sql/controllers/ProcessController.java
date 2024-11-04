package sql.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sql.DTO.ProcessDTO;
import sql.service.ProcessService;

@RestController
@RequestMapping("/process")
public class ProcessController {

    private ProcessService processService;

    @Autowired
    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }

    @PostMapping
    public ResponseEntity<ProcessDTO> create(@RequestBody ProcessDTO dto_obj){
        ProcessDTO response = processService.create(dto_obj);
        return ResponseEntity.ok(response);
    }

    //TODO
    @GetMapping("/{id}")
    public ResponseEntity<ProcessDTO> read(@PathVariable long id){
        ProcessDTO response = processService.read(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessDTO> update(@PathVariable Long id,@RequestBody ProcessDTO dto_obj){
        dto_obj.setId(id);
        ProcessDTO response = processService.update(dto_obj);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if(processService.getById(id) != null) {
            processService.delete(processService.getById(id));
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}