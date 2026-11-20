package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Palette {
    private final int [] colors = new int[256];

    public Palette(List<ColorTargetDTO> targets) {
        // 1. Recorrer targets de la futura paleta
        for(int i=0; i<targets.size()-1;i++){
            ColorTargetDTO from = targets.get(i);
            ColorTargetDTO to  = targets.get(i+1);

            //Rango = B - A
            //Hablamos de temperatura
            int range = to.temperatura() - from.temperatura();
            for (int t = from.temperatura(); t <= to.temperatura(); t++) {
                double ratio = (double) (t - from.temperatura()) / range; // valor entre 0 y 1

                int alpha = mix(from.alpha(), to.alpha(), ratio);
                int red = mix(from.red(), to.red(),ratio);
                int green = mix(from.green(),to.green(),ratio);
                int blue = mix(from.blue(),to.blue(),ratio) ;

                colors[t] = new Color(red,green,blue,alpha).getRGB();
            }
        }
    }
    public int color(int temperatura){
        return colors[temperatura];
    }
    //Interpolación lineal
    private int mix(int v1, int v2, double ratio){
        return (int) Math.round(v1 + (v2-v1) * ratio);
    }

    public static  Palette createPalette(){
        List<ColorTargetDTO> targets = new ArrayList<>();
        targets.add(new ColorTargetDTO(0,0,0,0,0));//negro
        targets.add(new ColorTargetDTO(20,255,224,58,0));//rojo
        targets.add(new ColorTargetDTO(80,255,255,208,32));//amarillo
        targets.add(new ColorTargetDTO(255,255,255,255,255));//blanco

        return new Palette(targets);
    }
}