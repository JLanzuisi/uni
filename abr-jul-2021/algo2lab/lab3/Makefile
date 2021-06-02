KC = kotlinc
KFLAG = -cp
SRC = Sortlib.kt PlotRuntime.kt SwingJfxDemoFrontendContext.kt Main.kt
CLASSES = SwingJfxDemoFrontendContext.class PlotRuntimeKt.class
LIBDIR = lib

all: $(SRC) $(CLASSES)
	$(KC) $(KFLAG) . Sortlib.kt Main.kt

SwingJfxDemoFrontendContext.class: SwingJfxDemoFrontendContext.kt
	$(KC) $(KFLAG) $(LIBDIR)/lets-plot-jfx-2.0.2.jar:$(LIBDIR)/lets-plot-kotlin-api-2.0.1.jar SwingJfxDemoFrontendContext.kt

PlotRuntimeKt.class: PlotRuntime.kt
	$(KC) $(KFLAG) $(LIBDIR)/lets-plot-kotlin-api-2.0.1.jar:. PlotRuntime.kt

# Clean executables
.PHONY: clean
clean:
	rm -r *.png *.class META-INF
