package sql.DTO;

public class ResultDTO {
    private long id;
    private long resultByParametr;
    private double resultValue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getResultByParametr() {
        return resultByParametr;
    }

    public void setResultByParametr(long resultByParametr) {
        this.resultByParametr = resultByParametr;
    }

    public double getResultValue() {
        return resultValue;
    }

    public void setResultValue(double resultValue) {
        this.resultValue = resultValue;
    }
}
