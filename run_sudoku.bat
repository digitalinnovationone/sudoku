@echo off
REM Script para compilar e executar o Sudoku Java no Windows

REM Caminho para a pasta do projeto - ajuste se necessário
set PROJECT_DIR=%~dp0

echo Compilando fontes Java...
javac "%PROJECT_DIR%src\br\com\dio\model\*.java" "%PROJECT_DIR%src\br\com\dio\Main.java"
if errorlevel 1 (
    echo Erro na compilacao! Verifique seu codigo.
    pause
    exit /b 1
)

echo Executando o jogo...
java -cp "%PROJECT_DIR%src" br.com.dio.Main

echo Jogo finalizado.
pause
