"""
Main build file for LSFN's client program

Whilst you can alter various important things,
such as where the source files live and where the
class files go, it is recommended you don't.
"""

# tacky fix alert!
import os.path

# Program level constants
SOURCE_DIRECTORY = 'src/'
BUILD_DIRECTORY = 'build/'
LIB_DIRECTORY = 'lib/'

# By default, scons' Glob() function returns Scons nodes
# but as we need filenames, have to use this list comprehension.
# Calling str(<scons file node>) gives the filename:
LIB_DIRECTORY_CONTENTS = [str(s) for s in Glob('lib/*')]
OUTPUT_JAR_FILENAME = 'console-pc.jar'

# Protocol buffer:
PROTO_SOURCE_DIRECTORY = 'proto_src/'
PROTO_OUTPUT_DIRECTORY = SOURCE_DIRECTORY
# Find all of the .proto files
PROTO_FILES = [str(s) for s in Glob(PROTO_SOURCE_DIRECTORY + '/*.proto')]

# Create the build environment:
env = Environment(tools = ['default', 'protoc'], toolpath = ['tools'])

# protoc build (setting the output to be in PROTO_SOURCE_DIRECTORY):
protoc_files = env.ProtocJava(source = PROTO_FILES, 
                              PROTOCJAVAOUTDIR = PROTO_OUTPUT_DIRECTORY)

# task for actually doing the java build:
env.Append(JAVACLASSPATH = LIB_DIRECTORY_CONTENTS) # add lib to the classpath
CLASS_FILES = java_build = env.Java(target = BUILD_DIRECTORY, 
                                    source = SOURCE_DIRECTORY)

# Tacky fix for a large bug in somewhere in Scons' Java scanner:
# Check that all the class files that are purported to exist actually do:
for filenode in CLASS_FILES:
    if not os.path.exists(str(filenode)):
        # For some reason, Scons does not correctly determine
        # that STS.java has the same package as all the other
        # java source files
        CLASS_FILES.remove(filenode)

# task for producing console-pc.jar:
jar_build = env.Jar(target = OUTPUT_JAR_FILENAME, 
                    source = CLASS_FILES + [LIB_DIRECTORY, "Manifest.txt"])

# Tell scons that one must build the java files before JARing them:
env.Depends(jar_build, java_build)

# and one must do protoc before building source:
env.Depends(java_build, protoc_files)

# Tell scons that the default action is to do the jar build:
env.Default(jar_build)
