@echo off
echo ========================================
echo    ChessGame - Compilacao e Execucao
echo ========================================
echo.

REM Criar diretorio bin se nao existir
if not exist "bin" mkdir bin

echo [1/6] Compilando Position.java...
javac -d bin src\model\board\Position.java
if %ERRORLEVEL% NEQ 0 (
    echo [ERRO] Falha ao compilar Position.java
    pause
    exit /b 1
)

echo [2/6] Compilando Board.java...
javac -d bin -cp bin src\model\board\Board.java
if %ERRORLEVEL% NEQ 0 (
    echo [ERRO] Falha ao compilar Board.java
    pause
    exit /b 1
)

echo [3/6] Compilando Piece.java e pecas...
javac -d bin -cp bin src\model\pieces\Piece.java
if %ERRORLEVEL% NEQ 0 (
    echo [ERRO] Falha ao compilar Piece.java
    pause
    exit /b 1
)

javac -d bin -cp bin src\model\pieces\King.java
javac -d bin -cp bin src\model\pieces\Queen.java
javac -d bin -cp bin src\model\pieces\Rook.java
javac -d bin -cp bin src\model\pieces\Bishop.java
javac -d bin -cp bin src\model\pieces\Knight.java
javac -d bin -cp bin src\model\pieces\Pawn.java
if %ERRORLEVEL% NEQ 0 (
    echo [ERRO] Falha ao compilar pecas
    pause
    exit /b 1
)

echo [4/6] Compilando Move.java e Game.java...
javac -d bin -cp bin src\model\board\Move.java
javac -d bin -cp bin src\controller\Game.java
if %ERRORLEVEL% NEQ 0 (
    echo [ERRO] Falha ao compilar Game.java
    pause
    exit /b 1
)

echo [5/6] Compilando IA Neural...
javac -d bin -cp bin src\ai\NeuralNetworkAI.java
if %ERRORLEVEL% NEQ 0 (
    echo [ERRO] Falha ao compilar NeuralNetworkAI.java
    pause
    exit /b 1
)

echo [6/6] Compilando Interface...
javac -d bin -cp bin src\view\ImageUtil.java
javac -d bin -cp bin src\view\ChessGUI.java
if %ERRORLEVEL% NEQ 0 (
    echo [ERRO] Falha ao compilar interface
    pause
    exit /b 1
)

echo.
echo ========================================
echo    Compilacao concluida com sucesso!
echo ========================================
echo.
echo Iniciando jogo de Xadrez com IA Neural...
echo.

REM Executar o jogo
java -cp bin view.ChessGUI

echo.
echo Jogo finalizado.
pause
