# ChessGame
## 🚀 Como Executar

### Execução Simples (Recomendado)
```bash
run.bat
```

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
