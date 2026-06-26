.PHONY: pdf clean help all

# Configuración
LATEXCOMPILER = xelatex
FLAGS = -shell-escape -interaction=nonstopmode
MAIN = main
OUTPUT = main.pdf

# Targets
help:
	@echo "================================"
	@echo "Informe LaTeX - Patrones Diseño"
	@echo "================================"
	@echo ""
	@echo "Targets disponibles:"
	@echo ""
	@echo "  make pdf       - Compilar el documento PDF"
	@echo "  make clean     - Limpiar archivos temporales"
	@echo "  make distclean - Limpiar todo incluyendo PDF"
	@echo "  make help      - Mostrar esta ayuda"
	@echo "  make all       - Compilar y limpiar temporales"
	@echo ""

all: pdf clean-temp

pdf:
	@echo "Compilando documento LaTeX..."
	$(LATEXCOMPILER) $(FLAGS) $(MAIN).tex
	@echo "Primera pasada completada..."
	$(LATEXCOMPILER) $(FLAGS) $(MAIN).tex
	@echo "Segunda pasada completada..."
	@echo ""
	@echo "✓ Documento generado: $(OUTPUT)"

clean-temp:
	@echo "Limpiando archivos temporales..."
	rm -f $(MAIN).aux $(MAIN).log $(MAIN).out $(MAIN).toc
	rm -f $(MAIN).lof $(MAIN).lot $(MAIN).minted*
	rm -f _minted-$(MAIN)/*
	@echo "✓ Archivos temporales eliminados"

clean: clean-temp

distclean: clean
	@echo "Eliminando PDF generado..."
	rm -f $(OUTPUT)
	rm -rf _minted-$(MAIN)
	@echo "✓ Limpieza completa realizada"

# Para desarrollo (compilar y abrir)
view: pdf
	@echo "Abriendo PDF..."
	@if command -v xdg-open >/dev/null 2>&1; then \
		xdg-open $(OUTPUT); \
	elif command -v open >/dev/null 2>&1; then \
		open $(OUTPUT); \
	else \
		echo "No se pudo abrir el PDF automáticamente"; \
	fi
