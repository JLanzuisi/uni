KC = kotlinc
KFLAGS = -include-runtime
SRC = Sortlib.kt Main.kt
PROG = TestSort.jar

all: $(SRC)
	$(KC) $(SRC) $(KFLAGS) -d $(PROG)

# Clean executables
.PHONY: clean
clean:
	rm $(PROG)
