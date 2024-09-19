package mx.unison;

import java.util.*;

public class Converter {
    public static void main(String[] args) {


        String inputString;

        Scanner keyb = new Scanner(System.in);

        while (true) {
            System.out.println("Introduce una expresion en notacion infija");
            System.out.print("> ");
            inputString = keyb.nextLine();
            if (inputString.equals("quit") || inputString.equals("QUIT")) {
                break;
            }
            System.out.printf("%s%n", inputString);
            List<String> tokens = getTokens(inputString);
            System.out.println(tokens.size());
            System.out.println(tokens);

            List<String> postfix = Converter.toPostfix(tokens);
            System.out.println(postfix.size());
            System.out.println(postfix);
            System.out.println(Converter.Operar(postfix));



        }
    }

    public static boolean isOperator(String token) {
        if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^")){

            return true;
        }
        return false;
    }

    public static ArrayList<String> toPostfix(List<String> tokens) {

        Stack<String> stack = new Stack<>();
        ArrayList<String> output = new ArrayList<>();
        String t;

        for (String token : tokens) {
            if (token.equalsIgnoreCase("(")) {
                stack.push(token);
            } else if (token.equalsIgnoreCase(")")) {
                while (!(t = stack.pop()).equals("(")) {
                    System.out.println(token);
                    output.add(t);
                }
            } else if (isOperand(token)) {
                output.add(token);
            }
            if (isOperator(token)) {
                if ( stack.isEmpty() ) {
                    stack.push(token);
                } else {
                    int r1 = Converter.getPrec(token);
                    int r2 = Converter.getPrec( stack.peek() );

                    if( r1 > r2 ){
                        stack.push(token);
                    } else {
                        output.add(stack.pop());
                        stack.push(token);
                    }

                }
            }

        }
        String token;
        while ( !stack.isEmpty() ) {
            token = stack.pop();
            output.add(token);
        }
        return output;
    }

    public static boolean isOperand(String token) {
        boolean result = true;
        try {
            Double.parseDouble(token);
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }

    public static List<String> getTokens(String input) {

        StringTokenizer st = new StringTokenizer(input," ()+-*/^", true);

        ArrayList<String> tokenList = new ArrayList<>();
       while (st.hasMoreTokens()) {
           String token = st.nextToken();
           if(token.trim().length() == 0 ) {
               continue;
           }
           tokenList.add(token);
        }
        return tokenList;
    }

    public static int getPrec(String token) {
        String t = token.toLowerCase();
        int rank = 0;

        switch (t) {
            case "^":
                rank = 3;
                break;
            case "*":
            case "/":
                rank = 2;
                break;
            case "+":
            case "-":
                rank = 1;
                break;
        }

        return rank;
    }

    public static List<String> Operar(List<String> numeros) {

        Stack<Double> Operando = new Stack<>();
        List<String> Guardar = new ArrayList<>();

        for (String token : numeros) {
            if (isOperator(token)) {  // Si es un operador
                double segundoOperando = Operando.pop();
                double primerOperando = Operando.pop();
                double resultado = 0;

                switch (token) {
                    case "+":
                        resultado = primerOperando + segundoOperando;
                        break;
                    case "-":
                        resultado = primerOperando - segundoOperando;
                        break;
                    case "*":
                        resultado = primerOperando * segundoOperando;
                        break;
                    case "/":
                        resultado = primerOperando / segundoOperando;
                        break;
                    case "^":
                        resultado = Math.pow(primerOperando, segundoOperando);
                        break;
                    default:
                        throw new IllegalArgumentException("Operador no válido: " + token);
                }
                Operando.push(resultado);  // Almacena el resultado en la pila
            } else {  // Si es un número
                Operando.push(Double.parseDouble(token));
            }
        }

        Guardar.add(Double.toString(Operando.pop()));  // El último valor en la pila es el resultado final
        return Guardar;
    }

}


