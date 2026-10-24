package aplicacion;

import java.util.ArrayList;
import java.util.Scanner;
import modelo.Empleado;
import modelo.EmpleadoFijo;
import modelo.EmpleadoPorHora;

public class Main {

    private static Scanner entrada = new Scanner(System.in);

    private static String[] meses = {
        "Enero", "Febrero", "Marzo", "Abril",
        "Mayo", "Junio", "Julio", "Agosto",
        "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    private static ArrayList<Empleado> empleados =
            new ArrayList<>();

    private static int numeroMes;

    public static void main(String[] args) {

        System.out.println("CALCULO DE NOMINA");

        numeroMes = leerEntero(
                "Ingrese el mes a trabajar (1-12): ", 1, 12);

        int opcion;

        do {
            limpiarPantalla();

            System.out.println("NOMINA DEL MES: "
                    + meses[numeroMes - 1].toUpperCase());

            System.out.println();
            System.out.println("1 - Ingreso de nomina");
            System.out.println("2 - Impresion de nomina");
            System.out.println("3 - Salir");

            opcion = leerEntero(
                    "\nSeleccione una opcion: ", 1, 3);

            switch (opcion) {
                case 1:
                    ingresarNomina();
                    break;

                case 2:
                    imprimirNomina();
                    break;

                case 3:
                    System.out.println(
                            "\nGracias por utilizar el programa.");
                    break;
            }

        } while (opcion != 3);

        entrada.close();
    }

    // Muestra el submenu de ingreso de empleados
    private static void ingresarNomina() {

        int continuar;

        do {
            limpiarPantalla();

            System.out.println("NOMINA DEL MES: "
                    + meses[numeroMes - 1].toUpperCase());

            System.out.println("INGRESO DE EMPLEADO A NOMINA");
            System.out.println("Trabajadores actuales = "
                    + empleados.size());

            System.out.println();
            System.out.println("1 - Empleado fijo");
            System.out.println("2 - Empleado por hora");

            int tipo = leerEntero(
                    "\nSeleccione el tipo de empleado: ", 1, 2);

            limpiarPantalla();

            System.out.println("NOMINA DEL MES: "
                    + meses[numeroMes - 1].toUpperCase());

            if (tipo == 1) {
                registrarEmpleadoFijo();
            } else {
                registrarEmpleadoPorHora();
            }

            System.out.println();
            System.out.println("Empleado agregado correctamente.");
            System.out.println("Trabajadores actuales = "
                    + empleados.size());

            System.out.println();
            System.out.println("Desea ingresar otro empleado?");
            System.out.println("1 - Si, ingresar otro empleado");
            System.out.println("2 - Volver al menu principal");

            continuar = leerEntero(
                    "\nSeleccione una opcion: ", 1, 2);

        } while (continuar == 1);
    }

    // Registra un empleado con sueldo mensual
    private static void registrarEmpleadoFijo() {

        System.out.println("INGRESO DE EMPLEADO FIJO");
        System.out.println();

        String dpi = leerDpi();

        String nombre = leerNombre();

        double sueldo = leerDecimal(
                "Sueldo mensual (Q): ");

        double bonificacion = leerDecimal(
                "Bonificacion de este mes (Q): ");

        EmpleadoFijo empleado = new EmpleadoFijo(
                dpi, nombre, sueldo, bonificacion);

        empleados.add(empleado);

        System.out.printf(
                "\nPago de %s = Q %.2f%n",
                empleado.getNombre(),
                empleado.calcularPago());
    }

    // Registra un empleado por hora
    private static void registrarEmpleadoPorHora() {

        System.out.println("INGRESO DE EMPLEADO POR HORA");
        System.out.println();

        String dpi = leerDpi();

        String nombre = leerNombre();

        double pagoHora = leerDecimal(
                "Pago por hora (Q): ");

        double horas = leerDecimal(
                "Horas trabajadas este mes: ");

        EmpleadoPorHora empleado = new EmpleadoPorHora(
                dpi, nombre, pagoHora, horas);

        empleados.add(empleado);

        System.out.printf(
                "\nPago de %s = Q %.2f%n",
                empleado.getNombre(),
                empleado.calcularPago());
    }

    // Muestra el reporte de nomina
    private static void imprimirNomina() {

        limpiarPantalla();

        System.out.println("NOMINA DEL MES: "
                + meses[numeroMes - 1].toUpperCase());

        System.out.println();

        System.out.printf(
                "%-15s %-20s %-12s %12s%n",
                "DPI", "Nombre", "Tipo", "Pago (Q)");

        double total = 0;

        if (empleados.isEmpty()) {
            System.out.println(
                    "\nNo hay empleados registrados "
                    + "en la nomina de este mes");
        }

        for (Empleado empleado : empleados) {

            double pago = empleado.calcularPago();

            System.out.printf(
                    "%-15s %-20s %-12s %12.2f%n",
                    empleado.getDpi(),
                    empleado.getNombre(),
                    empleado.obtenerTipo(),
                    pago);

            total += pago;
        }

        System.out.println();
        System.out.println("Total de trabajadores: "
                + empleados.size());

        System.out.printf(
                "Total de nomina: Q %.2f%n", total);

        System.out.println();
        System.out.println(
                "Presione Enter para volver al menu principal...");

        entrada.nextLine();
    }

    // Lee un numero entero dentro de un rango
    private static int leerEntero(
            String mensaje, int minimo, int maximo) {

        while (true) {

            System.out.print(mensaje);

            String texto = entrada.nextLine().trim();

            try {
                int numero = Integer.parseInt(texto);

                if (numero >= minimo && numero <= maximo) {
                    return numero;
                }

                System.out.println(
                        "Error: ingrese una opcion entre "
                        + minimo + " y " + maximo + ".");

            } catch (NumberFormatException e) {
                System.out.println(
                        "Error: debe ingresar un numero entero.");
            }
        }
    }

    // Lee un DPI de exactamente 13 digitos
    private static String leerDpi() {

        while (true) {

            System.out.print("DPI: ");

            String dpi = entrada.nextLine().trim();

            if (dpi.matches("[0-9]{13}")) {
                return dpi;
            }

            System.out.println(
                    "Error: el DPI debe contener "
                    + "exactamente 13 digitos.");
        }
    }

    // Lee un nombre que no este vacio
    private static String leerNombre() {

        while (true) {

            System.out.print("Nombre: ");

            String nombre = entrada.nextLine().trim();

            if (!nombre.isEmpty()) {
                return nombre;
            }

            System.out.println(
                    "Error: el nombre no puede estar vacio.");
        }
    }

    // Lee numeros decimales mayores o iguales a cero
    private static double leerDecimal(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String texto = entrada.nextLine().trim();

            try {
                double numero = Double.parseDouble(texto);

                if (Double.isFinite(numero) && numero >= 0) {
                    return numero;
                }

                System.out.println(
                        "Error: ingrese un numero valido "
                        + "mayor o igual a cero.");

            } catch (NumberFormatException e) {
                System.out.println(
                        "Error: debe ingresar un numero valido.");
            }
        }
    }

    // Simula la limpieza de la consola
    private static void limpiarPantalla() {

        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }
} 