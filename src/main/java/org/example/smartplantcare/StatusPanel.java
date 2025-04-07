package org.example.smartplantcare;

import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class StatusPanel {
    private final Canvas canvas;
    public StatusPanel(Canvas canvas){
        this.canvas=canvas;
    }

    public void drawStatus(){
        drawText(50,50,25,"Welcome, "); // add user name
        drawText(450,50,15,"Flowering Plant"); // selected plants from list should be shown here
        drawText(450,80,10,"Monstera Deliciosa");

        int x0=50,xd=130, sz1=20, sz2=40;
        drawText(x0,200,sz1,"Light");
        drawText(x0,250,sz2,"50%");
        drawText(x0+xd,200,sz1,"Temp");
        drawText(x0+xd,250,sz2,"15\u00B0C");
        drawText(x0+2*xd,200,sz1,"Water");
        drawText(x0+2*xd,250,sz2,"50%");
        drawText(x0+3*xd,200,sz1,"Humidity");
        drawText(x0+3*xd,250,sz2,"50%");
    }
    private void drawText(int x, int y,int sz, String s) {
        GraphicsContext gc=canvas.getGraphicsContext2D();
        gc.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD ,sz));
        gc.fillText(s, x,y);
    }


}
