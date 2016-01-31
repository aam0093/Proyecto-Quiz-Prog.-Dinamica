package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import exportar.Exportar;
import exportar.ExportarFactory;
import pregunta.*;
import problemas.Knapsack;
import problemas.MultiplicaMatrices;
import problemas.Problema;
import problemas.Problema.TIPO;
import problemas.SubsecuenciaComun;
import problemas.TSP;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Component;
import javax.swing.SwingConstants;

public class ExportarFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFGuarda;
	private JTextField txtFSemilla;

	String rutaRecuperado = utiles.Utiles.getRutaRecuperado();

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Problema p = new Knapsack(2, 3);
					ExportarFrame frame = new ExportarFrame(p, 2);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ExportarFrame(Problema proble, int i) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Parametros asociados a la ventana
		this.setBounds(100, 100, 500, 400);
		setVisible(true);
		setTitle("Exportar / Recuperar Problemas");

		// Creamos el conjunto de pestañas
		JTabbedPane pestañas = new JTabbedPane();

		// Creamos el panel y lo añadimos a las pestañas
		JPanel panelExportar = new JPanel();

		// Añadimos un nombre de la pestaña y el panel
		pestañas.addTab("Exportar", panelExportar);
		panelExportar.setLayout(new BorderLayout(0, 0));

		// **Añadir panel Titulo
		JPanel panelTitulo = new JPanel();
		panelTitulo.setLayout(new FlowLayout());

		JLabel lblTitulo = new JLabel("Exportar: ");
		lblTitulo.setFont(new Font("Tahoma", Font.ITALIC, 20));

		panelTitulo.add(lblTitulo);

		// ***Añadir Panel Central
		JPanel panelCentral = new JPanel();
		GridBagLayout gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[] { 100, 89, 110, 0, 100, 0 };
		gbl_panelCentral.rowHeights = new int[] { 58, 19, 51, 49, 0, 43, 0, 38, 58, 0 };
		gbl_panelCentral.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelCentral.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelCentral.setLayout(gbl_panelCentral);
		
				JLabel lblGuardarEn = new JLabel("Guardar en: ");
				GridBagConstraints gbc_lblGuardarEn = new GridBagConstraints();
				gbc_lblGuardarEn.fill = GridBagConstraints.BOTH;
				gbc_lblGuardarEn.insets = new Insets(0, 0, 5, 5);
				gbc_lblGuardarEn.gridx = 1;
				gbc_lblGuardarEn.gridy = 3;
				panelCentral.add(lblGuardarEn, gbc_lblGuardarEn);
		
				textFGuarda = new JTextField();
				GridBagConstraints gbc_textFGuarda = new GridBagConstraints();
				gbc_textFGuarda.fill = GridBagConstraints.HORIZONTAL;
				gbc_textFGuarda.insets = new Insets(0, 0, 5, 5);
				gbc_textFGuarda.gridx = 2;
				gbc_textFGuarda.gridy = 3;
				panelCentral.add(textFGuarda, gbc_textFGuarda);
				textFGuarda.setColumns(25);
		
				JButton btnBrowse = new JButton("Browse..");
				GridBagConstraints gbc_btnBrowse = new GridBagConstraints();
				gbc_btnBrowse.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnBrowse.insets = new Insets(0, 0, 5, 5);
				gbc_btnBrowse.gridx = 3;
				gbc_btnBrowse.gridy = 3;
				panelCentral.add(btnBrowse, gbc_btnBrowse);
				btnBrowse.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						// Creamos selector de apertura
						JFileChooser chooser = new JFileChooser();
						chooser.setCurrentDirectory(new java.io.File("."));
						chooser.setDialogTitle("Seleccionar Directorio");
						chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
								textFGuarda.setText(chooser.getSelectedFile().toString());
						} else {
							System.out.println("No seleccion ");
						} 
					}
				});
				btnBrowse.setBackground(Color.WHITE);

		final JRadioButton rdbtnHtml = new JRadioButton("HTML");
		rdbtnHtml.setSelected(true);
		GridBagConstraints gbc_rdbtnHtml = new GridBagConstraints();
		gbc_rdbtnHtml.fill = GridBagConstraints.BOTH;
		gbc_rdbtnHtml.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnHtml.gridx = 1;
		gbc_rdbtnHtml.gridy = 6;
		panelCentral.add(rdbtnHtml, gbc_rdbtnHtml);

		final JRadioButton rdbtnMoodle = new JRadioButton("Moodle");
		GridBagConstraints gbc_rdbtnMoodle = new GridBagConstraints();
		gbc_rdbtnMoodle.fill = GridBagConstraints.BOTH;
		gbc_rdbtnMoodle.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnMoodle.gridx = 2;
		gbc_rdbtnMoodle.gridy = 6;
		panelCentral.add(rdbtnMoodle, gbc_rdbtnMoodle);

		JRadioButton rdbtnJson = new JRadioButton("Json");
		GridBagConstraints gbc_rdbtnJson = new GridBagConstraints();
		gbc_rdbtnJson.fill = GridBagConstraints.BOTH;
		gbc_rdbtnJson.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnJson.gridx = 3;
		gbc_rdbtnJson.gridy = 6;
		panelCentral.add(rdbtnJson, gbc_rdbtnJson);

		final ButtonGroup formatos = new ButtonGroup();
		formatos.add(rdbtnHtml);
		formatos.add(rdbtnMoodle);
		formatos.add(rdbtnJson);

		// Componentes del panel1
		panelTitulo.add(lblTitulo, FlowLayout.LEFT);

		// Añadir panel Botones
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new FlowLayout());

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<Problema> problemas = (ArrayList<Problema>) Problema.problemasGenerados;
				String ruta = textFGuarda.getText();
				String tipo;
				if (rdbtnMoodle.isSelected()) {
					tipo = "XML";
				} else {
					if (rdbtnHtml.isSelected())
						tipo = "HTML";
					else
						tipo = "JSON";
				}
				if (problemas.size() == 0) {
					JOptionPane.showMessageDialog(new JFrame(), "No se ha generado ningun problema", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					ExportarFactory exportarFactory = new ExportarFactory();
					Exportar ex;
					ex = exportarFactory.getFormato(tipo);
					ex.abrirFichero();

					for (Problema p : problemas) {
						Pregunta preg = construirPregunta(p);
						ex.exportar(preg, ruta);
					}
					ex.cerrarFichero();
					JOptionPane.showMessageDialog(new JFrame(), "Fichero Guardado", "Info",
							JOptionPane.OK_CANCEL_OPTION);
				}
				dispose();
			}

		});
		panelBotones.add(btnGuardar);

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		panelBotones.add(btnCancel);
		panelExportar.add(panelTitulo, BorderLayout.NORTH);
		panelExportar.add(panelCentral, BorderLayout.CENTER);
		panelExportar.add(panelBotones, BorderLayout.SOUTH);

		// ****PANEL RECUPERAR ****** //

		JPanel panelRecuperar = new JPanel();
		panelRecuperar.setLayout(new BorderLayout());

		JPanel panelParametros = new JPanel();

		panelRecuperar.add(panelParametros, BorderLayout.WEST);

		JPanel panelVista = new JPanel();
		panelVista = new JPanel();
		panelVista.setBorder(new EmptyBorder(5, 5, 5, 5));// adds margin to
		final JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setContentType("text/html");
		textPane.setEditorKit(utiles.Utiles.getEstilo());

		// textPane.setBounds(260, 23, 209, 237);

		JScrollPane scrollLista = new JScrollPane(textPane);
		scrollLista.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollLista.setBounds(243, 75, 331, 227);
		panelVista.setLayout(new BorderLayout());
		panelVista.add(scrollLista, BorderLayout.CENTER);

		panelRecuperar.add(panelVista, BorderLayout.CENTER);

		GridBagLayout gbl_panelParametros = new GridBagLayout();
		gbl_panelParametros.columnWidths = new int[] { 30, 0, 0, 0 };
		gbl_panelParametros.rowHeights = new int[] { 14, 0, 0 };
		gbl_panelParametros.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		gbl_panelParametros.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		panelParametros.setLayout(gbl_panelParametros);
		JLabel lblNewLabel = new JLabel("Semilla: ");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panelParametros.add(lblNewLabel, gbc_lblNewLabel);

		JButton btnVer = new JButton("Recuperar");
		btnVer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtFSemilla.getText().isEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(), "Introduce un valor para la semilla", "Dialog",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					Problema p = recuperarProblema(txtFSemilla.getText().toString());
					System.out.println("Problema;: " + p.getTipo());
					if (p.getTipo().equals(Problema.TIPO.MOCHILA.toString())) {
						Knapsack mochila = (Knapsack) p;
						mochila.execute();
						utiles.Utiles.añadirMochilaPanel(textPane, mochila, rutaRecuperado);
					} else {
						if (p.getTipo().equals(Problema.TIPO.SUBSECUENCIA.toString())) {
							SubsecuenciaComun subsecuencia = (SubsecuenciaComun) p;
							subsecuencia.execute();
							utiles.Utiles.añadirSubsecuenciaPanel(textPane, subsecuencia, rutaRecuperado);
						}
						if (p.getTipo().equals(Problema.TIPO.VIAJANTE.toString())) {
							TSP viajante = (TSP) p;
							viajante.execute();
							utiles.Utiles.añadirViajantePanel(textPane, viajante, rutaRecuperado);
						}
						if (p.getTipo().equals(Problema.TIPO.MATRICES.toString())) {
							MultiplicaMatrices matrices = (MultiplicaMatrices) p;
							matrices.execute();
							utiles.Utiles.añadirMatricesPanel(textPane, matrices, rutaRecuperado);
						}
					}
					utiles.Utiles.cargarTextPane(textPane, rutaRecuperado);
				}
			}
		});

		txtFSemilla = new JTextField();
		txtFSemilla.setAlignmentY(Component.TOP_ALIGNMENT);
		txtFSemilla.setAlignmentX(Component.RIGHT_ALIGNMENT);
		txtFSemilla.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				{
					if (txtFSemilla.getText().length() == 15)
						arg0.consume();
				}
			}
		});
		txtFSemilla.setColumns(10);
		GridBagConstraints gbc_txtFSemilla = new GridBagConstraints();
		gbc_txtFSemilla.anchor = GridBagConstraints.NORTHEAST;
		gbc_txtFSemilla.insets = new Insets(0, 0, 5, 5);
		gbc_txtFSemilla.gridx = 1;
		gbc_txtFSemilla.gridy = 1;
		panelParametros.add(txtFSemilla, gbc_txtFSemilla);
		GridBagConstraints gbc_btnVer = new GridBagConstraints();
		gbc_btnVer.insets = new Insets(0, 0, 0, 5);
		gbc_btnVer.anchor = GridBagConstraints.WEST;
		gbc_btnVer.gridx = 1;
		gbc_btnVer.gridy = 3;
		panelParametros.add(btnVer, gbc_btnVer);

		JPanel panelBotonRecuperar = new JPanel();

		JButton btnNewButton_1 = new JButton("Limpiar");
		btnNewButton_1.setBounds(260, 286, 89, 23);
		panelBotonRecuperar.add(btnNewButton_1);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(526, 286, 75, 23);
		panelBotonRecuperar.add(btnCancelar);

		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				utiles.Utiles.borrarPanel(rutaRecuperado);
				dispose();
			}
		});

		btnCancelar.setBounds(380, 286, 89, 23);
		panelBotonRecuperar.add(btnCancelar);

		panelRecuperar.add(panelBotonRecuperar, BorderLayout.SOUTH);

		pestañas.addTab("Recuperar", panelRecuperar);
		getContentPane().add(pestañas);
	}

	public Problema recuperarProblema(String seed) {
		String tipo = seed.substring(0, 2);

		Problema problema = null;
		if (tipo.equals("10")) {

			int cantidad = Integer.parseInt(seed.substring(2, 5));
			int num_elem = Integer.parseInt(seed.substring(5, 8));

			System.out.println("Cantidad: " + cantidad + " - - " + "NumElemetos: " + num_elem);
			problema = new Knapsack(cantidad, num_elem, Long.valueOf(seed).longValue());
			return problema;
		}
		if (tipo.equals("20")) {
			int longcad1 = Integer.parseInt(seed.substring(2, 5));
			int longcad2 = Integer.parseInt(seed.substring(5, 8));
			problema = new SubsecuenciaComun(longcad1, longcad2, Long.valueOf(seed).longValue());
			return problema;
		}
		if (tipo.equals("30")) {
			problema = new TSP(0);
			return problema.recuperarProblema(seed);
		}
		if (tipo.equals("40")) {
			System.out.println("entra matrices");
			int numMat = Integer.parseInt(seed.substring(2, 5));
			problema = new MultiplicaMatrices(numMat, Long.parseLong(seed));
			return problema;
		}
			
		return null;
	}

	public Pregunta construirPregunta(Problema p) {

		PreguntaDirector creadorPregunta = new PreguntaDirector();
		PreguntaBuilder pr = null;
		switch (p.getTipo()) {
		case "MOCHILA":
			if (p.getTipoPregunta() == 1) {
				pr = new PrMochilaTipo1((Knapsack) p);
			} else {
				if (p.getTipoPregunta() == 2) {
					pr = new PrMochilaTipo2((Knapsack) p);
				} else {
					pr = new PrMochilaTipo3((Knapsack) p);
				}
			}
			break;
		case "SUBSECUENCIA":
			if (p.getTipoPregunta() == 1) {
				pr = new PrLCSTipo1((SubsecuenciaComun) p);
			} else {
				if (p.getTipoPregunta() == 2) {
					pr = new PrLCSTipo2((SubsecuenciaComun) p);
				} else {
					pr = new PrLCSTipo3((SubsecuenciaComun) p);
				}
			}
			break;
	case "MATRICES":
		if (p.getTipoPregunta() == 1) {
			pr = new PrMatricesTipo1((MultiplicaMatrices) p);
		} else {
			if (p.getTipoPregunta() == 2) {
				pr = new PrMatricesTipo2((MultiplicaMatrices) p);
			} else {
				pr = new PrMatricesTipo3((MultiplicaMatrices) p);
			}
		}
	}

		creadorPregunta.setPreguntaBuilder(pr);
		creadorPregunta.construirPregunta();

		Pregunta pregunta = creadorPregunta.getPregunta();
		return pregunta;
	}

}