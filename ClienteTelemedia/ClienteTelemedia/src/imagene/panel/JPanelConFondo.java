/**
 * @author Hugo Valdovinos Hernández <hugo.emec@gmail.com>
 */
package imagene.panel;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
/**@brief sobrecarga de la clase JPanel
 * @param imagen variable que guarda la imagen de fondo en el panel*/
@SuppressWarnings("serial")
public class JPanelConFondo extends JPanel {

    private Image imagen;

    public JPanelConFondo() {
    }

    /**@brief combierte la imagen apartir de la ruta recibida
     * @param nombreImagen ruta a la imagen*/
    public JPanelConFondo(String nombreImagen) {
        if (nombreImagen != null) {
            imagen = new ImageIcon(getClass().getResource(nombreImagen)).getImage();
        }
    }
    /**@brief combierte la imagen apartir de la imagen recibida
     * @param imagenInicial ruta a la imagen*/
    public JPanelConFondo(Image imagenInicial) {
        if (imagenInicial != null) {
            imagen = imagenInicial;
        }
    }
    /**@brief cambia la imagen apartir de la nueva ruta recibida
     * @param nombreImagen ruta a la imagen*/
    public void setImagen(String nombreImagen) {
        if (nombreImagen != null) {
        	imagen = new ImageIcon(nombreImagen).getImage();
        } else {
            imagen = null;
        }

        repaint();
    }
    /**@brief cambia la imagen apartir de la nueva imagen
     * @param nuevaImagen ruta a la imagen*/
    public void setImagen(Image nuevaImagen) {
        imagen = nuevaImagen;

        repaint();
    }
    /**@brief asigna la imagen al panel*/
    @Override
    public void paint(Graphics g) {
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);

            setOpaque(false);
        } else {
            setOpaque(true);
        }

        super.paint(g);
    }
}
