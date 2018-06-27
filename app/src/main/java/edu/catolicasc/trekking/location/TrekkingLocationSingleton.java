package edu.catolicasc.trekking.location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import android.location.Location;

public class TrekkingLocationSingleton {
    private static volatile TrekkingLocationSingleton instance;
    private static Object mutex = new Object();
    private float distanciaMaxima = 1.6f;

    public float getDistanciaMaxima() {
        return distanciaMaxima;
    }

    public void setDistanciaMaxima(float distanciaMaxima) {
        this.distanciaMaxima = distanciaMaxima;
    }

    private List<Location> locations = Collections.synchronizedList(new ArrayList<Location>());

    /**
     * Impede que seja usado o comando new na Classe TrekkingLocationSingleton
     */
    private TrekkingLocationSingleton() {
    }

    public static TrekkingLocationSingleton getInstance() {
        TrekkingLocationSingleton result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null)
                    instance = result = new TrekkingLocationSingleton();
            }
        }
        return result;
    }

    /**
     * Adiciona um novo ponto a lista de localizações
     * @param currentLocation
     */
    public synchronized void addLocation(Location currentLocation) {

        int tamanho = locations.size();
        System.out.println("Localizacao com tamanho " +  tamanho);
        if( tamanho == 0 ) {
            System.out.println("Adicionando o primeiro elemento");
            this.locations.add(currentLocation);
        } else {
            Location lastLocation = locations.get(tamanho - 1);

            if ( lastLocation.getLatitude() !=  currentLocation.getLatitude() ||
                  lastLocation.getLongitude() != currentLocation.getLongitude()) {
                float distance = lastLocation.distanceTo(currentLocation);
                System.out.println("[DISTANCIA] " + distance);
                if( distance > distanciaMaxima) {
                    System.out.println("A distancia entre as localizacoes esta dentro do esperado");
                    this.locations.add(currentLocation);
                }
            }
        }
    }

    /**
     * Retorna a lista de localizações
     * @return
     */
    public List<Location> getLocations() {
        return this.locations;
    }

    /**
     * Lista de pontos gravada no formato texto
     * @return String
     */
    public synchronized String getListOfLocationAsText() {
        StringBuffer buffer = new StringBuffer();
        synchronized (locations) {
            Iterator<Location> iterator = locations.iterator();
            while (iterator.hasNext()) {
                buffer.append(iterator.next().toString());
                buffer.append("\n");
            }
        }

        return  buffer.toString();
    }

    /**
     * Zera a lista de pontos. Depois da chamada tudo que existia foi perdido!!!
     * USE COM CUIDADO!!!
     */
    public synchronized void resetLocations() {
        this.locations = Collections.synchronizedList(new ArrayList<Location>());;
    }



}
