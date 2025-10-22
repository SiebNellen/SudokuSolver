public class Constraint implements Comparable<Constraint>{
    private Field a;
    private Field b;

    public Constraint(Field a, Field b) {
        this.a = a;
        this.b = b;
    }

    public Field getA() {
        return a;
    }

    public Field getB() {
        return b;
    }

    @Override
    public int compareTo(Constraint o) {
        if (o.getA().getValue() == o.getB().getValue()){
            return 1;
        }
        return 0;
    }

    public boolean revise(){
        if (a.getValue() == 0 && b.getValue() != 0){
            return a.removeFromDomain(b.getValue());
        }
        return false;
    }
}
