import java.util.Locale;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class APIClient {

    private static final String API_KEY = "4c2c920a21b87f6f0b72be9f";

    public static double consultarTaxaCambio(String moedaOrigem, String moedaDestino, double valor) throws Exception {
        String urlStr = String.format(Locale.US,
                "https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%.2f",
                API_KEY, moedaOrigem, moedaDestino, valor
        );

        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        if (status != 200) {
            throw new RuntimeException("Erro na consulta da API: " + status);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder conteudo = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            conteudo.append(inputLine);
        }
        in.close();
        con.disconnect();

        JsonObject json = JsonParser.parseString(conteudo.toString()).getAsJsonObject();

        if (!json.has("result") || !"success".equals(json.get("result").getAsString())) {
            String erro = json.has("error-type") ? json.get("error-type").getAsString() : "Erro desconhecido";
            throw new RuntimeException("Falha na conversão: " + erro);
        }

        return json.get("conversion_result").getAsDouble();
    }
}
