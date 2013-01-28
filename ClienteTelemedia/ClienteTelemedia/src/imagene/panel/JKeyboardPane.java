/**
 * @author Hugo Valdovinos Hernández <hugo.emec@gmail.com>
 */
package imagene.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

/**
 * @brief Teclado virtual extiende de JPanel regresando tal y conteniendo el
 *        teclado virtual
 * @param JTextfield
 *             auxiliar para igualar al objeto JtextField recibido en el
 *            constructor
 * @param boolean auxmayus variable de control para la tecla mayus
 * @param teclas
 *             contiene los caracteres del teclado
 * @param botones
 *             para los botones del teclado
 */
@SuppressWarnings("serial")
public class JKeyboardPane extends JPanel {

	JTextField txt;
	boolean auxmayus;

	/*
	 * opcional String teclasMayus[] = { "1", "2", "3", "4", "5", "6", "7", "8",
	 * "9", "0", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S",
	 * "D", "F", "G", "H", "J", "K", "L", "Ñ", "Z", "X", "C", "V", "B", "N",
	 * "M", "." };
	 */

	String teclas[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "q",
			"w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f",
			"g", "h", "k", "l", "ñ", "z", "x", "c", "v", "b", "n", "m", "." };
	ArrayList<JButton> botones = new ArrayList<JButton>();
	JPanel pletras, specialKeys;

	/**
	 * @brief contructor en el cual se realiza todas la instrucciones al crearse
	 * @param JTexField
	 *            t objeto al cual hace referencia el teclado
	 * @return panel con el teclado asociado al JTextField recivido
	 */
	public JKeyboardPane(JTextField t) {
		txt = t;
		pletras = new JPanel();
		setLayout(new BorderLayout());
		pletras.setLayout(new GridLayout(4, 10, 0, 0));
		/*listener que ingresa la acción a realizar en el botón
		 */
		ActionListener accion = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton) e.getSource();
				if (!b.getText().equalsIgnoreCase(" ")) {
					/**
					 condición para cambiar entre mayusculas y
					 minisculas
					 */
					if (auxmayus) {

						txt.setText("" + txt.getText()
								+ b.getText().toUpperCase());
					} else {
						txt.setText("" + txt.getText()
								+ b.getText().toLowerCase());
					}
				} else {
					txt.setText(txt.getText() + " ");
				}
			}
		};

		/*
		asigna la letra crea los botones y asigna el listener
		 * */
		for (int i = 0; i < 37; i++) {
			if (teclas[i].equalsIgnoreCase("x")) {
				JLabel l = new JLabel();
				pletras.add(l);
			}
			JButton b = new JButton(teclas[i]);
			// b.setBackground(Color.black);
			b.setPreferredSize(new Dimension(80, 70));
			b.addActionListener(accion);
			pletras.add(b);
			botones.add(b);
		}
		/*
		 listener exclusivo para la tecla retroceso
		 */
		ActionListener back = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String sizetxt = txt.getText();
				if (sizetxt.length() == 0)
					return;
				// elimina el ultimo caracter de la cadena
				txt.setText(sizetxt.substring(0, sizetxt.length() - 1));
			}

		};
		/* botón para el retroceso */

		JButton b = new JButton("Borrar");
		b.setPreferredSize(new Dimension(80, 70));
		b.addActionListener(back);
		// pletras.add(b);
		// botones.add(b);

		/*
		listener exclusivo para la tecla Mayus
		 * */
		ActionListener bloqMayus = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (auxmayus) {
					auxmayus = false;
					for (int i = 0; i < 37; i++) {
						botones.get(i).setText(
								botones.get(i).getText().toLowerCase());
					}
					System.out.println("false");
				} else {
					auxmayus = true;
					for (int i = 0; i < 37; i++) {
						botones.get(i).setText(
								botones.get(i).getText().toUpperCase());
					}
				}

				pletras.repaint();
			}

		};
		/* botón para el Mayus */
		JButton r = new JButton("Mayús");
		r.setPreferredSize(new Dimension(80, 70));
		/* agregar el listener al boton */
		r.addActionListener(bloqMayus);

		/*botón para el espacio */
		specialKeys = new JPanel(new GridLayout(1, 3));

		JLabel l = new JLabel();
		specialKeys.add(r);
		specialKeys.add(l);
		specialKeys.add(b);
		add(specialKeys, BorderLayout.NORTH);
		add(pletras);
		/*tecla espacio se deja desabilitada */
		/*
		 * JButton bespacio = new JButton(" ");
		 * bespacio.addActionListener(accion); specialKeys.add(new JLabel());
		 * specialKeys.add(bespacio); specialKeys.add(new JLabel());
		 */
		// add(specialKeys, BorderLayout.SOUTH);
	}
}
