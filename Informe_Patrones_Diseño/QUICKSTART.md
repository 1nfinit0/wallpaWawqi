# 🚀 Inicio Rápido

## Compilar el Informe en 30 Segundos

### Opción 1: Script Automático (Recomendado)

```bash
cd Informe_Patrones_Diseño
./compile.sh --view
```

Esto:
- ✅ Verifica requisitos
- ✅ Compila el documento
- ✅ Abre el PDF automáticamente

### Opción 2: Usar Make

```bash
cd Informe_Patrones_Diseño
make pdf
make view
```

### Opción 3: Manual

```bash
cd Informe_Patrones_Diseño
xelatex -shell-escape main.tex
xelatex -shell-escape main.tex
```

---

## Requisitos Previos

### Linux (Debian/Ubuntu)

```bash
# Instalar TeX y Pygments
sudo apt update
sudo apt install -y texlive-full
pip install pygments
```

### macOS

```bash
# Usar Homebrew
brew install mactex
pip install pygments
```

### Windows

Descarga e instala:
1. [MiKTeX](https://miktex.org/download)
2. Desde PowerShell: `pip install pygments`

---

## Verificar Instalación

```bash
# Verificar xelatex
xelatex --version

# Verificar pygments
python3 -c "import pygments; print('Pygments OK')"
```

---

## Solucionar Problemas

### "command not found: xelatex"

→ Instala TexLive completo (ver Requisitos Previos)

### "Pygments not found"

→ Ejecuta: `pip install --user pygments`

### PDF generado pero sin colores en código

→ Compila con `-shell-escape`:
```bash
xelatex -shell-escape main.tex
```

---

## Personalizar Antes de Compilar

Edita `main.tex` para cambiar:

```latex
% Línea 38 - Cambiar tema de código
\usemintedstyle{monokai}    ← Opciones: vim, friendly, solarized, etc.

% Línea 49 - Cambiar márgenes
\geometry{ left=2.5cm, ... }

% Línea 56 - Cambiar espaciado
\onehalfspacing              ← O \singlespacing, \doublespacing
```

---

## Estructura Final

Después de compilar:

```
Informe_Patrones_Diseño/
├── main.tex              ✓ Documento principal
├── main.pdf              ✓ RESULTADO
├── README.md             ✓ Documentación completa
├── QUICKSTART.md         ✓ Este archivo
├── compile.sh            ✓ Script de compilación
├── Makefile              ✓ Targets de make
└── _minted-main/         ✓ Temporales (se puede eliminar)
```

---

## Contenido del Informe

📖 El PDF incluye:

- ✅ Portada profesional
- ✅ Tabla de contenidos automática
- ✅ 7 Capítulos de análisis
- ✅ 6 Patrones de diseño explicados
- ✅ Ejemplos de código con sintaxis
- ✅ Diagramas conceptuales
- ✅ Recomendaciones de mejora
- ✅ Referencias bibliográficas

---

## Próximos Pasos

1. **Compila** con: `./compile.sh --view`
2. **Revisa** el PDF generado
3. **Personaliza** si necesitas cambios
4. **Comparte** `main.pdf` con tu equipo

---

## Tips Profesionales

### Para Imprimir

```bash
# Optimizar tamaño para impresión
pdftotext main.pdf -
pdfinfo main.pdf
```

### Hacer Cambios y Recompilar

```bash
# Script con limpieza automática
./compile.sh --clean --view

# O usa Make
make distclean pdf view
```

### Integrar con Git

```bash
# Ignorar temporales
echo "_minted-*/" >> .gitignore
echo "main.pdf" >> .gitignore

# Commitar solo fuentes
git add main.tex README.md compile.sh
git commit -m "docs: informe de patrones de diseño"
```

---

**¡Listo! Tu informe profesional está a un comando de distancia** 🎉
