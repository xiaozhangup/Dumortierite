all: assemble

clj-module/target/clj-module-0.1.0.jar: clj-module/project.clj
	cd clj-module && bash refresh.sh && LEIN_SNAPSHOTS_IN_RELEASE=1 lein jar

build/libs/Dumortierite-0.1.0.jar: build.gradle
	gradle build

assemble: clj-module/target/clj-module-0.1.0.jar build/libs/Dumortierite-0.1.0.jar
	mkdir -p assemble/original
	mkdir -p assemble/this
	unzip build/libs/Dumortierite-0.1.0.jar -d assemble/original
	unzip clj-module/target/clj-module-0.1.0.jar -d assemble/this
	cp -rf assemble/this/* assemble/original
	cd assemble/original/ && zip -r ../Dumortierite-assembled-0.1.0.jar *

dest: assemble
	cp -rf assemble/Dumortierite-assembled-0.1.0.jar ${MC_SERVER_PLUGIN_DIR}/Dumortierite-assembled-0.1.0.jar
	rm assemble/Dumortierite-assembled-0.1.0.jar

.PHONY: clean
clean:
	rm -rf assemble
	cd clj-module && lein clean
	rm -rf clj-module/target
	gradle clean

