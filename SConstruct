"""
Main build file for LSFN's client program

Whilst you can alter various important things,
such as where the source files live and where the
class files go, it is recommended you don't.
"""

SOURCE_DIRECTORY = 'src/'
BUILD_DIRECTORY = 'build/'

# Scons has a concept of a build 'environment', so this is needed:
env = Environment()

# task to make the build directory exist (if it doesn't already):
make_build_directory = env.Command(BUILD_DIRECTORY, 
								   None, 
								   Mkdir(BUILD_DIRECTORY))

# task for actually doing the java build:
java_build = env.Java(BUILD_DIRECTORY, SOURCE_DIRECTORY)

# Tell scons that the default action is to do the java build:
env.Default(java_build) 
