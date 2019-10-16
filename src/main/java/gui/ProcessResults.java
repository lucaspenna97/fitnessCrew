package gui;

public class ProcessResults {

    public static String calculateImc (String peso, String altura) {

        if (altura.contains(",") || altura.contains(".")) altura.replace(",", "").replace(".", "");

        int alturaQuadrado = Integer.parseInt(altura) * Integer.parseInt(altura);
        int pesoFormatado = Integer.parseInt(peso) * 10000;

        return String.valueOf(pesoFormatado / alturaQuadrado);

    }

    public static String calculateTmb (int peso, int altura, int idade, String sexo) {

        double resultado = 0;

        if (sexo.equalsIgnoreCase("Masculino")) {

            resultado = ((13.4 * peso) + (4.8 * altura) + 88) - (5.7 * idade);
        }

        if (sexo.equalsIgnoreCase("Feminino")) {

            resultado = ((9.2 * peso) + (3.1 * altura) + 448) - (4.3 * idade);

        }

        return String.valueOf(resultado);

    }

    public static String calculateGrauImc (int imc) {

        if (imc < 18) { return "Abaixo do peso"; }

        else if (imc >= 18 && imc <= 24) { return "Peso normal"; }

        else if (imc >= 25 && imc <= 29) { return "Acima do peso"; }

        else if (imc >= 30 && imc <= 34) { return "Obesidade grau I"; }

        else if (imc >= 35 && imc <= 39) { return "Obesidade grau II"; }

        else if (imc >= 40) { return "Obesidade grau III"; }

        else return null;

    }

    public static String resumoDescricao (int imc) {


        if (imc < 18) { return
                "Opte por uma dieta mais calórica \n" +
                        "(de acordo com sua taxa metabólica)\n " +
                        "ingerindo alimentos de alta qualidade\n" +
                        "e praticando musculação."; }

        else if (imc >= 18 && imc <= 24) { return
                "Parabéns! Mantenha-se com uma \n" +
                        "alimentação equilibrada e \n" +
                        "atividades físicas frequêntes."; }

        else if (imc >= 25 && imc <= 29) { return
                "Procure realizar uma dieta mais balanceada,\n " +
                        "com menos ingestão calórica, agregada\n " +
                        "a pratica rotineira de exercícios físicos."; }

        else if (imc >= 30 && imc <= 34) { return
                "O tratamento é realizado através\n " +
                        "e dieta apropriada com avaliação\n " +
                        "médica em conjunto com a prática\n " +
                        "de exercícios."; }

        else if (imc >= 35 && imc <= 39) { return
                "O tratamento é dado através de\n " +
                        "mudanças no estilo de vida, associado\n " +
                        "ao tratamento multiprofissional com \n" +
                        "nutricionista, psicólogo e médico."; }

        else if (imc >= 40) { return
                "A associação de reeducação alimentar\n " +
                        "e atividade física têm melhores resultados\n " +
                        "a curto e médio prazo, porém, muitas vezes,\n " +
                        "a atividade física acaba sendo bastante\n " +
                        "restrita, dependendo do grau de excesso\n " +
                        "de peso."; }

        else return null;

    }

}
