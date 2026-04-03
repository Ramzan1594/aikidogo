package be.technifutur.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapTarif {
    private Map<Integer, Tarif> mapTarif = new HashMap<>();

    public void addTarif(int id, Tarif tarif) {
        mapTarif.put(id, tarif);
    }
    public Map<Integer, Tarif> getMapTarif() {return mapTarif;}
    public Tarif getTarif(int id) {
        return mapTarif.get(id);
    }

    public Set<Integer> getTypes() {
        return mapTarif.keySet();
    }

    public void afficher() {
        int i = 1;
        for (int id : mapTarif.keySet()) {
            System.out.println(i++ + ". " + id);
        }
    }
}