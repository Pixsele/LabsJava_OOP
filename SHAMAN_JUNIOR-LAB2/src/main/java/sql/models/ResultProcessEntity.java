package sql.models;


import javax.persistence.*;

@Entity
@Table(name = "process_results")
public class ResultProcessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "process_id")
    private ProcessEntity processId;

    @Column(name = "result")
    private Double result;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProcessEntity getProcess() {
        return processId;
    }

    public void setProcess(ProcessEntity process) {
        this.processId = process;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
