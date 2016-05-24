package br.com.eudora.managedBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.PieChartModel;

import br.com.eudora.entidade.Pedido;
import br.com.eudora.negocio.PedidosRN;

@Named
@SessionScoped
public class PedidosMB implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<Pedido> lPedidos = new ArrayList<Pedido>();
	private PieChartModel pieModel1;

	

	@Inject
	PedidosRN pedidosRN;
	
	private String teste = "teste aaa";

	
	@PostConstruct
	public void inicio(){
		
	}
	
	public void obterPedidos(){
		
		try {
			lPedidos = pedidosRN.obtemPedidos("2016-01-01","2016-01-31");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	
	public void redirect(){
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	    try {
			externalContext.redirect("jsonJquery/demo.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void createPieModel1() {
		
		Integer cancel = 0;
		Integer pend = 0;
		
		pieModel1 = new PieChartModel();
		
		for (Pedido pedido : lPedidos) {
			if( !pedido.getStatus().equals("canceled") ){
				pend =pend+1;
			}else if( pedido.getStatus().equals("canceled") ){
				cancel =cancel+1;
			}
		}
		
        
         
        pieModel1.set("Cancelados", cancel);
        pieModel1.set("Pendentes", pend);
         
        pieModel1.setTitle("Pedidos Status");
        pieModel1.setLegendPosition("w");
        
        RequestContext.getCurrentInstance().execute("PF('dlg2').show()");
    }
	
	
	

	public List<Pedido> getlPedidos() {
		return lPedidos;
	}



	public void setlPedidos(List<Pedido> lPedidos) {
		this.lPedidos = lPedidos;
	}



	public PieChartModel getPieModel1() {
		if (pieModel1 == null) {
			pieModel1 = new PieChartModel();
		    }
		return pieModel1;
	}

	public void setPieModel1(PieChartModel pieModel1) {
		this.pieModel1 = pieModel1;
	}

}
