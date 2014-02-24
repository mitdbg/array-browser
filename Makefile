JCC = javac
CP = lib/javaee-api-7.0.jar:.
JFLAGS = -g -cp $(CP) -d classes
.SUFFIXES: .java .class
RM = rm -rf
.java.class:
	$(JCC) $(JFLAGS) $*.java

CLASSES = \
        src/com/arraybrowser/ComputeProjection.java \
        src/com/arraybrowser/SelectExample.java \
        src/com/arraybrowser/ServletDemo1.java

default: classes

classes: $(CLASSES:.java=.class)

clean: 
	$(RM) classes/*



