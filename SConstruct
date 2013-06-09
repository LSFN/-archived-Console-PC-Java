"""
Main build file for LSFN's client program

Whilst you can alter various important things,
such as where the source files live and where the
class files go, it is recommended you don't.
"""

# Program level constants
SOURCE_DIRECTORY = 'src/'
BUILD_DIRECTORY = 'build/'
LIB_DIRECTORY = 'lib/'
OUTPUT_JAR_FILENAME = 'console-pc.jar'

# Protocol buffer:
PROTO_FILES = ['src/STS.proto']

# Scons has a concept of a build 'environment', so this is needed:
# The two parameters tell it to load the default tools, as well as protoc
# Protoc.py is located in this directory, so we need to add that to the toolpath
env = Environment(tools = ['default', 'protoc'], toolpath = '.')

# task to make the build directory exist (if it doesn't already):
make_build_directory = env.Command(BUILD_DIRECTORY, 
								   None, 
								   Mkdir(BUILD_DIRECTORY))

# task for actually doing the java build:
java_build = env.Java(target = BUILD_DIRECTORY, source = SOURCE_DIRECTORY)

# task for producing console-pc.jar:
jar_build = env.Jar(target = OUTPUT_JAR_FILENAME, 
					source = [BUILD_DIRECTORY, LIB_DIRECTORY])

# protoc build:
protoc_files = env.ProtocJava(target = [], source = PROTO_FILES)

# Tell scons that one must build the java files before JARing them:
env.Depends(jar_build, java_build)

# and one must do protoc before building source:
env.Depends(java_build, protoc_files)

# Tell scons that the default action is to do the jar build:
env.Default(jar_build)
