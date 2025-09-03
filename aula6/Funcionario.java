public abstract class Funcionario implements Bonificavel {
    protected String nome;
    protected String cpf;
    protected double salario;
    protected String departamento;
    
    public Funcionario(String nome, String cpf, double salario, String departamento) {
        this.nome = nome;
        this.cpf = cpf;
        this.salario = salario;
        this.departamento = departamento;
    }
    
    // Getters e Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public double getSalario() {
        return salario;
    }
    
    public void setSalario(double salario) {
        this.salario = salario;
    }
    
    public String getDepartamento() {
        return departamento;
    }
    
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    /**
     * Método abstrato para calcular salário total
     * @return salário total incluindo bônus
     */
    public abstract double calcularSalarioTotal();
    
    /**
     * Método abstrato para obter informações do funcionário
     * @return string com informações formatadas
     */
    public abstract String obterInformacoes();
    
    @Override
    public String toString() {
        return "Funcionario{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", salario=" + salario +
                ", departamento='" + departamento + '\'' +
                '}';
    }
}
