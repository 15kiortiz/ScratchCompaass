import javax.swing.*;
import java.awt.*;

public class Dibujar extends JPanel implements CompassListener {

    @Override
    public void segundoPunto(CompassEvent evt)
    {

        Graphics g = getGraphics();

        JOptionPane.showMessageDialog(null,"XXX"+evt.getxOrigin());
        g.setColor(Color.BLUE);
        g.drawLine( (int) evt.getxOrigin(),(int) evt.yOrigin,(int) evt.getxFinal(),(int) evt.getyFinal());

        dibujarCirculo(g,evt.getxOrigin(),evt.getyOrigin(),evt.getRadius(),evt.getDiametro());

    }

    public void dibujarCirculo(Graphics g, double XOrigin, double YOrigin, double radius, double diameter)
    {
        g.setColor(Color.red);

        g.drawOval((int)(XOrigin-radius),(int)(YOrigin-radius),(int) diameter,(int) diameter);

    }
    @Override
    public void paintComponent(Graphics g) {

        //pinta el componente del color dado.
        g.setColor(Color.BLACK);
        //llena el componente respeto a la altura y andcho dado en dimension
        g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

        //No usado
        //graficarSeno(g,coordXOrig,coordYOrig);
    }
}
