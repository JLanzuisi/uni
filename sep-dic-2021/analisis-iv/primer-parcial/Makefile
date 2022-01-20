TEXFILE = main.tex
PSFILE = main.ps
LATEX = texfot latexmk
LATEXFLAGS = -latex -ps

ps: $(TEXFILE)
	$(LATEX) $(LATEXFLAGS) $(TEXFILE)

pdf: $(TEXFILE) $(PSFILE)
	ps2pdf $(PSFILE)