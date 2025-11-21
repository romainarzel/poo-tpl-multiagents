# ============= CONFIG ==================

SRC_DIR = src
BIN_DIR = bin
LIBS    = lib/gui.jar
CP      = $(BIN_DIR):$(LIBS)

# ------------- SPECIFIC_SRC ------------
INVADER_SRC = $(SRC_DIR)/TestInvader.java 
BALLS_SRC = $(SRC_DIR)/TestBalls.java 
GRAVITYBALL_SRC = $(SRC_DIR)/TestGravityBall.java 
CELLULARSIM_SRC = $(SRC_DIR)/cellularSim/*.java
SWARMSIM_SRC = $(SRC_DIR)/SwarmSim/*.java
# ============= RULES ===================

all: build

# Compile ALL sources in src/ (preserves package structure)
build:
	javac -d $(BIN_DIR) -classpath $(LIBS) -sourcepath $(SRC_DIR) $(SRC_DIR)/**/*.java

# ================== COMPILER ===================
SPECIFIC_COMPILE_CMD = javac -d $(BIN_DIR) -classpath $(LIBS) 

compileInvader: 
	 $(SPECIFIC_COMPILE_CMD) $(INVADER_SRC) 
compileTestBalls: 
	 $(SPECIFIC_COMPILE_CMD) $(BALLS_SRC) 
compileTestGravityBall: 
	 $(SPECIFIC_COMPILE_CMD) $(GRAVITYBALL_SRC) 
compileCellularSim: 
	 $(SPECIFIC_COMPILE_CMD) $(CELLULARSIM_SRC) 
compileSwarmSim: 
	 $(SPECIFIC_COMPILE_CMD) $(SWARMSIM_SRC) 
# ================== RUNNERS ====================
RUN_CMD = java -classpath $(CP)

runInvader: compileInvader
	$(RUN_CMD) TestInvader

runTestBalls: compileTestBalls
	$(RUN_CMD) TestBalls

runTestGravityBall: compileTestGravityBall
	$(RUN_CMD) TestGravityBall

runGameOfLife: compileCellularSim
	$(RUN_CMD) cellularSim.GameOfLife

runGameOfImmigration:  compileCellularSim
	$(RUN_CMD) cellularSim.GameOfImmigration

runSegregationSim:  compileCellularSim
	$(RUN_CMD) cellularSim.SegregationSim

runSwarm: compileSwarmSim
	$(RUN_CMD) SwarmSim.SwarmSim

# ================== HOUSEKEEPING ================

clean:
	rm -rf $(BIN_DIR)/*


# ================== DOCUMENTATION ================

javadoc:
	@echo "Génération de la documentation Javadoc..."
	mkdir -p doc/javadoc  
	javadoc -d doc/javadoc -sourcepath src -subpackages cellularSim:SwarmSim -classpath lib/gui.jar -encoding UTF-8 -charset UTF-8 -docencoding UTF-8 -author -version -Xdoclint:none -windowtitle "Multi-Agent Simulation" -doctitle "Documentation du projet Multi-Agents" -header "Simulations Multi-Agents" -bottom "Projet POO - Automates Cellulaires et Boids"
	@echo "Javadoc générée avec succès dans doc/javadoc/index.html"
	@echo "open in browser doc/javadoc/index.html"
	xdg-open doc/javadoc/index.html

.PHONY: javadoc clean
