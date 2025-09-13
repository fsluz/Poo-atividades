def ler_matrizes_do_arquivo(nome_arquivo):
    with open(nome_arquivo, 'r') as arquivo:
        linhas = arquivo.readlines()

    matriz_a = []
    matriz_b = []
    lendo_a = True

    for linha in linhas:
        linha = linha.strip()

        if not linha:
            continue

        if linha.lower().startswith("# matriz b"):
            lendo_a = False
            continue
        elif linha.lower().startswith("#"):
            continue  
        numeros = list(map(int, linha.split()))
        if lendo_a:
            matriz_a.append(numeros)
        else:
            matriz_b.append(numeros)

    return matriz_a, matriz_b

def eh_quadrada(matriz):
    linhas = len(matriz)
    for linha in matriz:
        if len(linha) != linhas:
            return False
    return True

def soma(a, b):
    return a + b

def multiplicacao(a, b):
    return a * b

def multiplicar_matrizes(matriz_a, matriz_b):
    tamanho = len(matriz_a)
    resultado = []

    print("\n🧮 Passo a passo da multiplicação:\n")

    for i in range(tamanho):
        linha_resultado = []
        for j in range(tamanho):
            soma_total = 0
            print(f"Elemento [{i}][{j}]: ", end="")
            for k in range(tamanho):
                mult = multiplicacao(matriz_a[i][k], matriz_b[k][j])
                soma_total = soma(soma_total, mult)
                print(f"{matriz_a[i][k]}*{matriz_b[k][j]}", end="")
                if k < tamanho - 1:
                    print(" + ", end="")
            print(f" = {soma_total}")
            linha_resultado.append(soma_total)
        resultado.append(linha_resultado)
    return resultado

def imprimir_matriz(matriz, nome):
    print(f"\n🔹 {nome}:")
    for linha in matriz:
        print("   ", linha)

arquivo = "matriz.txt"
matriz_a, matriz_b = ler_matrizes_do_arquivo(arquivo)

if not eh_quadrada(matriz_a) or not eh_quadrada(matriz_b):
    print("matriz a: ", matriz_a, 'matriz b:', matriz_b)
    print("❌ Uma ou ambas as matrizes não são quadradas.")
    exit()

if len(matriz_a) != len(matriz_b):
    print("❌ As matrizes não têm o mesmo tamanho.")
    exit()

imprimir_matriz(matriz_a, "Matriz A")
imprimir_matriz(matriz_b, "Matriz B")

resultado = multiplicar_matrizes(matriz_a, matriz_b)

imprimir_matriz(resultado, "Resultado (A x B)")
