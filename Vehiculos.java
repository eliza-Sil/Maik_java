class Vehiculo {
    private String marca;
    private String modelo;
    private int velocidadMaxima;

    public Vehiculo(String marca, String modelo, int velocidadMaxima) {
        this.marca = marca;
        this.modelo = modelo;
        this.velocidadMaxima = velocidadMaxima;
    }

    public void mostrarInfo() {
        System.out.println("Vehículo: " + marca + " " + modelo +
                " | Velocidad Máxima: " + velocidadMaxima + " km/h");
    }

    public void acelerar() {
        System.out.println("El vehículo está acelerando...");
    }
}

class Carro extends Vehiculo {
    public Carro(String marca, String modelo, int velocidadMaxima) {
        super(marca, modelo, velocidadMaxima);
    }

    @Override
    public void acelerar() {
        System.out.println("El carro acelera suavemente en carretera.");
    }
}

class Moto extends Vehiculo {
    public Moto(String marca, String modelo, int velocidadMaxima) {
        super(marca, modelo, velocidadMaxima);
    }

    @Override
    public void acelerar() {
        System.out.println("La moto acelera rápidamente y hace un rugido fuerte.");
    }
}

class Camion extends Vehiculo {
    public Camion(String marca, String modelo, int velocidadMaxima) {
        super(marca, modelo, velocidadMaxima);
    }

    @Override
    public void acelerar() {
        System.out.println("El camión acelera lentamente porque transporta carga pesada.");
    }
}

public class Vehiculos {
    public static void main(String[] args) {
        // Crear objetos
        Vehiculo generico = new Vehiculo("Genérico", "ModeloX", 120);
        Carro carro = new Carro("Toyota", "Corolla", 180);
        Moto moto = new Moto("Yamaha", "R3", 200);
        Camion camion = new Camion("Volvo", "FH16", 140);

        // Guardarlos en un arreglo
        Vehiculo[] listaVehiculos = { generico, carro, moto, camion };

        // Recorrer arreglo y aplicar polimorfismo
        for (Vehiculo v : listaVehiculos) {
            v.mostrarInfo();
            v.acelerar();
            System.out.println("---------------------------");
        }
    }
}
