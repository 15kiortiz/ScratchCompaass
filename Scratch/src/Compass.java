import com.sun.org.apache.xerces.internal.parsers.CachingParserPool;
import com.sun.xml.internal.bind.XmlAccessorFactory;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Time;
import java.util.Vector;

public class Compass extends JPanel implements Serializable{

    float gradRad;
    float SinGrad;
    int XActual, YActual, factorX = 1, factorY = 40;
    boolean banderaMoverOrigen = true;
    boolean banderaMoverOrigen2= true;
    private Color colorFondo;
    private int coordXOrig = 100, coordYOrig=100;
    private double XOrigin, YOrigin;
    private double coordXFin =0;
    private double coordYFin =0;
    private double XDistance=0, YDistance=0;
    private double TotalDistance=0;
    private double diameter=0;
    private double radius=0;
    private String resUsuario;
    private int resCon;
    ConexionBD MySql = new ConexionBD();
    Connection bdCon = null;
    private Vector<CompassListener> vectorCompass = new Vector<>();

    public Compass()
    {
        //colorFondo = Color.BLACK;
        this.setPreferredSize(new Dimension(600, 600));

        //Creacion de oyentes
        ControlaorRaton oyenteRaton = new ControlaorRaton();
        this.addMouseListener(oyenteRaton);
        this.addMouseMotionListener(oyenteRaton);

        //Verificar Si se conecto la base de datos
        bdCon = MySql.Conectar();
        if(bdCon != null)
        {
            JOptionPane.showMessageDialog(null, "Connected");
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        //pinta el componente del color dado.
        g.setColor(colorFondo);
        //llena el componente respeto a la altura y andcho dado en dimension
        g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

        //No usado
        //graficarSeno(g,coordXOrig,coordYOrig);
    }


    public void graficarSeno(Graphics g, int x, int y) {

        //No usado
        //g.setColor(Color.blue);
        /*
        for (int grad = 0; grad < 361; grad++) {
            gradRad = (float) Math.toRadians(grad);
            SinGrad = (float) Math.sin(gradRad);


            //Calcular coordenadas de pantalla
            XActual = x + (grad * factorX);
            YActual = (int) (y - (SinGrad * factorY));
           // g.fillRect(XActual, YActual, 5, 5);
        }*/
    }
    public synchronized void addCompassListener(CompassListener listener){
        vectorCompass.addElement(listener);
    }

    public class ControlaorRaton extends MouseAdapter
            implements MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            Graphics g = getGraphics();
            //super.mouseClicked(e);

            //Condicion del click "Click derecho"
            if((e.getButton()==MouseEvent.BUTTON3 ))
            {

                banderaMoverOrigen= false;
                //Toma la coordenadas del click y lo alamacena respecto X y Y.
                XOrigin= e.getX();
                YOrigin= e.getY();

                //Imprime las coordenadas y nuevo valor de bandera
                System.out.println("Bandera == "+banderaMoverOrigen+
                        "\n Coord X == "+XOrigin+
                        "\n Coord Y== "+YOrigin);

                //Muestra en pantalla las coordenadas de X y Y en el dado click derecho
                JOptionPane.showMessageDialog(null,"Coord x=="+XOrigin+
                        "Coord y== "+YOrigin);
                //graphicarCirculo(g);
            }

            //Condicion  del "click izquierdo"
            if((e.getButton()== MouseEvent.BUTTON1))
            {
                //Convierte bandera en false
                banderaMoverOrigen2= false;
                //Guarda las coordenadas de dicho click
                coordXFin= e.getX();
                coordYFin= e.getY();

                System.out.println("Bandera == "+banderaMoverOrigen2+
                        "\n Coord X Final == "+coordXFin+
                        "\n Coord Y Final== "+coordYFin);

                //Muestra en pantalla las coordenadas al hacer el click izquierdo
                JOptionPane.showMessageDialog(null,"X Final ==" +coordXFin+
                        "Y Final== " +coordYFin);
            }

            //Condicion de pixeles (coordenadas)
            //validar si la coordenada X del click derecho fue mayor la click izquierdo
            if(XOrigin>coordXFin)
            {
                //Resta si es mayor
                XDistance= XOrigin-coordXFin;
            }
            //validar si la coordenada X del click izquierdo fue mayor la click derecho
            else if(coordXFin>XOrigin)
            {
                //Resta si es mayor
                XDistance= coordXFin-XOrigin;
            }

            //Despues de hacer la validacion, se eleva al cuadrado
            XDistance=Math.pow(XDistance,2);

            //validar si la coordenada Y del click derecho fue mayor la click izquierdo
            if(YOrigin>coordYFin)
            {
                //Resta si es mayor
                YDistance= YOrigin-coordYFin;
            }
            //validar si la coordenada Y del click izquierdo fue mayor la click derecho
            else if (coordYFin>YOrigin)
            {
                //Resta si es mayor
                YDistance=coordYFin-YOrigin;
            }


            //Despues de hacer la validacion, se eleva al cuadrado
            YDistance= Math.pow(YDistance,2);

            //calcular distancia y encontrar radio que seria "Hypotenuse"
            TotalDistance=(XDistance+YDistance);
            radius=Math.sqrt(TotalDistance);

            //Calcular Diametro
            diameter=(radius*2);


            //Validacion de clicks
            if(banderaMoverOrigen==true && banderaMoverOrigen2==false)
            {
                JOptionPane.showMessageDialog(null,"Solo Marcaste Punto Final");
                banderaMoverOrigen2=true;
            }
            //Valida si las los clicks fueron hechos.
            else if(banderaMoverOrigen2== false &&banderaMoverOrigen==false)
            {

                //Creacion de Objeto de clase Compass Event y mando parametros
                CompassEvent evento = new CompassEvent(Compass.this,XOrigin,YOrigin,coordXFin,coordYFin,radius,diameter, g,banderaMoverOrigen,banderaMoverOrigen2);
                //Inserta en ConexionBD en el metodo de InsertarBase

                int nRegs= MySql.InstertarBase(XOrigin,YOrigin,coordXFin,coordYFin,radius,diameter);
                JOptionPane.showMessageDialog(null," "+nRegs);
                System.out.println("Núm de registro afectados: " + nRegs);


                //Respuesta de Usuario
                resCon= 0;

                while (resCon<1 || resCon>2)
                {
                    //Respuesta de Usuario
                    resUsuario= JOptionPane.showInputDialog("Ingrese 1 si quiere el circulo normal \n  ingrese 2 si lo quiere relleno");
                    resCon =Integer.parseInt(resUsuario);
                }
                //resUsuario= JOptionPane.showInputDialog("Ingrese 0 si quiere el circulo normal \n  ingrese 1 si lo quiere relleno");
                //resCon =Integer.parseInt(resUsuario);

                //Decision con un switch
                switch (resCon){
                    case 1:
                        graficarLinea(g, XOrigin, YOrigin, coordXFin, coordYFin);
                        circulo(g);
                        break;
                    case 2: circulo(g);
                        break;
                }
                banderaMoverOrigen2=true;
                banderaMoverOrigen=true;
            }

        }
        //Metodo para dibujar Linea
        public void graficarLinea(Graphics g, double x1, double y1, double x2, double y2) {
            g.setColor(Color.ORANGE);
            g.drawLine( (int) x1,(int) y1,(int) x2,(int) y2);


        }

        //Metodo para dibujar el circulo
        public void circulo(Graphics g)
        {
            //Dibujar el circulo blanco
            g.setColor(Color.white);

            //Valdiar si la respuesa fue 1
            if (banderaMoverOrigen==false && banderaMoverOrigen2 == false &&resCon==1)
            {

                //g.fillOval((int)(XOrigin-radius),(int)(YOrigin-radius),(int) diameter,(int) diameter);
                g.drawOval((int)(XOrigin-radius),(int)(YOrigin-radius),(int) diameter,(int) diameter);
                try
                {
                    //Hacer que el circulo no se borre antes de 2 segundos
                    Thread.sleep(2000);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
            }
            //Validar si la respuesta fue 2
            if (banderaMoverOrigen==false && banderaMoverOrigen2 == false &&resCon==2)
            {
                g.fillOval((int)(XOrigin-radius),(int)(YOrigin-radius),(int) diameter,(int) diameter);
                //g.drawOval((int)(XOrigin-radius),(int)(YOrigin-radius),(int) diameter,(int) diameter);
                //repaint();
                try
                {
                    //Hacer que el circulo no se borre despues de 2 segundos
                    Thread.sleep(2000);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
            }

            //Volver Banderas true para poder volver a dibujar, sino lo agarrara como false
            banderaMoverOrigen2= true;
            banderaMoverOrigen= true;
        }


        @Override
        public void mouseMoved(MouseEvent e) {
            // super.mouseMoved(e);
            //Mientras el mouse se este moviendo, marcara como true y agarara dichas coordenadas
            if(banderaMoverOrigen)
            {
                coordYOrig= e.getY();
                coordXOrig = e.getX();
                //repaint();
            }
            System.out.println("Coord = ("+ e.getX()+"," +e.getY()
                    +") bandera== "+banderaMoverOrigen);
        }
    }




    public void InsertarDatos()
    {

    }
    public Color getColorFondo() {
        return colorFondo;
    }

    public void setColorFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
        // this.setBackground(colorFondo);
        this.repaint();
    }


    public void setCoordYOrig(int coordYOrig) {
        this.coordYOrig = coordYOrig;
    }

    public void setCoordXOrig(int coordXOrig) {

        this.coordXOrig = coordXOrig;
    }

    public int getCoordYOrig() {

        return coordYOrig;
    }

    public int getCoordXOrig() {

        return coordXOrig;
    }

    public boolean isBanderaMoverOrigen2() {
        return banderaMoverOrigen2;
    }

    public void setBanderaMoverOrigen2(boolean banderaMoverOrigen2) {
        this.banderaMoverOrigen2 = banderaMoverOrigen2;
    }

    public double getCoordXFin() {
        return coordXFin;
    }

    public void setCoordXFin(int coordXFin) {
        this.coordXFin = coordXFin;
    }

    public double getCoordYFin() {
        return coordYFin;
    }

    public void setCoordYFin(int coordYFin) {
        this.coordYFin = coordYFin;
    }

    public double getXDistance() {
        return XDistance;
    }

    public void setXDistance(int XDistance) {
        this.XDistance = XDistance;
    }

    public double getYDistance() {
        return YDistance;
    }

    public void setYDistance(int YDistance) {
        this.YDistance = YDistance;
    }

    public double getTotalDistance() {
        return TotalDistance;
    }

    public void setTotalDistance(int totalDistance) {
        TotalDistance = totalDistance;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
