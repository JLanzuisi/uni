CC = kotlinc
CFLAGS = -include-runtime -d

all: Main.kt Sortlib.kt Sortlib.jar
		$(CC) Main.kt $(CFLAGS) Main.jar

Sortlib.jar: Sortlib.kt
		$(CC) Sortlib.kt $(CFLAGS) Sortlib.jar

# Clean executables
.PHONY: clean
clean:
	rm *.jar
