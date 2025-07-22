import javax.swing.JOptionPane;

public class ConversorMoedas {

    public static void main(String[] args){
        while (true) {
            String menu = "Bem-vindo ao Conversor de Moedas!\n\n" +
                    "Escolha a opção de conversão:\n" +
                    "1 - BRL para USD\n"+
                    "2 - USD para BRL\n"+
                    "3 - EUR para BRL\n"+
                    "4 - BRL para EUR\n"+
                    "5 - EUR para USD\n"+
                    "6 - USD para EUR\n"+
                    "7 - Sair";

            String input = JOptionPane.showInputDialog(menu);

            if (input == null) {
                // Usuário fechou a janela
                break;
            }

            int opcao;
            try {
                opcao = Integer.parseInt(input);
                if (opcao < 1 || opcao > 7) {
                    JOptionPane.showMessageDialog(null, "Por favor, insira uma opção válida (1 a 7).");
                    continue;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida! Digite um número de 1 a 7.");
                continue;
            }

            if (opcao == 7) {
                JOptionPane.showMessageDialog(null, "Saindo do Conversor. Até mais!");
                break;
            }

            String moedaOrigem = "";
            String moedaDestino = "";
            switch (opcao) {
                case 1:
                    moedaOrigem = "BRL";
                    moedaDestino = "USD";
                    break;
                case 2:
                    moedaOrigem = "USD";
                    moedaDestino = "BRL";
                    break;
                case 3:
                    moedaOrigem = "EUR";
                    moedaDestino = "BRL";
                    break;
                case 4:
                    moedaOrigem = "BRL";
                    moedaDestino = "EUR";
                    break;
                case 5:
                    moedaOrigem = "EUR";
                    moedaDestino = "USD";
                    break;
                case 6:
                    moedaOrigem = "USD";
                    moedaDestino = "EUR";
                    break;
            }

            String valorStr = JOptionPane.showInputDialog(
                    "Digite o valor a ser convertido de " + moedaOrigem + " para " + moedaDestino + ":"
            );
            if (valorStr == null) continue;

            double valor;
            try {
                valor = Double.parseDouble(valorStr);
                if (valor <= 0) {
                    JOptionPane.showMessageDialog(null, "Digite um valor positivo.");
                    continue;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Valor inválido!");
                continue;
            }

            try {
                double resultado = APIClient.consultarTaxaCambio(moedaOrigem, moedaDestino, valor);
                String mensagem = String.format("Resultado:\n%.2f %s = %.2f %s", valor, moedaOrigem, resultado, moedaDestino);
                JOptionPane.showMessageDialog(null, mensagem);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Não foi possível gerar a conversão no momento.\nErro: " + e.getMessage()
                );
            }
        }
    }
}