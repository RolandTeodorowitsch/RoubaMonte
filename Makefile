FILES=	README.md\
	README.TXT\
	package.bluej\
	package-list\
	Makefile\
	Main.java\
	Baralho.java\
	Carta.java\
	Figura.java\
	Naipe.java\
	RoubaMonte.java

all:		run

compile:
		@javac *.java

run:		compile
		@java RoubaMonte

javadoc:
		@javadoc *.java

init:
		@git init
		@ssh-keygen -t rsa -C "Roland Teodorowitsch"

add:
		@for i in ${FILES}; do git add $$i ; done

commit:		add
		@git commit -m "`date`"
		@git push -u origin master

clean:
		@rm -f *.class *.ctxt
