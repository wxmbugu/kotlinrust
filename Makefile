# Build config : release or debug
config := release
#jar file loaction
jar_name := Kotlinrust.jar

#kotlin scripts
scripts := kotlin/rust.kt  kotlin/main.kt

jar_path := $(scripts)

kotlin:build 
	kotlinc $(scripts) -include-runtime -d $(jar_name)

build:
	cargo build --$(config)

.PHONY: run

run:kotlin
	java -Djava.library.path=target/$(config)/ -jar $(jar_name)

