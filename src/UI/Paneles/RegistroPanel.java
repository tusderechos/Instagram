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
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(18, 18, 18, 18);
        
        PanelRedondeado card = new PanelRedondeado(UIConstantes.ARCO_CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(560, 720));
        card.setBackground(InstaColores.CARD);
        card.setBorder(new EmptyBorder(30, 34, 28, 34));
        
        JLabel lbltitulo = new JLabel("Crear Cuenta");
        lbltitulo.setFont(UIConstantes.LOGO_FONT);
        lbltitulo.setForeground(InstaColores.TEXTO_PRIMARIO);
        lbltitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblsubtitulo = new JLabel("Completa tus datos para registrarte");
        lblsubtitulo.setFont(UIConstantes.TEXTO_FONT);
        lblsubtitulo.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lblsubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        TxtNombre = CrearCampoTexto();
        CBGenero = CrearComboBox(new String[]{"M", "F"});
        TxtUsuario = CrearCampoTexto();
        TxtPassword = CrearCampoContrasena();
        SPEdad = CrearSpinnerEdad();
        CBTipoCuenta = CrearComboBox(new String[]{"PUBLICA", "PRIVADA"});
        TxtFotoPerfil = CrearCampoTexto();
        
        JPanel panelfoto = new JPanel(new BorderLayout(8, 0));
        panelfoto.setOpaque(false);
        panelfoto.setMaximumSize(new Dimension(340, UIConstantes.ALTURA_BOTON));
        panelfoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        BotonRedondeado btnexaminar = new BotonRedondeado("Examinar");
        btnexaminar.setPreferredSize(new Dimension(120, UIConstantes.ALTURA_BOTON));
        btnexaminar.setMaximumSize(new Dimension(120, UIConstantes.ALTURA_BOTON));
        btnexaminar.addActionListener(e -> SeleccionarFoto());
        
        panelfoto.add(TxtFotoPerfil, BorderLayout.CENTER);
        panelfoto.add(btnexaminar, BorderLayout.EAST);
        
        BotonRedondeado btncrear = new BotonRedondeado("Registrarme");
        btncrear.setPreferredSize(new Dimension(300, UIConstantes.ALTURA_BOTON));
        btncrear.setMaximumSize(new Dimension(300, UIConstantes.ALTURA_BOTON));
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
        LblEstado.setHorizontalAlignment(SwingConstants.CENTER);
        LblEstado.setMaximumSize(new Dimension(340, 20));
        
        card.add(Box.createVerticalStrut(2));
        card.add(lbltitulo);
        card.add(Box.createVerticalStrut(6));
        card.add(lblsubtitulo);
        card.add(Box.createVerticalStrut(20));
        
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
        card.add(btnvolver);
        card.add(Box.createVerticalStrut(5));
        card.add(LblEstado);
        
        add(card, gbc);
    }
    
    private JTextField CrearCampoTexto() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(340, UIConstantes.ALTURA_BOTON));
        field.setMaximumSize(new Dimension(340, UIConstantes.ALTURA_BOTON));
        field.setFont(UIConstantes.TEXTO_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        field.setBackground(InstaColores.INPUT_BG);
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        return field;
    }
    
    private JPasswordField CrearCampoContrasena() {
        JPasswordField field = new JPasswordField();
        field.setPreferredSize(new Dimension(340, UIConstantes.ALTURA_BOTON));
        field.setMaximumSize(new Dimension(340, UIConstantes.ALTURA_BOTON));
        field.setFont(UIConstantes.TEXTO_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        field.setBackground(InstaColores.INPUT_BG);
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        return field;
    }
    
    private JComboBox<String> CrearComboBox(String[] opciones) {
        JComboBox<String> combo = new JComboBox<>(opciones);
        combo.setFont(UIConstantes.TEXTO_FONT);
        combo.setPreferredSize(new Dimension(340, UIConstantes.ALTURA_BOTON));
        combo.setMaximumSize(new Dimension(340, UIConstantes.ALTURA_BOTON));
        combo.setBackground(InstaColores.INPUT_BG);
        combo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        return combo;
    }
    
    private JSpinner CrearSpinnerEdad() {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(18, 1, 120, 1));
        spinner.setPreferredSize(new Dimension(340, UIConstantes.ALTURA_BOTON));
        spinner.setMaximumSize(new Dimension(340, UIConstantes.ALTURA_BOTON));
        spinner.setFont(UIConstantes.TEXTO_FONT);
        spinner.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JComponent editor = spinner.getEditor();
        
        if (editor instanceof JSpinner.DefaultEditor defaultEditor) {
            defaultEditor.getTextField().setFont(UIConstantes.TEXTO_FONT);
            defaultEditor.getTextField().setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        }
        
        return spinner;
    }
    
    private JPanel CrearLabeledField(String label, JComponent componente) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(InstaColores.TEXTO_PRIMARIO);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(lbl);
        panel.add(Box.createVerticalStrut(5));
        panel.add(componente);
        
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
