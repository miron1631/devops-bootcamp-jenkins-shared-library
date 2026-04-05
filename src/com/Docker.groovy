#!/usr/bin/env groovy
package com.example

class Docker implements Serializable {
  
  def script

  Docker(script) {
    this.script = script
  }

  def buildImage(String imageName) {
    script.echo 'building the docker image...'
    script.sh "docker build -t $imageName ."
  }

  def login() {
    script.echo 'login to DockerHub...'
    script.withCredentials([
      script.usernamePassword(credentialsId: 'DockerHub', usernameVariable: 'USERNAME', passwordVariable: 'PWD')
    ]) {
      script.sh "echo $script.PWD | docker login -u $script.USERNAME --password-stdin"
    }
  }

  def publishImage(String imageName) {
    script.echo 'publishing the docker image...'
    script.sh "docker push $imageName"
  }
}