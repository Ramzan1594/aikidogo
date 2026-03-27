package be.technifutur.Model;

public enum ETypeParticipant {
    MINEUR(0.5),
    NORMAL(1.0),
    ANIMATEUR(0.0);

    private final double coefficient;

    ETypeParticipant(double c) {
        this.coefficient = c;
    }

    //function qui retourne le coefficient pour le prix
    public double getCoefficient() {
        return coefficient;
    }

    @Override
    public String toString(){
        return switch(this){
            case MINEUR -> "MINEUR";
            case NORMAL -> "NORMAL";
            case ANIMATEUR -> "ANIMATEUR";
        };
    }

}
