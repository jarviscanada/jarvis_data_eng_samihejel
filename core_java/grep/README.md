# Introduction
This app is a Java implementation of the linux 'grep' command. This implementation allows users of other OS to use the grep command. The app was coded with Java, built using Maven, and deployed
Discuss the design of each app. What does the app do? What technologies have you used? (e.g. core java, libraries, lambda, IDE, docker, etc..)

# Quick Start
Docker: 
1. Download the docker image from https://hub.docker.com/r/samihejel/grep
2. Create and run the container from the image: ```docker run --rm \ -v `pwd`/data:/data -v `pwd`/out:/out jrvs/grep \ ${regex_pattern} ${src_dir} /out/${outfile}```
3. The result will be located in the out directory

#Implemenation
## Pseudocode
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)

## Performance Issue
The only glaring performance issue is when it comes to very large files we might run out of memory, I've gone ahead and used some functional programming using Streams and Lambda expressions to alleviate this, but we can also try allowing more memory to the JVM.

# Test
Tested with different files, files structures, used logger to make sure nothing was wrong.

# Deployment
Deployed to https://hub.docker.com/r/samihejel/grep

# Improvement
- Add a GUI for ease of use
- Allow grepping of multiple files at the same time
- Further optimize functions to minimize memory usage
