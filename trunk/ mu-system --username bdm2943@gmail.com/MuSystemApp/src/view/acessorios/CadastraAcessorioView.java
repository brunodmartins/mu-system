package view.acessorios;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Acessorio;
import controller.AcessorioController;

public class CadastraAcessorioView extends JInternalFrame implements ActionListener {
	
	private static final long serialVersionUID = 5451841733276245832L;
	
	private JPanel painel;
	
	private GridLayout grade;
	
	private JLabel lbNome;
	private JLabel lbPreco;
	
	private JTextField tfNome;
	private JFormattedTextField tfPreco;
	
	private NumberFormat currencyFormat;
	
	private JButton btnGravar;
	private JButton btnCancelar;
	
	
	public CadastraAcessorioView(){		
		 inicializar();
		 setActions();
		 setLayout();
		 setFrame();
	}
	
	/**
	 * Configuar o frame da janela
	 * @author bruno
	 */
	private void setFrame() {
		 this.setTitle("Cadastro de acessórios");
		 this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		 this.setSize(400,300);
		 this.setVisible(true);		 
		 this.setClosable(true);
		 this.setMaximizable(true);
		 this.setIconifiable(true);
		 this.setResizable(true);
		 
	}

	/**
	 * Inicializa os componentes da tela
	 * @author bruno
	 */
	private void inicializar(){
		currencyFormat = NumberFormat.getCurrencyInstance();
		
		lbNome     = new JLabel("Nome: ");
		lbPreco    = new JLabel("Preço");
			
		tfNome     = new JTextField(30);
		tfPreco    = new JFormattedTextField(currencyFormat);
		tfPreco.setColumns(10);
				
		lbNome.setLabelFor(tfPreco);
		lbPreco.setLabelFor(tfPreco);
		btnGravar   = new JButton("Gravar");
		btnCancelar = new JButton("Cancelar");
	
		grade = new GridLayout(3, 2, 5, 5);
		
		painel = new JPanel(grade);
	}
	
	/**
	 * Adiciona os actions listeners aos botões
	 * @author bruno
	 */
	private void setActions(){
		btnGravar.addActionListener(this);
		btnCancelar.addActionListener(this);
	}
	
	
	/**
	 * Adiciona os componentes na 
	 * tela
	 * @author bruno
	 */
	private void setLayout(){
		painel.add(lbNome);
		painel.add(tfNome);
		
		painel.add(lbPreco);
		painel.add(tfPreco);
				
		painel.add(btnGravar);
		painel.add(btnCancelar);
		
		this.add(painel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if(src.equals(btnCancelar)){
			this.dispose();
		}else{
			Acessorio acessorio = new Acessorio();
			try {
				acessorio.setNome(tfNome.getText());
				acessorio.setPreco(Float.parseFloat(tfPreco.getText()));				
				
				new AcessorioController().gravarAcessorio(acessorio);	
				JOptionPane.showMessageDialog(painel, "Cliente gravado com sucesso!");
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(painel, e1.getMessage());
			}
		}
	}

}