# ChessGame

Jogo de xadrez desenvolvido em Java com interface gráfica moderna, temas claro/escuro e **IA Neural Avançada** para jogar contra o computador.

## 🧠 Sistema de IA Neural

### Níveis de Dificuldade
- **Muito Fácil (Nível 2)**: Movimentos aleatórios com lógica básica
- **Fácil (Nível 4)**: Algoritmo minimax com profundidade 4
- **Médio (Nível 7)**: IA neural com heurísticas avançadas
- **Difícil (Nível 10)**: IA neural com análise profunda e estratégia complexa

### Tecnologias da IA
- **Rede Neural**: Arquitetura multicamada com 6 camadas ocultas
- **Minimax com Poda Alfa-Beta**: Otimização para análise eficiente
- **Heurísticas Avançadas**: Material, posição, mobilidade, estrutura de peões, segurança do rei, táticas
- **Função de Ativação Sigmoid**: Processamento neural otimizado

## 🎮 Características do Jogo

- Interface gráfica moderna com temas claro e escuro
- Sistema de histórico de jogadas interativo
- Múltiplos temas de tabuleiro (5 opções)
- Suporte completo às regras do xadrez (roque, en passant, promoção)
- IA adaptativa com diferentes níveis de desafio

## 🚀 Como Executar

### Execução Simples (Recomendado)
```bash
run.bat
```
**Este comando faz tudo automaticamente:**
- ✅ Compila todo o projeto na ordem correta
- ✅ Verifica erros de compilação
- ✅ Executa o jogo com IA Neural
- ✅ Mostra progresso detalhado

### Compilação Manual (Avançado)
```bash
# Se preferir compilar manualmente
compile.bat

# Depois executar
java -cp bin view.ChessGUI
```

## 📁 Estrutura do Projeto

```
src/
├── ai/                    # Sistema de IA Neural
│   └── NeuralNetworkAI.java
├── controller/            # Lógica do jogo
│   └── Game.java
├── model/                 # Modelos das peças e tabuleiro
│   ├── board/
│   └── pieces/
└── view/                  # Interface gráfica
    ├── ChessGUI.java
    └── ImageUtil.java
```

## 🎯 Melhorias Implementadas

- ✅ **IA Neural Avançada**: Substituição da IA simples por rede neural
- ✅ **4 Níveis de Dificuldade**: Do iniciante ao expert
- ✅ **Interface Atualizada**: Seleção visual de dificuldade
- ✅ **Performance Otimizada**: Poda alfa-beta e ordenação de movimentos
- ✅ **Heurísticas Avançadas**: Análise de posição, mobilidade e táticas
