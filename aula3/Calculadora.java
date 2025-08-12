/**
 * Esta classe representa uma calculadora simples.
 * Aqui temos as 4 operações básicas: soma, subtração, multiplicação e divisão.
 * Também tem uma parte de testes e uma interface gráfica básica feita com Swing.
 * 
 * @author Felipe
 * @version 1.0
 */
public class Calculadora {

    /**
     * Soma dois números.
     * @param a primeiro número
     * @param b segundo número
     * @return a soma dos dois números
     */
    public double somar(double a, double b) {
        return a + b;
    }

    /**
     * Subtrai o segundo número do primeiro.
     * @param a primeiro número
     * @param b segundo número
     * @return o resultado da subtração
     */
    public double subtrair(double a, double b) {
        return a - b;
    }

    /**
     * Multiplica dois números.
     * @param a primeiro número
     * @param b segundo número
     * @return o resultado da multiplicação
     */
    public double multiplicar(double a, double b) {
        return a * b;
    }

    /**
     * Divide o primeiro número pelo segundo.
     * Se o segundo número for zero, dá erro.
     * @param a numerador
     * @param b denominador
     * @return o resultado da divisão
     * @throws ArithmeticException se b for zero
     */
    public double dividir(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Não é possível dividir por zero!");
        }
        return a / b;
    }

    /**
     * Faz alguns testes básicos para verificar se a calculadora funciona.
     * Para funcionar, precisa rodar o programa com a opção -ea (enable assertions).
     */
    public static void rodarTestes() {
        Calculadora calc = new Calculadora();

        assert calc.somar(2, 3) == 5 : "Erro na soma";
        assert calc.subtrair(10, 4) == 6 : "Erro na subtração";
        assert calc.multiplicar(3, 5) == 15 : "Erro na multiplicação";
        assert calc.dividir(8, 2) == 4 : "Erro na divisão";

        try {
            calc.dividir(5, 0);
            assert false : "Deveria ter dado erro na divisão por zero";
        } catch (ArithmeticException e) {
            // Aqui é o esperado
        }

        System.out.println("✅ Testes passaram!");
    }

    /**
     * Cria e mostra a interface gráfica da calculadora.
     */
    public static void iniciarInterface() {
        Calculadora calc = new Calculadora();

        javax.swing.JFrame frame = new javax.swing.JFrame("Calculadora");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new java.awt.GridLayout(4, 2, 5, 5));

        javax.swing.JTextField num1 = new javax.swing.JTextField();
        javax.swing.JTextField num2 = new javax.swing.JTextField();
        javax.swing.JTextField resultado = new javax.swing.JTextField();
        resultado.setEditable(false);

        javax.swing.JButton btnSoma = new javax.swing.JButton("+");
        javax.swing.JButton btnSub = new javax.swing.JButton("-");
        javax.swing.JButton btnMult = new javax.swing.JButton("*");
        javax.swing.JButton btnDiv = new javax.swing.JButton("/");

        btnSoma.addActionListener(e -> resultado.setText(String.valueOf(
            calc.somar(Double.parseDouble(num1.getText()), Double.parseDouble(num2.getText()))
        )));

        btnSub.addActionListener(e -> resultado.setText(String.valueOf(
            calc.subtrair(Double.parseDouble(num1.getText()), Double.parseDouble(num2.getText()))
        )));

        btnMult.addActionListener(e -> resultado.setText(String.valueOf(
            calc.multiplicar(Double.parseDouble(num1.getText()), Double.parseDouble(num2.getText()))
        )));

        btnDiv.addActionListener(e -> {
            try {
                resultado.setText(String.valueOf(
                    calc.dividir(Double.parseDouble(num1.getText()), Double.parseDouble(num2.getText()))
                ));
            } catch (ArithmeticException ex) {
                javax.swing.JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(new javax.swing.JLabel("Número 1:"));
        frame.add(num1);
        frame.add(new javax.swing.JLabel("Número 2:"));
        frame.add(num2);
        frame.add(btnSoma);
        frame.add(btnSub);
        frame.add(btnMult);
        frame.add(btnDiv);
        frame.add(new javax.swing.JLabel("Resultado:"));
        frame.add(resultado);

        frame.setVisible(true);
    }

    /**
     * Método principal do programa.
     * Primeiro roda os testes, depois abre a interface gráfica.
     * @param args argumentos da linha de comando (não usados aqui)
     */
    public static void main(String[] args) {
        rodarTestes(); // Roda os testes
        javax.swing.SwingUtilities.invokeLater(Calculadora::iniciarInterface); // Abre a interface
    }
}
