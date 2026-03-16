/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App;

/**
 *
 * @author USUARIO
 */

import Red.ServidorChatLauncher;
import UI.Frame.MainFrame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        ServidorChatLauncher.IniciarServidorSiNoExiste(5050);
        
        SwingUtilities.invokeLater(() -> {
            MainFrame main = new MainFrame();
            main.setVisible(true);
        });
    }
}
