public class Gerente extends Funcionario {
    private int nivelHierarquico;
    private double comissao;
    
    public Gerente(String nome, String cpf, double salario, String departamento, 
                   int nivelHierarquico, double comissao) {
        super(nome, cpf, salario, departamento);
        this.nivelHierarquico = nivelHierarquico;
        this.comissao = comissao;
    }
    
    public int getNivelHierarquico() {
        return nivelHierarquico;
    }
    
    public void setNivelHierarquico(int nivelHierarquico) {
        this.nivelHierarquico = nivelHierarquico;
    }
    
    public double getComissao() {
        return comissao;
    }
    
    public void setComissao(double comissao) {
        this.comissao = comissao;
    }
    
    @Override
    public double calcularBonus() {
        // Bônus baseado no nível hierárquico e comissão
        return salario * (0.15 + (nivelHierarquico * 0.05)) + comissao;
    }
    
    @Override
    public double calcularSalarioTotal() {
        return salario + calcularBonus();
    }
    
    @Override
    public String obterInformacoes() {
        return String.format("GERENTE - Nome: %s, CPF: %s, Departamento: %s, " +
                           "Nível: %d, Salário: R$ %.2f, Bônus: R$ %.2f, Total: R$ %.2f",
                           nome, cpf, departamento, nivelHierarquico, salario, 
                           calcularBonus(), calcularSalarioTotal());
    }
    
    @Override
    public String toString() {
        return "Gerente{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", salario=" + salario +
                ", departamento='" + departamento + '\'' +
                ", nivelHierarquico=" + nivelHierarquico +
                ", comissao=" + comissao +
                '}';
    }
}
