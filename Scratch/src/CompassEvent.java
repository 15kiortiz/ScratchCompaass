import javax.swing.*;
import java.awt.*;
import java.util.EventObject;

public class CompassEvent extends EventObject {
    double xOrigin;
    double yOrigin;
    double xFinal;
    double yFinal;
    double radius;
    double diametro;
    boolean banderaMoverOrigen;
    boolean banderaMoverOrigen2;
    Graphics g;


    public CompassEvent(Object source, double xOrigin, double yOrigin, double xFinal, double yFinal, double radius, double diametro, Graphics g
    ,boolean banderaMoverOrigen, boolean banderaMoverOrigen2){
        super(source);

        this.xOrigin=xOrigin;
        this.yOrigin=yOrigin;
        this.xFinal=xFinal;
        this.yFinal=yFinal;
        this.radius=radius;
        this.diametro=diametro;
        this.g=g;
        this.banderaMoverOrigen=banderaMoverOrigen;
        this.banderaMoverOrigen2=banderaMoverOrigen2;

        JOptionPane.showMessageDialog(null,""+getxOrigin());
    }


    public double getxOrigin() {
        return xOrigin;
    }

    public void setxOrigin(double xOrigin) {
        this.xOrigin = xOrigin;
    }

    public double getyOrigin() {
        return yOrigin;
    }

    public void setyOrigin(double yOrigin) {
        this.yOrigin = yOrigin;
    }

    public double getxFinal() {
        return xFinal;
    }

    public void setxFinal(double xFinal) {
        this.xFinal = xFinal;
    }

    public double getyFinal() {
        return yFinal;
    }

    public void setyFinal(double yFinal) {
        this.yFinal = yFinal;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getDiametro() {
        return diametro;
    }

    public void setDiametro(double diametro) {
        this.diametro = diametro;
    }


    public Graphics getG() {
        return g;
    }

    public void setG(Graphics g) {
        this.g = g;
    }
    public boolean isBanderaMoverOrigen() {
        return banderaMoverOrigen;
    }

    public void setBanderaMoverOrigen(boolean banderaMoverOrigen) {
        this.banderaMoverOrigen = banderaMoverOrigen;
    }

    public boolean isBanderaMoverOrigen2() {
        return banderaMoverOrigen2;
    }

    public void setBanderaMoverOrigen2(boolean banderaMoverOrigen2) {
        this.banderaMoverOrigen2 = banderaMoverOrigen2;
    }
}
