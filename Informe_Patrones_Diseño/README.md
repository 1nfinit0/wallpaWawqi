# Informe: Análisis de Patrones de Diseño - WallpaWawqi

Este directorio contiene un informe LaTeX completo que presenta el análisis detallado de los patrones de diseño implementados en el backend del proyecto WallpaWawqi.

## Contenido del Informe

El informe incluye:

1. **Introducción**: Contexto y objetivos del análisis
2. **Estructura del Proyecto**: Organización arquitectónica del backend
3. **Patrones de Diseño Identificados**:
   - Template Method Pattern
   - Strategy Pattern
   - Service Layer Pattern
   - Generic Types Pattern
   - Functional Programming Pattern
   - Dependency Injection (Implícito)
4. **Análisis Integrado**: Cómo interactúan los patrones
5. **Evaluación y Recomendaciones**: Mejoras sugeridas
6. **Conclusiones**: Resumen y aplicabilidad
7. **Apéndices**: Referencias y recursos

## Características

✅ Documentación profesional con portada y tabla de contenidos
✅ Ejemplos de código con sintaxis destacada (minted)
✅ Diagramas conceptuales ASCII
✅ Análisis detallado de cada patrón
✅ Recomendaciones de mejora con ejemplos
✅ Referencias bibliográficas

## Requisitos de Compilación

### Opción 1: Con TexLive (Linux/Mac)

```bash
# Instalar TexLive con soporte para minted
sudo apt-get install texlive-full python3-pygments

# Compilar el documento
pdflatex -shell-escape main.tex

# O mejor aún, usar xelatex o lualatex
xelatex -shell-escape main.tex
```

### Opción 2: Con MiKTeX (Windows)

1. Instalar [MiKTeX](https://miktex.org/)
2. Instalar Python y Pygments: `pip install pygments`
3. Compilar: `xelatex -shell-escape main.tex`

### Opción 3: Usar Overleaf Online

1. Crear una nueva cuenta en [Overleaf](https://www.overleaf.com/)
2. Crear un proyecto en blanco
3. Copiar el contenido de `main.tex`
4. Compilar en el navegador

## Pasos de Compilación Manual

```bash
# Navegar al directorio
cd Informe_Patrones_Diseño

# Compilar (primera pasada)
xelatex -shell-escape main.tex

# Compilar (segunda pasada para TOC)
xelatex -shell-escape main.tex

# Resultado
# Se generará: main.pdf
```

## Estructura de Archivos

```
Informe_Patrones_Diseño/
├── main.tex              # Documento principal (este archivo)
├── README.md             # Instrucciones (este archivo)
└── (el PDF se genera aquí después de compilar)
```

## Personalización

### Cambiar Tema de Código

En `main.tex`, busca la línea:
```latex
\usemintedstyle{monokai}
```

Opciones disponibles: `default`, `emacs`, `friendly`, `colorful`, `autumn`, `murphy`, `pastie`, `perldoc`, `solarized`, `vim`, `vs`, `xcode`

### Ajustar Márgenes

Modifica la sección `geometry`:
```latex
\geometry{
    left=2.5cm,      % Cambiar
    right=2.5cm,     % Cambiar
    top=2.5cm,       % Cambiar
    bottom=2.5cm     % Cambiar
}
```

### Cambiar Espaciado

Busca `\onehalfspacing` y reemplaza con:
- `\singlespacing` para sencillo
- `\doublespacing` para doble

## Troubleshooting

### Error: "minted.sty not found"

```bash
# Instalar minted
tlmgr install minted
```

### Error: "Pygments not found"

```bash
# Instalar Pygments
pip install pygments
```

### El PDF se genera pero sin código coloreado

Asegúrate de compilar con `-shell-escape`:
```bash
xelatex -shell-escape main.tex
```

### Rutas de código no funcionan

Las líneas de código se referencian desde la raíz del proyecto. Si el PDF no encuentra los archivos `.java`, verifica que:

1. Los archivos existan en las rutas indicadas
2. O modifica las rutas en `main.tex` para que sean relativas a donde compiles

## Genera el PDF

```bash
# Compilación rápida (Linux/Mac)
xelatex -shell-escape main.tex && xelatex -shell-escape main.tex
```

## Visualizar el Resultado

Una vez compilado, abre `main.pdf` con tu lector PDF preferido.

## Notas Adicionales

- El documento está en **español** completamente
- Usa la fuente **Courier New** para código monoespaciado
- El código se resalta con el estilo **Monokai**
- Las páginas tienen encabezados y pies de página personalizados
- Incluye índice automático (table of contents)

## Autor

Informe generado como análisis arquitectónico del proyecto WallpaWawqi

---

**Última actualización**: 2026-06-12
