import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexionBD {
    Connection link;
    public String db= "test";
    public String url = "jdbc:mysql://localhost:3306/"+db+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public String user= "root";
    public String pass = "hola";

    public Connection Conectar()
    {
        link = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            link= DriverManager.getConnection(this.url,this.user,this.pass);

        }catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex);
        }
        return link;
    }

    //metodo de Insertar en la base de datos

    public int InstertarBase(double XOrigin, double YOrigin, double coordXFin, double coordYFin, double radius, double diameter)
    {

        int numRegs = 0;
        try {
            PreparedStatement stInsertar = link.prepareStatement(
                    "insert into compass(XInicial,yInicial,XFinal,YFinal,Radius,Diametro)"
                            + " values(?, ?, ?, ?, ?, ?)");

            stInsertar.setString(1, String.valueOf(XOrigin));
            stInsertar.setString(2, String.valueOf(YOrigin));
            stInsertar.setString(3, String.valueOf(coordXFin));
            stInsertar.setString(4, String.valueOf(coordYFin));
            stInsertar.setString(5, String.valueOf(radius));
            stInsertar.setString(6, String.valueOf(diameter));


            numRegs= stInsertar.executeUpdate();
        }
        catch(SQLException error){
            error.printStackTrace();
        }

        return numRegs;

    }



}
