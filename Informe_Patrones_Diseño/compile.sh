#!/bin/bash

# Script para compilar el informe LaTeX
# Uso: ./compile.sh [opciones]
# Opciones:
#   --help          Mostrar esta ayuda
#   --clean         Limpiar antes de compilar
#   --view          Abrir el PDF después
#   --quiet         Modo silencioso

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
MAIN_FILE="main"
PDF_OUTPUT="main.pdf"
COMPILER="xelatex"
FLAGS="-shell-escape -interaction=nonstopmode"

# Colores
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Funciones
print_help() {
    echo -e "${BLUE}=== Compilador de Informe LaTeX ===${NC}"
    echo ""
    echo "Uso: $0 [opciones]"
    echo ""
    echo "Opciones:"
    echo "  --help       Mostrar esta ayuda"
    echo "  --clean      Limpiar archivos antes de compilar"
    echo "  --view       Abrir PDF después de compilar"
    echo "  --quiet      Modo silencioso (sin mensajes)"
    echo ""
    echo "Ejemplos:"
    echo "  $0                    # Compilar normalmente"
    echo "  $0 --clean --view     # Limpiar, compilar y abrir"
    echo ""
}

check_requirements() {
    echo -e "${BLUE}Verificando requisitos...${NC}"

    if ! command -v $COMPILER &> /dev/null; then
        echo -e "${RED}✗ Error: $COMPILER no está instalado${NC}"
        echo -e "${YELLOW}  Instala con: sudo apt install texlive-xetex${NC}"
        exit 1
    fi

    if ! python3 -c "import pygments" 2>/dev/null; then
        echo -e "${RED}✗ Error: Pygments no está instalado${NC}"
        echo -e "${YELLOW}  Instala con: pip install pygments${NC}"
        exit 1
    fi

    echo -e "${GREEN}✓ Todos los requisitos están instalados${NC}"
    echo ""
}

clean_files() {
    echo -e "${YELLOW}Limpiando archivos temporales...${NC}"
    rm -f $MAIN_FILE.aux $MAIN_FILE.log $MAIN_FILE.out $MAIN_FILE.toc
    rm -f $MAIN_FILE.lof $MAIN_FILE.lot
    rm -rf _minted-$MAIN_FILE
    echo -e "${GREEN}✓ Limpieza completada${NC}"
    echo ""
}

compile_document() {
    echo -e "${BLUE}Compilando documento...${NC}"
    echo ""

    echo -e "${YELLOW}Primera pasada...${NC}"
    if $COMPILER $FLAGS $MAIN_FILE.tex > /dev/null 2>&1; then
        echo -e "${GREEN}✓ Primera pasada completada${NC}"
    else
        echo -e "${RED}✗ Error en primera pasada${NC}"
        exit 1
    fi

    echo -e "${YELLOW}Segunda pasada (para tabla de contenidos)...${NC}"
    if $COMPILER $FLAGS $MAIN_FILE.tex > /dev/null 2>&1; then
        echo -e "${GREEN}✓ Segunda pasada completada${NC}"
    else
        echo -e "${RED}✗ Error en segunda pasada${NC}"
        exit 1
    fi

    if [ -f "$PDF_OUTPUT" ]; then
        FILE_SIZE=$(du -h "$PDF_OUTPUT" | cut -f1)
        echo -e "${GREEN}✓ PDF generado exitosamente: $PDF_OUTPUT ($FILE_SIZE)${NC}"
    else
        echo -e "${RED}✗ Error: PDF no fue generado${NC}"
        exit 1
    fi
    echo ""
}

view_pdf() {
    echo -e "${BLUE}Abriendo PDF...${NC}"
    if command -v xdg-open &> /dev/null; then
        xdg-open "$PDF_OUTPUT" 2>/dev/null &
    elif command -v open &> /dev/null; then
        open "$PDF_OUTPUT" 2>/dev/null &
    else
        echo -e "${YELLOW}No se pudo abrir automáticamente. Abre: $PDF_OUTPUT${NC}"
    fi
    echo ""
}

# Variables
QUIET=false
CLEAN=false
VIEW=false

# Parsear argumentos
while [[ $# -gt 0 ]]; do
    case $1 in
        --help)
            print_help
            exit 0
            ;;
        --clean)
            CLEAN=true
            shift
            ;;
        --view)
            VIEW=true
            shift
            ;;
        --quiet)
            QUIET=true
            shift
            ;;
        *)
            echo -e "${RED}Opción desconocida: $1${NC}"
            print_help
            exit 1
            ;;
    esac
done

# Ejecutar
cd "$SCRIPT_DIR"

if [ "$QUIET" = false ]; then
    print_help
fi

check_requirements

if [ "$CLEAN" = true ]; then
    clean_files
fi

compile_document

if [ "$VIEW" = true ]; then
    view_pdf
fi

echo -e "${GREEN}=== Compilación completada ===${NC}"
echo ""
echo "El informe está listo en: $PDF_OUTPUT"
