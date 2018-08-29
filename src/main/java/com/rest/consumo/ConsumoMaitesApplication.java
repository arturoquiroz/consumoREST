package com.rest.consumo;

import java.util.LinkedList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConsumoMaitesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumoMaitesApplication.class, args);
		ListadoDeId();
		ActualizarPlanta(1);
	}
	
	private static ResponseEntity<String> Consumidora(String urlDeConsumo) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response
		  = restTemplate.getForEntity(urlDeConsumo, String.class);
		return response;
	}
	
	private static List<String> ListadoDeId() {
		ResponseEntity<String> response = Consumidora("http://localhost:8081/planta/listarId"); 
		String respuesta  = response.getBody();
		String valores = respuesta.substring(respuesta.indexOf("[")).replace('}', ' ');
		List<String> lista = new LinkedList<String>(); 
		valores = valores.substring(1, valores.length()-2);
		valores= valores + ",";
		int u = 0;
		int indice = 0;
		for (int i = 0; i <valores.length(); i++) {
			if(valores.substring(i,i+1).equals(",")) {
				u ++;
				if(u==2) {
					lista.add(valores.substring(indice, i).replace('[', ' ')
							.replace(']', ' ').trim());
					u=0;
					indice = i+1;
				}
			}
		}
		return lista;
	}
	
	private static List<String> ActualizarPlanta(int id){
		ResponseEntity<String> response = Consumidora("http://localhost:8081/planta/actualizar?id="+id); 
		String respuesta  = response.getBody();
		respuesta = respuesta.replace('"', ' ').replace('{', ' ')
				.replace('}', ' ').replaceAll(" ", "").trim();
		String [] r = respuesta.split(",");
		List<String> lista = new LinkedList<String>(); 
		for (int i = 0; i < r.length; i++) {
			lista.add(r[i]);
		}
		return lista;
	}
}
