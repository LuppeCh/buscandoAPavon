package varios;

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
