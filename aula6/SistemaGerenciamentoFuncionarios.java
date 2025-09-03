import java.util.ArrayList;
import java.util.List;

public class SistemaGerenciamentoFuncionarios {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GERENCIAMENTO DE FUNCIONÁRIOS ===\n");
        
        // Criando funcionários de diferentes tipos
        Gerente gerente1 = new Gerente("João Silva", "123.456.789-00", 8000.0, "TI", 3, 500.0);
        Gerente gerente2 = new Gerente("Maria Santos", "987.654.321-00", 7500.0, "RH", 2, 300.0);
        
        Estagiario estagiario1 = new Estagiario("Pedro Costa", "111.222.333-44", 1200.0, "TI", "UFMG", 8, true);
        Estagiario estagiario2 = new Estagiario("Ana Oliveira", "555.666.777-88", 1000.0, "Marketing", "PUC", 4, false);
        
        Desenvolvedor dev1 = new Desenvolvedor("Carlos Lima", "999.888.777-66", 6000.0, "TI", "Java", 5, true);
        Desenvolvedor dev2 = new Desenvolvedor("Lucia Ferreira", "444.333.222-11", 5500.0, "TI", "Python", 3, false);
        
        // Criando uma lista polimórfica de funcionários
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(gerente1);
        funcionarios.add(gerente2);
        funcionarios.add(estagiario1);
        funcionarios.add(estagiario2);
        funcionarios.add(dev1);
        funcionarios.add(dev2);
        
        // DEMONSTRAÇÃO 1: Polimorfismo com método calcularBonus()
        System.out.println("1. DEMONSTRAÇÃO DE POLIMORFISMO - MÉTODO calcularBonus():");
        System.out.println("=" .repeat(80));
        
        for (Funcionario funcionario : funcionarios) {
            System.out.printf("Bônus de %s: R$ %.2f%n", 
                            funcionario.getNome(), funcionario.calcularBonus());
        }
        
        // DEMONSTRAÇÃO 2: Polimorfismo com método calcularSalarioTotal()
        System.out.println("\n2. DEMONSTRAÇÃO DE POLIMORFISMO - MÉTODO calcularSalarioTotal():");
        System.out.println("=" .repeat(80));
        
        for (Funcionario funcionario : funcionarios) {
            System.out.printf("Salário total de %s: R$ %.2f%n", 
                            funcionario.getNome(), funcionario.calcularSalarioTotal());
        }
        
        // DEMONSTRAÇÃO 3: Polimorfismo com método obterInformacoes()
        System.out.println("\n3. DEMONSTRAÇÃO DE POLIMORFISMO - MÉTODO obterInformacoes():");
        System.out.println("=" .repeat(80));
        
        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario.obterInformacoes());
            System.out.println();
        }
        
        // DEMONSTRAÇÃO 4: Polimorfismo com interface Bonificavel
        System.out.println("4. DEMONSTRAÇÃO DE POLIMORFISMO - INTERFACE Bonificavel:");
        System.out.println("=" .repeat(80));
        
        List<Bonificavel> bonificaveis = new ArrayList<>();
        bonificaveis.add(gerente1);
        bonificaveis.add(estagiario1);
        bonificaveis.add(dev1);
        
        for (Bonificavel bonificavel : bonificaveis) {
            if (bonificavel instanceof Funcionario) {
                Funcionario func = (Funcionario) bonificavel;
                System.out.printf("Funcionário %s (via interface): Bônus = R$ %.2f%n", 
                                func.getNome(), bonificavel.calcularBonus());
            }
        }
        
        // DEMONSTRAÇÃO 5: Cálculos estatísticos usando polimorfismo
        System.out.println("\n5. ESTATÍSTICAS DA EMPRESA (usando polimorfismo):");
        System.out.println("=" .repeat(80));
        
        double totalSalarios = 0;
        double totalBonus = 0;
        double maiorSalario = 0;
        String funcionarioMaiorSalario = "";
        
        for (Funcionario funcionario : funcionarios) {
            totalSalarios += funcionario.getSalario();
            totalBonus += funcionario.calcularBonus();
            
            if (funcionario.calcularSalarioTotal() > maiorSalario) {
                maiorSalario = funcionario.calcularSalarioTotal();
                funcionarioMaiorSalario = funcionario.getNome();
            }
        }
        
        System.out.printf("Total de funcionários: %d%n", funcionarios.size());
        System.out.printf("Total de salários base: R$ %.2f%n", totalSalarios);
        System.out.printf("Total de bônus: R$ %.2f%n", totalBonus);
        System.out.printf("Total geral: R$ %.2f%n", totalSalarios + totalBonus);
        System.out.printf("Maior salário total: %s com R$ %.2f%n", 
                         funcionarioMaiorSalario, maiorSalario);
        System.out.printf("Média de salários: R$ %.2f%n", totalSalarios / funcionarios.size());
        System.out.printf("Média de bônus: R$ %.2f%n", totalBonus / funcionarios.size());
        
        // DEMONSTRAÇÃO 6: Teste de métodos específicos de cada classe
        System.out.println("\n6. TESTE DE MÉTODOS ESPECÍFICOS:");
        System.out.println("=" .repeat(80));
        
        // Testando métodos específicos do Gerente
        System.out.println("Testando métodos específicos do Gerente:");
        System.out.printf("Nível hierárquico de %s: %d%n", 
                         gerente1.getNome(), gerente1.getNivelHierarquico());
        System.out.printf("Comissão de %s: R$ %.2f%n", 
                         gerente1.getNome(), gerente1.getComissao());
        
        // Testando métodos específicos do Estagiario
        System.out.println("\nTestando métodos específicos do Estagiário:");
        System.out.printf("Instituição de %s: %s%n", 
                         estagiario1.getNome(), estagiario1.getInstituicao());
        System.out.printf("Período de %s: %d%n", 
                         estagiario1.getNome(), estagiario1.getPeriodo());
        System.out.printf("Bolsa de estudo de %s: %s%n", 
                         estagiario1.getNome(), estagiario1.isBolsaEstudo() ? "Sim" : "Não");
        
        // Testando métodos específicos do Desenvolvedor
        System.out.println("\nTestando métodos específicos do Desenvolvedor:");
        System.out.printf("Linguagem de %s: %s%n", 
                         dev1.getNome(), dev1.getLinguagemProgramacao());
        System.out.printf("Anos de experiência de %s: %d%n", 
                         dev1.getNome(), dev1.getAnosExperiencia());
        System.out.printf("Certificado de %s: %s%n", 
                         dev1.getNome(), dev1.isCertificado() ? "Sim" : "Não");
        
        System.out.println("\n=== SISTEMA TESTADO COM SUCESSO! ===");
    }
}
