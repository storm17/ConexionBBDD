/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package turispain;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Kiko
 */
public class Conexion {
        
        
    
    private final String url = "jdbc:postgresql://localhost:5432/turispainfi";
    private final String user = "";
    private final String password = "";
    private static boolean status = false;
    
    Connection conn = null;
    
    public Connection connect() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        status = false;
        try {
            
            String usersql; 
            String passwordsql;
            
            Scanner in = new Scanner(System.in);
            System.out.print("Usuario: ");
            usersql = in.nextLine();
          
            System.out.print("Contraseña: ");
            passwordsql = in.nextLine();
            
            Class.forName("org.postgresql.Driver").newInstance();
            conn = DriverManager.getConnection(url, usersql, passwordsql);
            status = true;
            System.out.println("¡Conectado!");
            
        }
        
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        
        return conn;
    }
    
    public void disconnect(){
        
        try {
            
            conn.close();
            
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }   
}
    
    public static boolean getStatus(){
        
        return status;
              
        }
    
    public void getDatosClienteReserva (){
       
       try {
           
           Statement stmnt;
           stmnt = conn.createStatement();
           String query = "select distinct \"codigo_cliente\", \"NIF\", \"nombre_cliente\", apellidos, \"direccion_cliente\", \n" +
"\"codigo_postal\", \"poblacion\", \"telefono_cliente\", \"provincia_cliente\", \"codigo_reserva\", \n" +
"a.nombre, sum(\"precio_habitacion\"), count (\"codigo_reserva\"), \"codigo_habitacion\"\n" +
"from \"Alojamiento\" a\n" +
"left join \"Habitacion\" \n" +
"on a.nombre = \"nombre_Alojamiento\"    \n" +
"right join \"muchos_Reserva_tiene_muchos_Habitacion\"\n" +
"on \"codigo_habitacion\" = \"codigo_habitacion_Habitacion\"\n" +
"right join \"Reserva\"\n" +
"on \"codigo_reserva_Reserva\" = \"codigo_reserva\"\n" +
"right join \"Cliente\"\n" +
"on \"codigo_cliente_Cliente\" = codigo_cliente\n" +
"where \"codigo_cliente\" = (select \"codigo_cliente_Cliente\"\n" +
"                         from \"muchos_Reserva_tiene_muchos_Habitacion\"\n" +
"                          where \"codigo_cliente_Cliente\" = 345\n" +
"                          order by \"codigo_cliente_Cliente\" desc limit 1\n" +
"                         )\n" +
"group by a.nombre, \"NIF\", \"apellidos\", \"codigo_reserva\", \"provincia_cliente\", \n" +
"a.provincia, \"codigo_cliente\", \"precio_habitacion\", \"codigo_habitacion\"\n" +
"order by \"codigo_cliente\", \"codigo_reserva\", count";
           ResultSet rs = stmnt.executeQuery(query);
           
           System.out.println("Consulta 1. ");
           System.out.println("===========================");
           while (rs.next()){
               System.out.println(""+rs.getString("codigo_cliente"));
               System.out.println(""+rs.getString("NIF"));
               System.out.println(""+rs.getString("nombre_cliente"));
               System.out.println(""+rs.getString("apellidos"));
               System.out.println(""+rs.getString("direccion_cliente"));
               System.out.println(""+rs.getString("codigo_postal"));
               System.out.println(""+rs.getString("poblacion"));
               System.out.println(""+rs.getString("telefono_cliente"));
               System.out.println(""+rs.getString("provincia_cliente"));
               System.out.println(""+rs.getString("codigo_reserva"));
               System.out.println(""+rs.getString("nombre"));
               System.out.println(""+rs.getString("sum"));
               System.out.println(""+rs.getString("count"));
               System.out.println(""+rs.getString("codigo_habitacion"));
               System.out.println("-----------------------------------");
           }
       }
       catch (SQLException ex) { System.out.println(ex.getMessage()); }

}
    
        public void getPorcentajeTransferencia(){
        
        try {
           
           Statement stmnt;
           stmnt = conn.createStatement();
           String query = "select \"tipo_pago\", count (\"tipo_pago\") * 100 / (select count(*) from \"Factura\") as Porcentaje \n" +
"from \"Factura\"\n" +
"where tipo_pago = 'Transferencia'\n" +
"group by \"tipo_pago\"";
           ResultSet rs = stmnt.executeQuery(query);
           
           System.out.println("Consulta 2. Porcentaje de reservas pagadas por transferencia");
           System.out.println("===========================");
           while (rs.next()){
               System.out.println(""+rs.getString("tipo_pago"));
               System.out.println(""+rs.getString("Porcentaje"));
               System.out.println("-----------------------------------");
           }
       }
       catch (SQLException ex) { System.out.println(ex.getMessage()); }
        
        
    }
    
    
        public void getDatosClienteActividad (){
       
       try {
           
           Statement stmnt;
           stmnt = conn.createStatement();
           String query = "select codigo_cliente, \"NIF\", apellidos, nombre_cliente, direccion_cliente, codigo_postal, poblacion, provincia_cliente, \n" +
"telefono_cliente, count (\"codigo_reserva_Reserva\")\n" +
"from \"Cliente\" c\n" +
"left join \"Reserva\" r \n" +
"on c.codigo_cliente = \"codigo_cliente_Cliente\"\n" +
"left outer join \"muchos_Reserva_tiene_muchos_Actividadm\" m \n" +
"on \"codigo_reserva\" = \"codigo_reserva_Reserva\"\n" +
"group by \"codigo_cliente\"\n" +
"order by (count) desc limit 1";
           ResultSet rs = stmnt.executeQuery(query);
           
           System.out.println("Consulta 3. Cliente que mas actividades ha contratado");
           System.out.println("===========================");
           while (rs.next()){
               System.out.println(""+rs.getString("codigo_cliente"));
               System.out.println(""+rs.getString("NIF"));
               System.out.println(""+rs.getString("apellidos"));
               System.out.println(""+rs.getString("nombre_cliente"));
               System.out.println(""+rs.getString("direccion_cliente"));
               System.out.println(""+rs.getString("codigo_postal"));
               System.out.println(""+rs.getString("poblacion"));
               System.out.println(""+rs.getString("provincia_cliente"));
               System.out.println(""+rs.getString("telefono_cliente"));
               System.out.println("-----------------------------------");

           }
       }
       catch (SQLException ex) { System.out.println(ex.getMessage()); }

}
        
        public void getReservasActividad (){
       
       try {
           
           Statement stmnt;
           stmnt = conn.createStatement();
           String query = "select distinct \"nombre_actividad\", count (\"codigo_actividad\")\n" +
"from \"Actividadm\"\n" +
"inner join \"muchos_Reserva_tiene_muchos_Actividadm\" on \"codigo_actividad\" = \"codigo_actividad_Actividadm\"\n" +
"group by \"codigo_actividad\"\n" +
"order by count desc";
           ResultSet rs = stmnt.executeQuery(query);
           
           System.out.println("Consulta 4. Numero de reservas que tiene cada actividad");
           System.out.println("===========================");
           while (rs.next()){
               System.out.println(""+rs.getString("nombre_actividad"));
               System.out.println(""+rs.getString("count"));
               System.out.println("-----------------------------------");
           }
       }
       catch (SQLException ex) { System.out.println(ex.getMessage()); }

}
        
        public void getAlojActividad (){
       
       try {
           
           Statement stmnt;
           stmnt = conn.createStatement();
           String query = "select distinct a.nombre, \"nombre_actividad\", count (\"codigo_reserva_Reserva\")\n" +
"from \"Alojamiento\" a                                                                                \n" +
"left join \"Habitacion\" \n" +
"on a.nombre = \"nombre_Alojamiento\"  \n" +
"right join \"muchos_Reserva_tiene_muchos_Habitacion\"\n" +
"on \"codigo_habitacion\" = \"codigo_habitacion_Habitacion\"\n" +
"right join \"Reserva\"\n" +
"on \"codigo_reserva_Reserva\" = \"codigo_reserva\"\n" +
"left join \"muchos_Reserva_tiene_muchos_Actividadm\"\n" +
"on \"codigo_reserva\" = \"codigo_reserva_Reservam\"\n" +
"left join \"Actividadm\"\n" +
"on \"codigo_actividad_Actividadm\" = \"codigo_actividad\"\n" +
"group by \"nombre\", \"nombre_actividad\"\n" +
"order by \"nombre\" desc limit 3";
           ResultSet rs = stmnt.executeQuery(query);
           
           System.out.println("Consulta 5. Alojamiento con mas visitas y actividades que ofrece.");
           System.out.println("===========================");
           while (rs.next()){
               System.out.println(""+rs.getString("nombre"));
               System.out.println(""+rs.getString("nombre_actividad"));
               System.out.println(""+rs.getString("count"));
               System.out.println("-----------------------------------");
           }
       }
       catch (SQLException ex) { System.out.println(ex.getMessage()); }

}
        
        public void getVisitasProvincia (){
       
       try {
           
           Statement stmnt;
           stmnt = conn.createStatement();
           String query = "select distinct \"provincia\", count (\"provincia\") \n" +
"from \"Alojamiento\" a\n" +
"left join \"Habitacion\" \n" +
"on a.nombre = \"nombre_Alojamiento\"    \n" +
"right join \"muchos_Reserva_tiene_muchos_Habitacion\"\n" +
"on \"codigo_habitacion\" = \"codigo_habitacion_Habitacion\"\n" +
"group by \"provincia\"\n" +
"order by count desc limit 1";
           ResultSet rs = stmnt.executeQuery(query);
           
           System.out.println("Consulta 6. Provincia que mas visitas ha recibido junto a las actividades que realiza");
           System.out.println("===========================");
           while (rs.next()){
               System.out.println(""+rs.getString("provincia"));
               System.out.println(""+rs.getString("count"));
               System.out.println("-----------------------------------");
           }
       }
       catch (SQLException ex) { System.out.println(ex.getMessage()); }

}

        public void getAlojamientosCliente (){
       
       try {
           
           Statement stmnt;
           stmnt = conn.createStatement();
           String query = "select distinct a.nombre, apellidos, \"codigo_reserva\"\n" +
"from \"Alojamiento\" a  \n" +
"left join \"Habitacion\" \n" +
"on a.nombre = \"nombre_Alojamiento\"    \n" +
"right join \"muchos_Reserva_tiene_muchos_Habitacion\"\n" +
"on \"codigo_habitacion\" = \"codigo_habitacion_Habitacion\"\n" +
"right join \"Reserva\"\n" +
"on \"codigo_reserva_Reserva\" = \"codigo_reserva\"\n" +
"right join \"Cliente\"\n" +
"on \"codigo_cliente_Cliente\" = codigo_cliente\n" +
"group by a.nombre, \"apellidos\", \"codigo_reserva_Reserva\", \"codigo_reserva\"\n" +
"order by \"apellidos\"";
           ResultSet rs = stmnt.executeQuery(query);
           
           System.out.println("Consulta 7. Alojamientos que visita cada cliente");
           System.out.println("===========================");
           while (rs.next()){
               System.out.println(""+rs.getString("nombre"));
               System.out.println(""+rs.getString("apellidos"));
               System.out.println(""+rs.getString("codigo_reserva"));
               System.out.println("-----------------------------------");
           }
       }
       catch (SQLException ex) { System.out.println(ex.getMessage()); }

}
        
        public void getClientesReservasComunidad (){
       
       try {
           
           Statement stmnt;
           stmnt = conn.createStatement();
           String query = "select distinct \"codigo_cliente\", \"NIF\", \"nombre_cliente\", apellidos, \"direccion_cliente\", \n" +
"\"codigo_postal\", \"poblacion\", \"telefono_cliente\", \"provincia_cliente\", a.provincia\n" +
"from \"Alojamiento\" a\n" +
"left join \"Habitacion\" \n" +
"on a.nombre = \"nombre_Alojamiento\"    \n" +
"right join \"muchos_Reserva_tiene_muchos_Habitacion\"\n" +
"on \"codigo_habitacion\" = \"codigo_habitacion_Habitacion\"\n" +
"right join \"Reserva\"\n" +
"on \"codigo_reserva_Reserva\" = \"codigo_reserva\"\n" +
"right join \"Cliente\"\n" +
"on \"codigo_cliente_Cliente\" = codigo_cliente\n" +
"inner join \"Alojamiento\"\n" +
"on a.provincia != \"provincia_cliente\"\n" +
"group by a.nombre, \"NIF\", \"apellidos\", \"codigo_reserva_Reserva\", \"codigo_reserva\", \"provincia_cliente\", \n" +
"a.provincia, \"codigo_cliente\"\n" +
"order by \"apellidos\"";
           ResultSet rs = stmnt.executeQuery(query);
           
           System.out.println("Consulta 8. Indicar los datos de los clientes que han realizado reservas "
                   + "en alojamientos fuera de su comunidad autónoma");
           System.out.println("===========================");
           while (rs.next()){
               System.out.println(""+rs.getString("codigo_cliente"));
               System.out.println(""+rs.getString("NIF"));
               System.out.println(""+rs.getString("nombre_cliente"));
               System.out.println(""+rs.getString("apellidos"));
               System.out.println(""+rs.getString("direccion_cliente"));
               System.out.println(""+rs.getString("codigo_postal"));
               System.out.println(""+rs.getString("poblacion"));
               System.out.println(""+rs.getString("telefono_cliente"));
               System.out.println(""+rs.getString("provincia_cliente"));
               System.out.println(""+rs.getString("provincia"));
               System.out.println("-----------------------------------");
           }
       }
       catch (SQLException ex) { System.out.println(ex.getMessage()); }

}
        

    }
    
    
    
