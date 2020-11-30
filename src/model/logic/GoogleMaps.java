package model.logic;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.data_structures.Edge;
import model.data_structures.DiGraph;
import model.data_structures.BusquedaSecuencial;
import model.data_structures.TablaHashSeparateChaining;
import model.data_structures.Vertex;

public class GoogleMaps {

	public void imprimirElMejorGrafoDeLaHistoria(DiGraph base)
	{

		List<Vertex<Integer, Estacion>> vertex = base.vertices();
		Edge[]  arcos = base.edges();

		// Color de los vertices
		String colVerti = darColorAleatorio();

		// Color de los arcos
		String colArco = darColorAleatorio();

		// Vamos a generar el archivo de los vertices.

		try 
		{
			FileWriter file = new FileWriter("./paint/Google Api/vertices.js");

			file.write("citymap = { \n");

			for (int i = 0; i< vertex.size();i++)
			{
				Vertex aux = vertex.get(i);

				if(aux != null)
				{
					Estacion info = (Estacion) aux.getInfo();

					file.write("\t" + aux.getId() + ": {\n");
					file.write("\t center: {lat: " + info.darLatitud() + ", lng: " + info.darLongitud() + "},  \n");
					file.write("\t population: " + 150 + ",   \n");
					file.write("\t color: '" + colVerti + "', \n");
					file.write("\t titulo: " + "'Hola'" + "\n");

					if(i == base.numVertices()-1) file.write("\t } \n");
					else 			       file.write("\t }, \n");
				}
			}

			file.write("}");

			file.close();

		}
		catch (Exception e){
			e.printStackTrace();
		}

		// Vamos a generar el archivo de los arcos.

		try 
		{
			FileWriter file = new FileWriter("./paint/Google Api/arcos.js");

			file.write("cityroad = { \n");

			//Edge actual = arcos[0];
			int i = 0;
			
			while (i < arcos.length)
			{
				Edge actual = (Edge) arcos[i];
				//Arco aux= (Arco) actual.data;

				Vertex inicio = actual.getDest();
				Estacion infoInicio = (Estacion) inicio.getInfo();

				Vertex fin = actual.getSource();
				Estacion infoFin = (Estacion) fin.getInfo();

				if(inicio != null & fin != null)
				{
					file.write("\t" + inicio.getId() + fin.getId() + ": {\n");
					file.write("\t inicio: {lat: " + infoInicio.darLatitud() + ", lng: " + infoInicio.darLongitud() + "},  \n");
					file.write("\t fin: {lat: " + infoFin.darLatitud() + ", lng: " + infoFin.darLongitud() + "},  \n");
					file.write("\t color: '" + colArco + "', \n");

					if (arcos[i+1] == null)
					{
						file.write("\t  }  \n");
					}
					else
					{
						file.write("\t  }  ,\n");
					}
				}

				i++;
			}

			file.write("}");

			file.close();

		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		// Abre el mapa
		abrirArchivo();

	}


	private String darColorAleatorio() 
	{
		Color color = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
		String stringColor = Integer.toHexString(color.getRGB());
		return stringColor;
	}

	private void abrirArchivo()
	{
		String osName = System.getProperty("os.name");
		File file = new File("./paint/Google Api/grafoMapGoogle.html");
		try {

			if (osName.startsWith("Windows")) 			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());
			else if (osName.startsWith("Mac OS X")) 	Runtime.getRuntime().exec("open " + file.getAbsolutePath());
			else 										System.out.println("Please open a browser and go to " + file.getAbsolutePath());

		} 
		catch (IOException e) {
			System.out.println("Failed to start a browser to open the url " + file.getAbsolutePath());
			e.printStackTrace();
		}
	}

}
