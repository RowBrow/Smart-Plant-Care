package org.example.smartplantcare;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class StatusPanel {

    private final Canvas canvas;

    public StatusPanel(Canvas canvas){
        this.canvas = canvas;
    }

    public void drawStatus(float light, float temperature, float water, float humidity){
        GraphicsContext gc=canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        drawText(50,50,25,"Welcome, "); // add username
        drawText(450,50,17,"Flowering Plant"); // selected plants from list should be shown here
        drawText(450,80,15,"Monstera Deliciosa");

        int x0=20,xd=160, sz1=20, sz2=40;
        drawText(x0,200,sz1,"Light");
        drawText(x0,250,sz2,String.valueOf(light)+" %");
        drawText(x0+xd,200,sz1,"Temp");
        drawText(x0+xd,250,sz2,String.valueOf(temperature) +" °C");
        drawText(x0+2*xd,200,sz1,"Water");
        drawText(x0+2*xd,250,sz2,String.valueOf(water) + " %");
        drawText(x0+3*xd,200,sz1,"Humidity");
        drawText(x0+3*xd,250,sz2,String.valueOf(humidity) + " %");
    }
    private void drawText(int x, int y,int sz, String s) {
        GraphicsContext gc=canvas.getGraphicsContext2D();
        gc.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD ,sz));
        gc.fillText(s, x,y);
    }
}
