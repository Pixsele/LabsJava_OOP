package sql.DTO;

import java.time.LocalDateTime;

public class ResultsByParametrDTO {
    private long id;
    private long functionId;
    private LocalDateTime creationTime;
    private double parametr;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(long functionId) {
        this.functionId = functionId;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public double getParametr() {
        return parametr;
    }

    public void setParametr(double parametr) {
        this.parametr = parametr;
    }
}
