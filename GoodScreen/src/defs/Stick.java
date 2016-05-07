/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package defs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * represents a stick with speed. the stick is also a shape
 * every action not explained is a getter\setter\never built but needed because of inheretance
 * @author Ronen
 */
public class Stick implements Shape{
    private double length;
    private Vector2d dir;
    private double mass;
    private Ball playball;
    private Point2D loc;
    private Color stick;
    private Vector2d speed;
    private boolean colliding;
    /**
     * empty constructor
     * never used
     */
    public Stick() {
    }
    /**
     * builds a regular stick
     * @param length-the length of the stick
     * @param dir-the vector where it points- never enter a value- it will be ignored
     * @param mass-it's mass
     * @param playball-the ball it's pointing to
     * @param loc-the location of stick center
     * @param Stick - the stick color
     */
    public Stick(double length, Vector2d dir, double mass,Ball playball,Point2D loc,Color Stick) {
        this.length = length;
        this.loc=loc;
        this.dir = new Vector2d(loc , playball.getPos()).normalize();
        this.mass = mass;
        this.playball=playball;
        speed=new Vector2d(0, 0);
        this.stick=Stick;
        colliding=true;
    }
    /**
     * paints the stick to the given graphics
     * @param g2d -the graphics
     */
    public void paint(Graphics2D g2d){
        Stroke temp=g2d.getStroke();
        g2d.setColor(stick);
    try{
        if(loc.distance(playball.getPos())-playball.getRadius()>length/2){
            System.out.println(loc);
            g2d.fill(this);
        }
        else{
            double l=length;
            setLength(2*(loc.distance(playball.getPos())-playball.getRadius()));
            g2d.fill(this);
            colliding=true;
            setLength(l);
        }
    }catch(Exception e){
        if(loc==null){
            System.out.print("loc is null");
        }
    }
    }


    public Ball getPlayball() {
        return playball;
    }


    public Vector2d getDir() {
        return dir;
    }

    public double getLength() {
        return length;
    }

    public double getMass() {
        return mass;
    }

    public void setPlayball(Ball playball) {
        this.playball = playball;
    }

    public void setLoc(Point2D loc) {
        this.loc = loc;
    }

    public Point2D getLoc() {
        return loc;
    }

    public Color getStick() {
        return stick;
    }

    public void setStick(Color stick) {
        this.stick = stick;
    }



    public void setDir(Vector2d dir) {
        this.dir = dir;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle getBounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle2D getBounds2D() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(double d, double d1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(Point2D pd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean intersects(double d, double d1, double d2, double d3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean intersects(Rectangle2D rd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(double d, double d1, double d2, double d3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(Rectangle2D rd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * calculates how to draw the shape/this action is never called directly
     * @param at-the transform of the graphics
     * @return -the path to draw the shape
     */
    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        GeneralPath gp=new GeneralPath();
        Vector2d mak=new Vector2d(-dir.dY, dir.dX);
        Point2D first=mak.scale(2.5).add(loc);
        gp.moveTo(first.getX(),first.getY());
        Point2D next=dir.scale(length/2).subtract(first);
        gp.lineTo(next.getX(),next.getY() );
        next=mak.scale(5).subtract(next);
        gp.lineTo(next.getX(), next.getY());
        next=dir.scale(length).add(next);
        gp.lineTo(next.getX(), next.getY());
        next=mak.scale(5).add(next);
        gp.lineTo(next.getX(), next.getY());
        gp.closePath();
        return gp.getPathIterator(at);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double d) {
          GeneralPath gp=new GeneralPath();
        Vector2d mak=new Vector2d(-dir.dY, dir.dX);
        Point2D first=mak.scale(2.5).add(loc);
        gp.moveTo(first.getX(),first.getY());
        Point2D next=dir.scale(length/2).subtract(first);
        gp.lineTo(next.getX(),next.getY() );
        next=mak.scale(5).subtract(next);
        gp.lineTo(next.getX(), next.getY());
        next=dir.scale(length).add(next);
        gp.lineTo(next.getX(), next.getY());
        next=mak.scale(5).add(next);
        gp.lineTo(next.getX(), next.getY());
        gp.closePath();
        return gp.getPathIterator(at,d);
    }

    public Vector2d getSpeed() {
        return speed;
    }

    public void setSpeed(Vector2d speed) {
        this.speed = speed;
    }

    public void updatePos() {
        loc=new Point2D.Double(loc.getX()+speed.dX, loc.getY()+speed.dY);
    }

}
