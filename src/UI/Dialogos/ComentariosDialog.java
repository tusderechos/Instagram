/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Dialogos;

/**
 *
 * @author USUARIO
 */

import service.ComentarioService;
import modelo.Comentario;
import UI.Componentes.BotonRedondeado;

import javax.swing.*;
import java.awt.*;

public class ComentariosDialog extends JDialog {
    
    public ComentariosDialog(JFrame padre, long postID, String autorpost, String usuarioactual) {
        super(padre, "Comentarios", true);
        
        ComentarioService service = new ComentarioService();
        
        setSize(400, 500);
        setLayout(new BorderLayout());
        
        DefaultListModel<String> modelo = new DefaultListModel<>();
        JList<String> lista = new JList<>(modelo);
        
        for (Comentario comentario : service.ListarComentarios(postID, autorpost)) {
            modelo.addElement(comentario.getUsuario() + ": " + comentario.getTexto());
        }
        
        JTextField input = new JTextField();
        
        BotonRedondeado btnenviar = new BotonRedondeado("Enviar");
        btnenviar.addActionListener(e -> {
            String texto = input.getText();
            
            if (texto.isBlank()) {
                return;
            }
            
            service.AgregarComentario(postID, autorpost, usuarioactual, texto);
            modelo.addElement(usuarioactual + ": " + texto);
            
            input.setText("");
        });
        
        JPanel abajo = new JPanel(new BorderLayout());
        abajo.add(input, BorderLayout.CENTER);
        abajo.add(btnenviar, BorderLayout.EAST);
        
        add(new JScrollPane(lista), BorderLayout.CENTER);
        add(abajo, BorderLayout.SOUTH);
    }
}
