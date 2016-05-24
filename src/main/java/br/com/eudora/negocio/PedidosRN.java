package br.com.eudora.negocio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Singleton;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import br.com.eudora.entidade.Pedido;

@Stateless
public class PedidosRN  implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Metodo para pegar os pedidos pelo periodo de data 
	 * @return
	 */
	public List<Pedido> obtemPedidos( String pDataIni, String pDataFim){
		List<Pedido> lReturn = null;

		try {
			//http://portaltop.eudora.com.br/interview/api/Ecommerce/GetOrders
			//http://localhost:8080/eudoraBoticario/pedidosTeste.json
			InputStream input = new URL("http://portaltop.eudora.com.br/interview/api/Ecommerce/GetOrders").openStream();
			JsonReader reader = new JsonReader(new InputStreamReader(input, "UTF-8"));

			Gson gson = new GsonBuilder().create();

			reader.beginArray();
			
			
			Date dataIni = transformaData(pDataIni);
			Date dataFim = transformaData(pDataFim);
			
			

			while (reader.hasNext()) {

				Pedido person = gson.fromJson(reader, Pedido.class);
				
				if( transformaData( person.getDate() ).after(dataIni) && transformaData( person.getDate() ).before(dataFim)){
					if(lReturn == null ) lReturn = new ArrayList<Pedido>();
					lReturn.add(person);
				}
			}

			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lReturn;
	}
	
	
	


	public Date transformaData( String pData ){
		
		Date pDateReturn = null;

		try {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			pDateReturn = formatter.parse(pData.substring(0, 10));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return pDateReturn;
		
	}

}
