import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
 
 
// AQUÍ EMPIEZA LA CLASE
public class Dialog extends JDialog {
 
    private final JPanel contentPanel = new JPanel();
 
    /* ESTO LO GENERA ECLIPSE PARA PROBAR MIENTRAS CONSTRUIMOS
     * EL DIALOGO, LO PODEMOS DESCOMENTAR PARA
     * PROBAR EL DIÁLOGO SIN PROBAR TODA LA APLICACIÓN COMPLETA 
    public static void main(String[] args) {
        try {
            Dialog dialog = new Dialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
 
    // CONSTRUCTOR DE LA CLASE
    // crea la ventana, con los bordes, botones,
    // y todos los componentes internos para hacer lo que
    // se pretenda con éste diálogo.
    public Dialog() {
        // evita cambio de tamaño
        setResizable(true);
        // título del diáolog
        setTitle("\u00C9sto es una ventana de di\u00E1logo");
        // dimensiones que ocupa en la pantalla
        setBounds(100, 100, 450, 229);
        // capa que contendrá todo
        getContentPane().setLayout(new BorderLayout());
        // borde de la ventan
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        // pone el panel centrado
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        // sin capas para poder posicionar los elementos por coordenadas 
        contentPanel.setLayout(null);
        {
            // aquí se pone el JTextArea dentro de un JScrollPane 
            // para que tenga barras de desplazamiento
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setBounds(10, 11, 424, 146);
            contentPanel.add(scrollPane);
            {
                JTextArea txtrstoEsUn = new JTextArea();
                txtrstoEsUn.setText("\u00C9sto es un JTextArea, aqu\u00ED podemos poner un texto de varias l\u00EDneas.\r\n1\r\n2\r\n3\r\n..\r\n\r\nObserva que no se ve en la barra de tareas que exista \u00E9sta ventana.  Si fuera un JFrame s\u00ED que se ver\u00EDa en la barra de tareas con el texto  del t\u00EDtulo de la ventana...\r\n\r\nEl componente JTextArea est\u00E1 dentro de un JScrollPane para que se  visualizen las barras de scroll cuando sea necesario.\r\n\r\nLa ventana tiene el atributo 'resizable' a 'false' para evitar que se pueda cambiar el tama\u00F1o.\r\n\r\n\r\n\r\n\r\n\r\nFin del texto.");
                txtrstoEsUn.setLineWrap(true);
                txtrstoEsUn.setAutoscrolls(true);
                scrollPane.setViewportView(txtrstoEsUn);
            }
        }
        {
            // a continuación tenemos los botones clásicos 'Vale' y 'Cancela'
            // éste código lo ha generado Eclipse...
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("Vale");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // aquí van las acciones al hacer click en Vale
                        // envía el diálogo al recolector de basura de Java
                        dispose();
                    }
                });
                okButton.setActionCommand("Vale");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancelar");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        // aquí van las acciones al hacer click en Vale
                        // envía el diálogo al recolector de basura de Java
                        dispose();
                    }
                });
                cancelButton.setActionCommand("Cancelar");
                buttonPane.add(cancelButton);
            }
        }
    }
}