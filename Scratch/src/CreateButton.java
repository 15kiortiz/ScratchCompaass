import javax.swing.*;
import java.awt.*;

public class CreateButton extends JFrame{

    private Color colorFondo;

    public CreateButton()
    {
        super("Compass Example");

        Container container = getContentPane();
        container.setLayout(new BoxLayout(container,BoxLayout.LINE_AXIS));

       //colorFondo = Color.black;
        //Dibujar dibujo = new Dibujar();
        Compass compass = new Compass();

        //compass.addCompassListener(dibujo);
        container.add(compass);


    }


    public static void main (String [] args)
    {
        CreateButton prog= new CreateButton();
        prog.setDefaultCloseOperation(EXIT_ON_CLOSE);
        prog.setSize(new Dimension(800,600));
        prog.setVisible(true);
    }
}

