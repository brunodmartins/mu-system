package view.livro;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import model.Livro;
import services.auxiliarityViews.AlterarDadoView;
import services.auxiliarityViews.GerenciamentoServices;
import services.validator.Validator;
import view.GUIModels.tableModels.LivroTableModel;
import controller.LivroController;

public class GerenciaLivroView extends JInternalFrame implements ActionListener, GerenciamentoServices<Livro>{
	
	private static final long serialVersionUID = 3439220950863868970L;
	
	private JPanel painel;
	private JPanel painelFiltro;
	private JPanel painelBotoes;
	private JScrollPane painelScroll;
	
	private JTable tblLivro;	
	private LivroTableModel tblModel;
	
	private JLabel lbFiltro;
	
	private JTextField tfFiltro;
	
	private JButton btnAlterar;
	private JButton btnDeletar;
	private JButton btnCancelar;
	private JButton btnPesquisar;
	
	private TableRowSorter<LivroTableModel> sorter;
	
	private static final int COL_TITULO = 0; 
	
	private LivroController crtl = new LivroController();	
	
	
	public GerenciaLivroView() {
		try {
			inicializar();
			setLayout();
			setActions();
			setFrame();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();
			this.dispose();
		}		
	}

	/**
	 * Configuar o frame da janela
	 * @author joão
	 */
	private void setFrame() {
		 this.setTitle("Gerenciador de Livros");
		 this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		 this.setSize(480,360);
		 this.setVisible(true);		 
		 this.setClosable(true);
		 this.setMaximizable(true);
		 this.setIconifiable(true);
		 this.setResizable(true);		 
	}
	
	/**
	 * Inicializa os componentes da tela
	 * @author joão
	 * @throws Exception 
	 */
	private void inicializar() throws Exception{		
		carregarTabela();
		btnAlterar   = new JButton("Alterar");
		btnDeletar   = new JButton("Deletar");
		btnCancelar  = new JButton("Cancelar");	
		btnPesquisar = new JButton("Pesquisar");
		
		lbFiltro = new JLabel("Filtro");
		tfFiltro = new JTextField(20);
		
		painel = new JPanel(new BorderLayout(10, 10));
		painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 5,5));
		painelFiltro.setSize(getWidth(), 20);
		painelScroll = new JScrollPane(tblLivro);
		painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 5,5));
	}
	
	/**
	 * Carrega a tabela de Livros
	 * @throws Exception 
	 */
	private void carregarTabela() throws Exception {
		carregarTableModel();			
		tblLivro = new JTable();
		popularTabela();		
	}
	
	/**
	 * Popula a tabela
	 */
	private void popularTabela() {
		tblLivro.setModel(tblModel);
		sorter = new TableRowSorter<LivroTableModel>(tblModel);
		tblLivro.setRowSorter(sorter);
	}

	/**
	 * Carrega um tableModel
	 * @return 
	 * @throws Exception 
	 */
	private void carregarTableModel() throws Exception{
		List<Livro> listaLivros = crtl.listarTodos();
		tblModel = new LivroTableModel(listaLivros);		
	}
	
	/**
	 * Adiciona os componentes na 
	 * tela
	 * @author joão
	 */
	private void setLayout(){

		painelFiltro.add(lbFiltro);
		painelFiltro.add(tfFiltro);
		
		painelBotoes.add(btnAlterar);
		painelBotoes.add(btnDeletar);
		painelBotoes.add(btnPesquisar);
		painelBotoes.add(btnCancelar);

		painel.add(painelFiltro, BorderLayout.NORTH);
		painel.add(painelScroll, BorderLayout.CENTER);
		painel.add(painelBotoes, BorderLayout.SOUTH);
		
		this.add(painel);
	}
	
	/**
	 * Adiciona os actions listeners aos botões
	 * @author joão
	 */
	private void setActions(){
		btnAlterar.addActionListener(this);
		btnDeletar.addActionListener(this);
		btnPesquisar.addActionListener(this);
		btnCancelar.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if(src.equals(btnAlterar)){
			alterar();
		}else if(src.equals(btnDeletar)){
			deletar();
		}else if(src.equals(btnPesquisar)){
			procurar();
		}else if(src.equals(btnCancelar)){
			this.dispose();
		}
		
	}
	
	/**
	 * Confirma a deleção de um Livro
	 * @param livro
	 * @return
	 */
	private boolean confirmaDeletar(Livro livro) {
		String msg = "Deseja realmente deletar o livro " + livro + " do sistema?";
		int opt = JOptionPane.showConfirmDialog(this, msg);
		System.out.println(opt);
		return opt == 0;
	}
		
	@Override
	public void alterar() {
		int idx = tblLivro.getSelectedRow();
		int col = tblLivro.getSelectedColumn();
		try{
			validarIndice(idx);
			
			String nomeCampo = tblModel.getColumnName(col);			
			Class<?> clazz = tblModel.getColumnClass(col);
			Object e = AlterarDadoView.alterarDado(clazz, nomeCampo);
			
			if(!Validator.isEmpty(String.valueOf(e))){				
				tblModel.setValueAt(e, idx, col);
				String titulo = (String) tblLivro.getValueAt(idx, COL_TITULO);
				Livro liv = tblModel.get(titulo);
				crtl.atualizarLivro(liv);
				JOptionPane.showMessageDialog(this, "Livro atualizado com sucesso");
				carregarTableModel();
				popularTabela();
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		
	}
		
	/**
	 * Valida o indice selecionado em uma
	 * linha da tabela
	 * @param idx
	 * @throws Exception 
	 */
	private void validarIndice(int idx) throws Exception {
		if(idx == -1)
			throw new Exception("Nenhum Livro foi selecionado");		
	}

	@Override
	public List<Livro> procurar() {
		String filtro = tfFiltro.getText();
		if(filtro.length() == 0){
			sorter.setRowFilter(null);
		}else{
			sorter.setRowFilter(RowFilter.regexFilter(filtro, 0));
		}
		return null;
	}
	
	@Override
	public void deletar() {
		int idx = tblLivro.getSelectedRow();
		try {
			validarIndice(idx);
			String titulo = (String) tblLivro.getValueAt(idx, COL_TITULO);
			Livro l = tblModel.get(titulo);
			if(confirmaDeletar(l)){
				crtl.deletarLivro(l);
				JOptionPane.showMessageDialog(this, "Livro removido com sucesso");
				carregarTableModel();
				popularTabela();
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(this, e1.getMessage());
			e1.printStackTrace();
		}
		
		
	}


}