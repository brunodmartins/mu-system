package controller;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import model.Cliente;
import tableModels.ClienteTableModel;
import tableModels.TableModelFactory;
import dao.DaoFacade;

public class ClienteController {
	
	public void gravarCliente(Cliente cliente) throws Exception{
		DaoFacade.save(cliente);
	}

	public DefaultTableModel getTableModel() {
		return TableModelFactory.getTableModel(Cliente.class);
	}

	//FIXME Melhorar!
	@SuppressWarnings("unchecked")
	public DefaultTableModel carregarTabela(DefaultTableModel tblModel) throws Exception {
		List<Cliente> listaClientes = (List<Cliente>) DaoFacade.lerTodos(Cliente.class);		
		tblModel = new ClienteTableModel(listaClientes);					
		return tblModel;
	}

}