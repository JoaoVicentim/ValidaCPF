import java.util.Scanner;

public class ValidadorCPF {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o CPF (com ou sem formatação): ");
        String cpf = scanner.nextLine().trim();

        if (cpf.length() < 11 || cpf.length() > 14) {
            System.out.println("CPF deve ter entre 11 e 14 caracteres.");
            return;
        }

        if (cpf.length() == 14 && !verificarFormatacaoCPF(cpf)) {
            System.out.println("CPF formatado incorretamente. Ex: 123.456.789-09");
            return;
        }

        // Remove pontuação manualmente
        String cpfNumerico = "";
        for (int i = 0; i < cpf.length(); i++) {
            char c = cpf.charAt(i);
            if (c >= '0' && c <= '9') {
                cpfNumerico += c;
            }
        }

        if (cpfNumerico.length() != 11) {
            System.out.println("CPF inválido: deve conter 11 dígitos numéricos.");
            return;
        }

        // Verifica se todos os dígitos são iguais
        boolean todosIguais = true;
        char primeiroDigito = cpfNumerico.charAt(0);
        for (int i = 1; i < cpfNumerico.length(); i++) {
            if (cpfNumerico.charAt(i) != primeiroDigito) {
                todosIguais = false;
                break;
            }
        }
        if (todosIguais) {
            System.out.println("CPF inválido: todos os dígitos são iguais.");
            return;
        }

        // Validação dos dígitos verificadores
        if (validarDigitosCPF(cpfNumerico)) {
            System.out.println("CPF válido!");
        } else {
            System.out.println("CPF inválido: dígitos verificadores incorretos.");
        }
    }

    private static boolean verificarFormatacaoCPF(String cpf) {
        // Verifica se os pontos e o hífen estão nas posições certas
        return cpf.charAt(3) == '.' && cpf.charAt(7) == '.' && cpf.charAt(11) == '-';
    }

    private static boolean validarDigitosCPF(String cpf) {
        int[] numeros = new int[11];
        for (int i = 0; i < 11; i++) {
            numeros[i] = cpf.charAt(i) - '0';
        }

        // Calcular primeiro dígito verificador
        int soma1 = 0;
        for (int i = 0; i < 9; i++) {
            soma1 += numeros[i] * (10 - i);
        }
        int digito1 = (soma1 * 10) % 11;
        if (digito1 == 10) digito1 = 0;

        // Calcular segundo dígito verificador (usa os 10 primeiros dígitos do CPF informado)
        int soma2 = 0;
        for (int i = 0; i < 10; i++) {
            soma2 += numeros[i] * (11 - i);
        }
        int digito2 = (soma2 * 10) % 11;
        if (digito2 == 10) digito2 = 0;

        return (digito1 == numeros[9]) && (digito2 == numeros[10]);
    }
}
