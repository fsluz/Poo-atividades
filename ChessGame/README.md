# ChessGame
## 🚀 Como Executar

### Execução Simples (Recomendado)
```bash
run.bat
```

### Compilação Manual (Avançado)
```bash
REM Criar diretorio bin se nao existir
if not exist "bin" mkdir bin

javac -d bin src\model\board\Position.java
javac -d bin -cp bin src\model\board\Board.java
javac -d bin -cp bin src\model\pieces\Piece.java
javac -d bin -cp bin src\model\pieces\King.java
javac -d bin -cp bin src\model\pieces\Queen.java
javac -d bin -cp bin src\model\pieces\Rook.java
javac -d bin -cp bin src\model\pieces\Bishop.java
javac -d bin -cp bin src\model\pieces\Knight.java
javac -d bin -cp bin src\model\pieces\Pawn.java
javac -d bin -cp bin src\model\board\Move.java
javac -d bin -cp bin src\controller\Game.java
javac -d bin -cp bin src\ai\NeuralNetworkAI.java
javac -d bin -cp bin src\view\ImageUtil.java
javac -d bin -cp bin src\view\ChessGUI.java
echo    Compilacao concluida com sucesso!
java -cp bin view.ChessGUI
REM Criar diretorio bin se nao existir
if not exist "bin" mkdir bin

javac -d bin src\model\board\Position.java
javac -d bin -cp bin src\model\board\Board.java
javac -d bin -cp bin src\model\pieces\Piece.java
javac -d bin -cp bin src\model\pieces\King.java
javac -d bin -cp bin src\model\pieces\Queen.java
javac -d bin -cp bin src\model\pieces\Rook.java
javac -d bin -cp bin src\model\pieces\Bishop.java
javac -d bin -cp bin src\model\pieces\Knight.java
javac -d bin -cp bin src\model\pieces\Pawn.java
javac -d bin -cp bin src\model\board\Move.java
javac -d bin -cp bin src\controller\Game.java
javac -d bin -cp bin src\ai\NeuralNetworkAI.java
javac -d bin -cp bin src\view\ImageUtil.java
javac -d bin -cp bin src\view\ChessGUI.java
echo    Compilacao concluida com sucesso!

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
