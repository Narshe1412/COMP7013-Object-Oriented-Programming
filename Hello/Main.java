
public class Main {

	public static int[][] bitmap;

	public static void Main(String[] args) {
		System.out.println("Hello world!");
		bitmap = new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 } };
		System.out.println("Stack");
		rotar90ALaDerecha();
		System.out.println("Mio");
		roto90ALaDerecha();
	}

	public static void rotar90ALaDerecha() {
		int ancho = bitmap[0].length;
		int alto = bitmap.length;

		int temp[][] = new int[ancho][alto];
		int nuevaColumna = alto - 1;
		for (int i = 0; i < alto; i++, nuevaColumna--) {
			for (int j = 0; j < ancho; j++) {
				System.out.println("bitmap[" + i + "][" + j + "] temp[" + j + "][" + nuevaColumna + "]");
				temp[j][nuevaColumna] = bitmap[i][j];
			}
		}
		bitmap = temp;
	}

	public static void roto90ALaDerecha() {
		int[][] temp = bitmap;
		System.out.println(Arrays.deepToString(temp));
		int ancho = bitmap.length;
		int alto = bitmap[0].length;
		bitmap = new int[alto][ancho];
		System.out.println("alto" + alto + " ancho" + ancho);

		for (int i = 0; i < ancho; i++) {
			for (int j = 0; j < alto; j++) {
				int pixel = temp[i][j];
				bitmap[j][i] = pixel;
				System.out.println("bitmap[" + i + "][" + j + "] temp[" + j + "][" + i + "]");
			}
		}
		System.out.println(Arrays.deepToString(temp));
		System.out.println(Arrays.deepToString(bitmap));
	}

}
