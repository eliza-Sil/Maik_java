public class CuentaBancaria {
    // Atributos
    private String titular;
    private String numeroCuenta;
    private double saldo;

    // Constructor
    public CuentaBancaria(String titular, String numeroCuenta, double saldoInicial) {
        this.titular = titular;
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
    }

    // Método para mostrar los datos de la cuenta
    public void mostrarDatos() {
        System.out.println("Titular: " + titular);
        System.out.println("Número de cuenta: " + numeroCuenta);
        System.out.println("Saldo actual: $" + saldo);
    }

    // Método para depositar dinero
    public void depositar(double cantidad) {
        if (cantidad > 0) {
            saldo += cantidad;
            System.out.println("Depósito exitoso. Nuevo saldo: $" + saldo);
        } else {
            System.out.println("Error: La cantidad a depositar debe ser positiva");
        }
    }

    // Método para retirar dinero
    public void retirar(double cantidad) {
        if (cantidad > 0) {
            if (saldo >= cantidad) {
                saldo -= cantidad;
                System.out.println("Retiro exitoso. Nuevo saldo: $" + saldo);
            } else {
                System.out.println("Error: Saldo insuficiente");
            }
        } else {
            System.out.println("Error: La cantidad a retirar debe ser positiva");
        }
    }

    // Método para transferir dinero a otra cuenta
    public void transferir(CuentaBancaria destino, double cantidad) {
        if (cantidad > 0) {
            if (this.saldo >= cantidad) {
                this.saldo -= cantidad;
                destino.saldo += cantidad;
                System.out.println("Transferencia exitosa de $" + cantidad + " a " + destino.titular);
            } else {
                System.out.println("Error: Saldo insuficiente para transferencia");
            }
        } else {
            System.out.println("Error: La cantidad a transferir debe ser positiva");
        }
    }

    // Método principal para probar la clase
    public static void main(String[] args) {
        CuentaBancaria cuenta1 = new CuentaBancaria("Juan Pérez", "123456789", 1000.0);
        CuentaBancaria cuenta2 = new CuentaBancaria("María López", "987654321", 1000.0);

        // Mostrar datos iniciales
        System.out.println("--- Cuenta 1 ---");
        cuenta1.mostrarDatos();
        System.out.println("\n--- Cuenta 2 ---");
        cuenta2.mostrarDatos();

        // Realizar operaciones
        System.out.println("\nOperaciones:");
        cuenta1.depositar(200.0);
        cuenta1.retirar(100.0);
        cuenta1.transferir(cuenta2, 300.0);

        // Mostrar datos después de las operaciones
        System.out.println("\n--- Cuenta 1 después de operaciones ---");
        cuenta1.mostrarDatos();
        System.out.println("\n--- Cuenta 2 después de operaciones ---");
        cuenta2.mostrarDatos();
    }
}
