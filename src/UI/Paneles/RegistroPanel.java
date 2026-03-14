/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Paneles;

/**
 *
 * @author USUARIO
 */

import enums.TipoCuenta;
import service.UsuarioService;
import UI.Componentes.BotonRedondeado;
import UI.Componentes.PanelRedondeado;
import interfaces.NavigationListener;
import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class RegistroPanel extends JPanel {
    
    private final UsuarioService usuarioService;
    private final NavigationListener navigationListener;
    
    private JTextField TxtNombre;
    private JComboBox<String> CBGenero;
    private JTextField TxtUsuario;
    private JPasswordField TxtPassword;
    private JSpinner SPEdad;
    private JComboBox<String> CBTipoCuenta;
    private JTextField TxtFotoPerfil;
    private JLabel LblEstado;
    
    public RegistroPanel(NavigationListener navigationListener) {
        usuarioService = new UsuarioService();
        this.navigationListener = navigationListener;
        
        setLayout(new GridBagLayout());
        setBackground(InstaColores.FONDO);
        
        PanelRedondeado card = new PanelRedondeado(UIConstantes.ARCO_CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(430, 620));
        card.setBorder(new EmptyBorder(28, 28, 28, 28));
        
        JLabel lbltitulo = new JLabel("Crear Cuenta");
        lbltitulo.setFont(UIConstantes.TEXTO_FONT);
        lbltitulo.setForeground(InstaColores.TEXTO_PRIMARIO);
        lbltitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        TxtNombre = CrearCampoTexto();
        CBGenero = new JComboBox<>(new String[]{"M", "F"});
        TxtUsuario = CrearCampoTexto();
        TxtPassword = CrearCampoContrasena();
        SPEdad = new JSpinner(new SpinnerNumberModel(18, 1, 120, 1));
        CBTipoCuenta = new JComboBox<>(new String[]{"PUBLICA", "PRIVADA"});
        TxtFotoPerfil = CrearCampoTexto();
        
        JPanel panelfoto = new JPanel(new BorderLayout(8, 0));
        panelfoto.setOpaque(false);
        
        BotonRedondeado btnexaminar = new BotonRedondeado("Examinar");
        btnexaminar.setPreferredSize(new Dimension(120, 40));
        btnexaminar.addActionListener(e -> SeleccionarFoto());
        
        panelfoto.add(TxtFotoPerfil, BorderLayout.CENTER);
        panelfoto.add(btnexaminar, BorderLayout.EAST);
        
        BotonRedondeado btncrear = new BotonRedondeado("Registrarme");
        btncrear.setPreferredSize(new Dimension(300, 42));
        btncrear.setMaximumSize(new Dimension(300, 42));
        btncrear.setAlignmentX(Component.CENTER_ALIGNMENT);
        btncrear.addActionListener(e -> RegistrarUsuario());
        
        JButton btnvolver = new JButton("Volver a Login");
        btnvolver.setFont(UIConstantes.PEQUENO_FONT);
        btnvolver.setForeground(InstaColores.AZUL);
        btnvolver.setContentAreaFilled(false);
        btnvolver.setBorderPainted(false);
        btnvolver.setFocusPainted(false);
        btnvolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnvolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnvolver.addActionListener(e -> navigationListener.irALogin());
        
        LblEstado = new JLabel(" ");
        LblEstado.setFont(UIConstantes.PEQUENO_FONT);
        LblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(lbltitulo);
        card.add(Box.createVerticalStrut(22));
        card.add(CrearLabeledField("Nombre completo", TxtNombre));
        card.add(Box.createVerticalStrut(10));
        card.add(CrearLabeledField("Genero", CBGenero));
        card.add(Box.createVerticalStrut(10));
        card.add(CrearLabeledField("Usuario", TxtUsuario));
        card.add(Box.createVerticalStrut(10));
        card.add(CrearLabeledField("Contraseña", TxtPassword));
        card.add(Box.createVerticalStrut(10));
        card.add(CrearLabeledField("Edad", SPEdad));
        card.add(Box.createVerticalStrut(10));
        card.add(CrearLabeledField("Tipo de Cuenta", CBTipoCuenta));
        card.add(Box.createVerticalStrut(10));
        card.add(CrearLabeledField("Foto de Perfil (opcional)", panelfoto));
        card.add(Box.createVerticalStrut(18));
        card.add(btncrear);
        card.add(Box.createVerticalStrut(12));
        card.add(LblEstado);
        card.add(Box.createVerticalGlue());
        card.add(btnvolver);
        
        add(card);
    }
    
    private JTextField CrearCampoTexto() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(300, 40));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setFont(UIConstantes.TEXTO_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
    
        return field;
    }
    
    private JPasswordField CrearCampoContrasena() {
        JPasswordField field = new JPasswordField();
        field.setPreferredSize(new Dimension(300, 40));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setFont(UIConstantes.TEXTO_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
    
        return field;
    }
    
    private JPanel CrearLabeledField(String label, JComponent componente) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(0, 6));
        
        JLabel lbl = new JLabel(label);
        lbl.setFont(UIConstantes.PEQUENO_FONT);
        lbl.setForeground(InstaColores.TEXTO_SECUNDARIO);
        
        if (componente instanceof JComboBox<?>) {
            componente.setPreferredSize(new Dimension(300, 40));
            componente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        }
        
        if (componente instanceof JSpinner) {
            componente.setPreferredSize(new Dimension(300, 40));
            componente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        }
        
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(componente, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void SeleccionarFoto() {
        JFileChooser chooser = new JFileChooser();
        int resultado = chooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            TxtFotoPerfil.setText(file.getAbsolutePath());
        }
    }
    
    private void RegistrarUsuario() {
        String nombre = TxtNombre.getText().trim();
        String genero = (String) CBGenero.getSelectedItem();
        String usuario = TxtUsuario.getText().trim();
        String contrasena = new String(TxtPassword.getPassword());
        int edad = (Integer) SPEdad.getValue();
        String tipotexto = (String) CBTipoCuenta.getSelectedItem();
        String foto = TxtFotoPerfil.getText().trim();
        
        TipoCuenta tipocuenta = TipoCuenta.valueOf(tipotexto);
        
        boolean creado = usuarioService.RegistrarUsuario(nombre, genero, usuario, contrasena, edad, tipocuenta, foto);
        
        if (!creado) {
            LblEstado.setForeground(InstaColores.ERROR);
            LblEstado.setText("No se pudo registrar. Revisa tus datos");
            return;
        }
        
        LblEstado.setForeground(InstaColores.SUCCESS);
        LblEstado.setText("Cuenta creada con exito. Ahora inicia sesion");
        LimpiarCampos();
    }
    
    private void LimpiarCampos() {
        TxtNombre.setText("");
        CBGenero.setSelectedIndex(0);
        TxtUsuario.setText("");
        TxtPassword.setText("");
        SPEdad.setValue(18);
        CBTipoCuenta.setSelectedIndex(0);
        TxtFotoPerfil.setText("");
    }
}
