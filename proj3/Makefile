# This makefile is defined to give you the following targets:
#
#    default: The default target: Compiles the program in package db61b.
#    style: Run our style checker on the project source files.  Requires that
#           the source files compile.
#    check: Compiles the db61b package, if needed, and then performs the
#           tests described in testing/Makefile.
#    clean: Remove regeneratable files (such as .class files) produced by
#           other targets and Emacs backup files.
#
# In other words, type 'make' to compile everything; 'make check' to 
# compile and test everything, and 'make clean' to clean things up.
# 
# You can use this file without understanding most of it, of course, but
# I strongly recommend that you try to figure it out, and where you cannot,
# that you ask questions.  The Lab Reader contains documentation.

STYLEPROG = style61b

# Targets that don't correspond to files, but are to be treated as commands.
.PHONY: default check clean style

default:
	$(MAKE) -C graph default
	$(MAKE) -C make default
	$(MAKE) -C trip default
	$(MAKE) -C grader default

check: unit integration

unit:
	$(MAKE) -C graph unit
	$(MAKE) -C make unit
	$(MAKE) -C trip unit

integration:
	$(MAKE) -C graph integration
	$(MAKE) -C make integration
	$(MAKE) -C trip integration

grader: default
	java -ea grader.UnitTest

style:
	$(MAKE) -C $(PACKAGE) STYLEPROG=$(STYLEPROG) style

# 'make clean' will clean up stuff you can reconstruct.
clean:
	$(RM) *~ 
	$(MAKE) -C graph clean
	$(MAKE) -C make clean
	$(MAKE) -C trip clean
	$(MAKE) -C testing clean


