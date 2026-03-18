/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Componentes;

/**
 *
 * @author USUARIO
 */

import UI.Styles.InstaColores;
import UI.Styles.UIConstantes;
import UI.Utils.FileUtils;
import enums.TipoCuenta;
import enums.TipoMedia;
import modelo.Usuario;
import service.UsuarioService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class EditarPerfilDialog extends JDialog {
    
    private final UsuarioService usuarioService;
    private final Usuario UsuarioOriginal;
    
    private JTextField TxtNombre;
    private JPasswordField TxtContrasena;
    private JSpinner SPEdad;
    private JComboBox<String> CBTipoCuenta;
    private JTextField TxtFotoPerfil;
    private JLabel LblEstado;
    
    private String RutaFotoSeleccionada;
    private boolean Guardado;
    
    public EditarPerfilDialog(Frame padre, Usuario usuario) {
        super(padre, "Editar perfil", true);
        UsuarioOriginal = usuario;
        usuarioService = new UsuarioService();
        RutaFotoSeleccionada = usuario != null ? usuario.getFotoPerfil() : "";
        Guardado = false;
        
        setSize(720, 680);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());
        getContentPane().setBackground(InstaColores.FONDO);
        
        PanelRedondeado card = new PanelRedondeado(UIConstantes.ARCO_CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(InstaColores.CARD);
        card.setBorder(new EmptyBorder(24, 28, 24, 28));
        
        JLabel lbltitulo = new JLabel("Editar perfil");
        lbltitulo.setFont(UIConstantes.TITULO_FONT);
        lbltitulo.setForeground(InstaColores.TEXTO_PRIMARIO);
        lbltitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblsubtitulo = new JLabel("Actualiza la informacion de tu cuenta");
        lblsubtitulo.setFont(UIConstantes.TEXTO_FONT);
        lblsubtitulo.setForeground(InstaColores.TEXTO_SECUNDARIO);
        lblsubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        TxtNombre = CrearCampoTexto();
        TxtContrasena = CrearCampoContrasena();
        SPEdad = CrearSpinnerEdad();
        CBTipoCuenta = CrearComboBox(new String[]{"PUBLICA", "PRIVADA"});
        TxtFotoPerfil = CrearCampoTexto();
        TxtFotoPerfil.setEditable(false);
        
        if (UsuarioOriginal != null) {
            TxtNombre.setText(UsuarioOriginal.getNombreCompleto());
            TxtContrasena.setText(UsuarioOriginal.getContrasena());
            SPEdad.setValue(UsuarioOriginal.getEdad());
            CBTipoCuenta.setSelectedItem(UsuarioOriginal.getTipoCuenta().name());
            TxtFotoPerfil.setText(UsuarioOriginal.getFotoPerfil());
        }
        
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 12, 0);
        
        formulario.add(CrearLabeledField("Nombre completo", TxtNombre), gbc);
        
        gbc.gridy++;
        formulario.add(CrearLabeledField("Contraseña", TxtContrasena), gbc);
        
        gbc.gridy++;
        JPanel filaedadcuenta = new JPanel(new GridLayout(1, 2, 12, 0));
        filaedadcuenta.setOpaque(false);
        filaedadcuenta.add(CrearLabeledField("Edad", SPEdad));
        filaedadcuenta.add(CrearLabeledField("Tipo de cuenta", CBTipoCuenta));
        formulario.add(filaedadcuenta, gbc);
        
        gbc.gridy++;
        formulario.add(CrearCampoFoto(), gbc);
        
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        acciones.setOpaque(false);
        
        BotonRedondeado btnguardar = new BotonRedondeado("Guardar");
        btnguardar.setPreferredSize(new Dimension(130, UIConstantes.ALTURA_BOTON));
        btnguardar.addActionListener(e -> GuardarCambios());
        
        JButton btncancelar = new JButton("Cancelar");
        btncancelar.setFocusPainted(false);
        btncancelar.setFont(UIConstantes.TEXTO_FONT);
        btncancelar.setForeground(InstaColores.TEXTO_PRIMARIO);
        btncancelar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER_SUAVE), BorderFactory.createEmptyBorder(8, 14, 8, 14)));
        btncancelar.setContentAreaFilled(true);
        btncancelar.setBackground(InstaColores.CARD);
        btncancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btncancelar.addActionListener(e -> dispose());
        
        acciones.add(btnguardar);
        acciones.add(btncancelar);
        
        LblEstado = new JLabel(" ");
        LblEstado.setFont(UIConstantes.PEQUENO_FONT);
        LblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        LblEstado.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(lbltitulo);
        card.add(Box.createVerticalStrut(6));
        card.add(lblsubtitulo);
        card.add(Box.createVerticalStrut(20));
        card.add(formulario);
        card.add(Box.createVerticalStrut(10));
        card.add(LblEstado);
        card.add(Box.createVerticalStrut(10));
        card.add(acciones);
        
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        
        wrapper.add(card);
        
        add(wrapper, BorderLayout.CENTER);
    }
    
    private JTextField CrearCampoTexto() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(420, UIConstantes.ALTURA_BOTON));
        field.setFont(UIConstantes.TEXTO_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        field.setBackground(InstaColores.INPUT_BG);
        
        return field;
    }
    
    private JPasswordField CrearCampoContrasena() {
        JPasswordField field = new JPasswordField();
        field.setPreferredSize(new Dimension(420, UIConstantes.ALTURA_BOTON));
        field.setFont(UIConstantes.TEXTO_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(InstaColores.BORDER), BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        field.setBackground(InstaColores.INPUT_BG);
        
        return field;
    }
    
    private JComboBox<String> CrearComboBox(String[] opciones) {
        JComboBox<String> combo = new JComboBox<>(opciones);
        combo.setFont(UIConstantes.TEXTO_FONT);
        combo.setPreferredSize(new Dimension(200, UIConstantes.ALTURA_BOTON));
        combo.setBackground(InstaColores.INPUT_BG);
        
        return combo;
    }
    
    private JSpinner CrearSpinnerEdad() {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(18, 1, 120, 1));
        spinner.setPreferredSize(new Dimension(200, UIConstantes.ALTURA_BOTON));
        spinner.setFont(UIConstantes.TEXTO_FONT);
        
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
        
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(InstaColores.TEXTO_PRIMARIO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        componente.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(lbl);
        panel.add(Box.createVerticalStrut(5));
        panel.add(componente);
        
        return panel;
    }
    
    private JPanel CrearCampoFoto() {
        JPanel contenedor = new JPanel();
        contenedor.setOpaque(false);
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        
        JLabel lbl = new JLabel("Foto de Perfil");
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(InstaColores.TEXTO_PRIMARIO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel filafoto = new JPanel(new BorderLayout(10, 0));
        filafoto.setOpaque(false);
                
        BotonRedondeado btnexaminar = new BotonRedondeado("Examinar");
        btnexaminar.setPreferredSize(new Dimension(130, UIConstantes.ALTURA_BOTON));
        btnexaminar.addActionListener(e -> SeleccionarFoto());
        
        filafoto.add(TxtFotoPerfil, BorderLayout.CENTER);
        filafoto.add(btnexaminar, BorderLayout.EAST);
        
        contenedor.add(lbl);
        contenedor.add(Box.createVerticalStrut(5));
        contenedor.add(filafoto);
        
        return contenedor;
    }
    
    private void SeleccionarFoto() {
        JFileChooser chooser = new JFileChooser();
        int resultado = chooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            RutaFotoSeleccionada = file.getAbsolutePath();
            TxtFotoPerfil.setText(RutaFotoSeleccionada);
        }
    }
    
    private void GuardarCambios() {
        if (UsuarioOriginal == null) {
            MostrarError("No se pudo cargar el usuario");
            return;
        }
        
        String nombre = TxtNombre.getText().trim();
        String contrasena = new String(TxtContrasena.getPassword()).trim();
        int edad = (Integer) SPEdad.getValue();
        String tipotexto = (String) CBTipoCuenta.getSelectedItem();
        TipoCuenta tipocuenta = TipoCuenta.valueOf(tipotexto);
        
        String fotofinal = UsuarioOriginal.getFotoPerfil();
        
        if (RutaFotoSeleccionada != null && !RutaFotoSeleccionada.isBlank() && !RutaFotoSeleccionada.equals(UsuarioOriginal.getFotoPerfil())) {
            String rutacopiada = FileUtils.CopiarFotoPerfil(RutaFotoSeleccionada, UsuarioOriginal.getUsuario());
            
            if (rutacopiada.isBlank()) {
                MostrarError("No se pudo copiar la foto de perfil");
                return;
            }
            
            fotofinal = rutacopiada;
        }
        
        boolean actualizado = usuarioService.ActualizarPerfil(UsuarioOriginal.getUsuario(), nombre, contrasena, edad, tipocuenta, fotofinal);
        
        if (!actualizado) {
            MostrarError("No se pudo actualizar el perfil");
            return;
        }
        
        Guardado = true;
        dispose();
    }
    
    private void MostrarError(String mensaje) {
        LblEstado.setForeground(InstaColores.ERROR);
        LblEstado.setText(mensaje);
    }
    
    public boolean isGuardado() {
        return Guardado;
    }
}


