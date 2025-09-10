package dados;

import java.util.Random;

public class Dados {
    private byte numCaras;
    private int numDados;
    private Random random;

    public Dados( byte numCaras, int numDados){
        this.numCaras = numCaras;
        this.numDados =numDados;
        this.random = new Random();
    }

    public int[] tirarDados () {
        int[] resultados = new int[numDados];

        for (int i = 0; i < numDados; i++) {
            resultados[i] = random.nextInt(numCaras)+1;
        }
        return resultados;
    }
}

//Instuccion para llamar la funcion dados

//import dados.Dados;

//int numDados = 1; //numero de dados en el array
//byte numCaras = 6; // numero de caras del dado
//
////Creo el objeto
//Dados tiradas = new Dados(numCaras, numDados);
//
//int[] numeroDado = tiradas.tirarDados();
//
//// Llamo al metodo y guardo el resultado en una variable (de todo el array)
//        for(int i=0;i < numeroDado.length;i++)
//        System.out.println("[" + numeroDado[i] + "]");
// Si quisiera que los dados llamados sean mas especificos, tengo que llamarlo:
//                               *** numeroDado[n]; ***
//              *** donde n sera la posicion que ocupa en el array ***
