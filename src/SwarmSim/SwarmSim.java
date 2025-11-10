package SwarmSim;

import gui.GUISimulator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SwarmSim {
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(600, 600, Color.BLACK);

        int NbBoids = 30;
        SwarmBoard sim = new SwarmBoard(gui,NbBoids);
    }
}
