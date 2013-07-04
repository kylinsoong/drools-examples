package org.jbpm.test.workitem.model;

public class MultiplierOperation {
    private Double op1;
    private Double op2;
    private Double result;

    public MultiplierOperation(Double op1, Double op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    public Double getOp1() {
        return op1;
    }

    public Double getOp2() {
        return op2;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MultiplierOperation{" + "op1=" + op1 + ", op2=" + op2 + ", result=" + result + '}';
    }

}
