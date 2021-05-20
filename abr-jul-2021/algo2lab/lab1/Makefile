CC = kotlinc
CFLAGS = -include-runtime -d

all: Main.kt Sortlib.kt
		$(CC) Main.kt $(CFLAGS) TestSort.jar

# Clean executables
.PHONY: clean
clean:
	rm *.jar
