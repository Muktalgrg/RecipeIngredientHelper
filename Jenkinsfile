#!/usr/bin/env groovy

pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    stages {
        
        stage('increment version'){
            steps{
                script{
                    echo "incrementing app version...."
                    sh 'mvn build-helper:parse-version versions:set \
                        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion}\
                        versions:commit'
                    def matcher =  readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1] //1.1.1
                    env.IMAGE_NAME = "$version-$BUILD_NUMBER"  // appending build number which is unique to the project 
                }
            }
        }
        
        
        stage('build app') {
            steps {
                script {
                    echo "building the application ...."
                    sh 'mvn clean package'
                }
            }
        }
        
        stage('build image') {
            steps {
                script {
                    echo "building the docker image..."
                    withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
//                         sh "docker build -t muktagurung/recipe-ingredient-helper:${IMAGE_NAME} ."

                        sh "docker build --platform linux/amd64 -t muktagurung/recipe-ingredient-helper:${IMAGE_NAME} ."
                        sh "echo $PASS | docker login -u $USER --password-stdin"
                        sh "docker push muktagurung/recipe-ingredient-helper:${IMAGE_NAME}"
                    }  
                }   
            }
        }

        stage("deploy") {
            steps {
                script {
                    echo 'deploying docker image to EC2 ...'
                    def dockerCmd = "docker run -p 8081:8081 muktagurung/recipe-ingredient-helper:${IMAGE_NAME}"
                    // def dockerCmd = "sudo docker run -p 8081:8081 muktagurung/recipe-ingredient-helper:2.6.6-28"
                    
                    sshagent(['ec2-server-key']) {
                        // sh "ssh -o StrictHostKeyChecking=no ec2-user@13.54.32.183 ${dockerCmd}"
                        sh "ssh -o StrictHostKeyChecking=no ec2-user@3.27.173.145 ${dockerCmd}"
                    }
                }
            }
        }
        
        // stage("deploy") {
        //     steps {
        //         script {
        //             def dockerCmd = 'docker run -p 3080:3080 -d muktagurung/demo-app:1.0'
        //             sshagent(['ec2-server-key']) {
        //                 sh "ssh -o StrictHostKeyChecking=no ec2-user@13.54.32.183 ${dockerCmd}"
        //             }
        //         }
        //     }
        // }

/*
        stage('commit version update'){
            steps{
                script{
                    withCredentials([usernamePassword(credentialsId: 'gitlab', passwordVariable: 'PASS', usernameVariable: 'USER')]){
                        // as an alternative, we can use ssh and set mail n username there
                        
                        sh 'git config --global user.email "jenkins@example.com"'
                        sh 'git config --global user.name "jenkins"'
                        
                        //just for info, can be deleted later

                        sh 'git status'
                        sh 'git branch'
                        sh 'git config --list'
                        // pom file is changed and ready to be committed.
                       
                        sh "git remote set-url origin https://${USER}:${PASS}@gitlab.com/muktagurung/practisejenkins.git"
                        // set url to below content
                        sh 'git add .'
                        sh 'git commit -m "ci: version bump"'
                        sh 'git push origin HEAD:main'
                    }
                }
            }
        }
*/
        
    }   
}