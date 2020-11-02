/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turispain;
import java.util.Scanner;

/**
 *
 * @author Kiko
 */
public class TuriSpain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // TODO code application logic here
        Conexion app = new Conexion();
        app.connect(); 
        
        if (Conexion.getStatus()){
            System.out.println("Consulta 1. Obtener los datos del cliente que ha realizado más reservas, "
                + "indicando los alojamientos que ha alquilado y el dinero que se ha gastado en cada uno de ellos.");
        System.out.println("|");
        System.out.println("Consulta 2. Mostrar el porcentaje de reservas pagadas por transferencia.");
        System.out.println("|");
        System.out.println("Consulta 3. Mostrar los datos del cliente que más actividades ha contratado");
        System.out.println("|");
        System.out.println("Consulta 4. Indicar el número de reservas que haya tenido cada una de las actividades.");
        System.out.println("|");
        System.out.println("Consulta 5. Determinar el alojamiento con más visitas que ha recibido junto con "
                + "las actividades multiaventura que realiza.");
        System.out.println("|");
        System.out.println("Consulta 6. Determinar la provincia que más visitas ha recibido de entre "
                + "todos los clientes que han realizado reservas.");
        System.out.println("|");
        System.out.println("Consulta 7. Indicar los alojamientos que ha visitado cada cliente.");
        System.out.println("|");
        System.out.println("Consulta 8. Indicar los datos de los clientes que han realizado reservas."
                + "en alojamientos fuera de su comunidad autónoma.");
        System.out.println("|");
        System.out.println("9. Desconectarse de la base de datos.");
        
        int opcion; 
        Scanner in = new Scanner(System.in);
        System.out.print("Bienvenido. Elige la consulta que deseas realizar: ");
        opcion = in.nextInt();
        System.out.println("");
        
        switch (opcion)
        {
            case 1: opcion = 1;
            app.getDatosClienteReserva();
            break;
            
            case 2: opcion = 2;
            app.getPorcentajeTransferencia();
            break;
            
            case 3: opcion = 3;
            app.getDatosClienteActividad();
            break;
            
            case 4: opcion = 4;
            app.getReservasActividad();
            break;
            
            case 5: opcion = 5;
            app.getAlojActividad();
            break;
            
            case 6: opcion = 6;
            app.getVisitasProvincia();
            break;
            
            case 7: opcion = 7;
            app.getAlojamientosCliente();
            break;
            
            case 8: opcion = 8;
            app.getClientesReservasComunidad();
            break;
            
            case 9: opcion = 9;
            app.disconnect();
            System.out.println("Se ha desconectado. Hasta la próxima.");
            break;
        }
        }
        
        else {
            System.out.println("USUARIO O CONTRASEÑA INCORRECTOS");
            app.disconnect();
            
        }        
    
    }
    
}

