src_dir = src/
java_classes = ConsolePC.class StarshipConnection.class STS.class
java_package_dir = org/lsfn/console_pc/
protoc_src_dir = proto_src/
protoc_sources = STS.proto
lib_dir = lib/
lib_files = protobuf-java-2.5.0.jar
build_dir = build/

.PHONY: all

STS.java: $(protoc_src_dir)STS.proto
	protoc -I. --java_out=$(src_dir) $(protoc_src_dir)STS.proto

$(build_dir)$(java_package_dir)%.class: $(src_dir)$(java_package_dir)%.java
	javac -classpath $(lib_dir)$(libfiles) -d $(build_dir) $^

console-pc.jar: $(addprefix $(build_dir)$(java_package_dir),$(java_classes))
	jar cfm $@ Manifest.txt $(lib_dir) -C $(build_dir) .

all: console-pc.jar