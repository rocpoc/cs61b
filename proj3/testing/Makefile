# This makefile is defined to give you the following targets:
#
#    default: Same as check
#    make-check: Run integration tests on make application.
#    make-trip: Run integration tests on trip application.
#    check: Same as make-check + make-trip 
#    clean: Remove extraneous files.
#
# You can use this file without understanding most of it, of course, but
# I strongly recommend that you try to figure it out, and where you cannot,
# that you ask questions.  The Lab Reader contains documentation.

# To test parts of your code mixed with our solutions, use the following
# make options:
#
#   1. To test your make and trip packages with our graph package:
#         make GRAPHPACK=staff check     
#   2. To test your graph package with our trip package:
#         make TRIPPACK=staff check     
#   3. To test your graph package with our make package:
#         make MAKEPACK=staff check     
#
# This file looks for the staff solutions (staff-*.jar) in four places:
#
#   1. This directory.
#   2. The testing directory (one directory out from here).
#   3. The directory $MASTERDIR/lib (their location on the
#      instructional machines).
#   4. $HOME/cs61b-software/lib
#
# So you can always copy our staff-*.jar files from the instructional
# machines to this directory when operating at home without cs61b-software
# in the usual location.

SHELL = /bin/bash

HERE := $(shell pwd)

# If your python has a different name, do NOT change this line (you will
# fail our 'make check' tests.  Instead, if for example your python3 is
# simply called 'python', run 'make check' as
#      make PYTHON=python check
PYTHON = python3

GRAPH_CLASSES := $(HERE)/graph-classes
MAKE_CLASSES := $(HERE)/make-classes
TRIP_CLASSES := $(HERE)/trip-classes

ifeq ($(GRAPHPACK),staff)
$(info ***Using staff graph package***)
GRAPH_CLASSES = $(HERE)/staff-graph.jar
endif

ifeq ($(MAKEPACK),staff)
$(info ***Using staff make package***)
MAKE_CLASSES := $(HERE)/staff-make.jar
endif

ifeq ($(TRIPPACK),staff)
$(info ***Using staff trip package***)
TRIP_CLASSES := $(HERE)/staff-trip.jar
endif

MAKE_CLASSPATH = $(GRAPH_CLASSES):$(MAKE_CLASSES):$(CLASSPATH)

TRIP_CLASSPATH = $(GRAPH_CLASSES):$(TRIP_CLASSES):$(CLASSPATH)

TESTER := $(PYTHON) tester.py

TESTER_FLAGS =

.PHONY: default check clean make-check trip-check

# First, and therefore default, target.
default:
	$(MAKE) -C .. 
	$(MAKE) check

check: make-check trip-check

make-check: $(GRAPH_CLASSES) $(MAKE_CLASSES)
	@echo "Testing application make..."
	@cd make; CLASSPATH=$(MAKE_CLASSPATH) $(TESTER) $(TESTER_FLAGS) *.in

trip-check: $(GRAPH_CLASSES) $(TRIP_CLASSES)
	@echo "Testing application trip..."
	@cd trip; CLASSPATH=$(TRIP_CLASSPATH) $(TESTER) $(TESTER_FLAGS) *.in

$(HERE)/staff-graph.jar $(HERE)/staff-make.jar $(HERE)/staff-trip.jar:
	@base=$(notdir $@); if [ -f $(HERE)/../$$base ]; then \
	    $(RM) $@; \
	    ln -s ../$$base .; \
	elif [ -f $(MASTERDIR)/lib/$$base ]; then \
	    $(RM) $@; \
	    ln -s $(MASTERDIR)/lib/$$base .; \
	elif [ -f $(HOME)/cs61b-software/lib/$$base ]; then \
	    $(RM) $@; \
	    ln -s $(HOME)/cs61b-software/lib/$$base .; \
	else \
	    echo Could not find $$base; \
	fi

# 'make clean' will clean up stuff you can reconstruct.
clean:
	cd trip; $(RM) -r *~ __pycache__ 
	cd make; $(RM) -r *~ __pycache__ 
	$(RM) staff-*.jar

