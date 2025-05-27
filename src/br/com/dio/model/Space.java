package br.com.dio.model;

public class Space {

    private Integer actual;
    private Integer expected;
    private boolean fixed;


    public Space(final Integer expected, final boolean fixed) {
        this.expected = expected;
        this.fixed = fixed;
        if (fixed && expected != null){
            actual = expected;
        }
    }

    public Integer getActual() {
        return actual;
    }

    public void setActual(final Integer actual) {
        if (fixed) return;
        this.actual = actual;
    }

    public void clearSpace(){
        setActual(null);
    }

    public int getExpected() {
        return expected;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setExpected(int value) {
        this.expected = value;

    }

    public void setFixed(boolean b) {
        this.fixed = b;

    }
}
