public enum Ficha {
    X,
    O;

    public Ficha siguiente() {
        return this == X ? O : X;
    }
}