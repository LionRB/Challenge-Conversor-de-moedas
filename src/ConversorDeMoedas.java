import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.Gson;

// Classe para mapear a resposta da API
class ConversaoResponse {
    String base;
    String date;
    double result;
}

public class ConversorDeMoedasGson {

    // Função para buscar taxa usando Gson
    public static double buscarTaxa(String de, String para) throws Exception {
        // URL sem apikey (não é necessário no exchangerate.host)
        String urlStr = "https://api.exchangerate.host/convert?from=" + de + "&to=" + para;

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        Gson gson = new Gson();
        ConversaoResponse response = gson.fromJson(reader, ConversaoResponse.class);
        reader.close();

        if (response == null) {
            throw new Exception("Resposta inválida da API");
        }

        return response.result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("=== Conversor de Moedas (API com Gson) ===");
            System.out.print("Digite o valor: ");
            double valor = sc.nextDouble();

            System.out.print("De (USD, BRL, EUR, JPY...): ");
            String de = sc.next().toUpperCase();

            System.out.print("Para (USD, BRL, EUR, JPY...): ");
            String para = sc.next().toUpperCase();

            // Buscar a taxa
            double taxa = buscarTaxa(de, para);
            double convertido = valor * taxa;

            System.out.printf("%.2f %s = %.2f %s%n", valor, de, convertido, para);
            System.out.println("Taxa utilizada: 1 " + de + " = " + taxa + " " + para);

        } catch (Exception e) {
            System.out.println("Erro ao buscar taxas: " + e.getMessage());
        }

        sc.close();
    }
}





