package db.DTO;

public class PointDTO {
    private Long id;
    private Long function;
    private Double x;
    private Double y;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFunction() {
        return function;
    }

    public void setFunction(Long function) {
        this.function = function;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
