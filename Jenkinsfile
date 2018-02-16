node("cxs-slave-master") {
    echo sh(returnStdout: true, script: 'env')

    configFileProvider(
        [configFile(fileId: '37cb206e-6498-4d8a-9b3d-379cd0ccd99b',  targetLocation: 'settings.xml')]) {
	    sh 'mkdir -p ~/.m2 && sed -i "s|@LOCAL_REPO_PATH@|$WORKSPACE/M2_REPO|g" $WORKSPACE/settings.xml && cp $WORKSPACE/settings.xml -f ~/.m2/settings.xml'
    }

    stage ('Checkout') {
        checkout scm
    }

    stage ('Versioning') {
        sh "mvn versions:set -DnewVersion=${env.MAJOR_VERSION_NUMBER}-${env.BUILD_NUMBER} -DprocessDependencies=false -DprocessParent=true -Dmaven.test.skip=true"
    }

    stage ('Build') {
        sh "mvn clean install -DskipTests=true"
    }

    stage ('Archive') {
        archiveArtifacts artifacts: 'target/media-plugin-vad-noise-threshold-*.jar'
    }

    stage ('Deploy') {
        if(env.PUBLISH_TO_CXS_NEXUS == 'true') {
            sh "mvn clean install package deploy:deploy -Pattach-sources,generate-javadoc,maven-release -DskipTests=true -DskipNexusStagingDeployMojo=true -DaltDeploymentRepository=nexus::default::$CXS_NEXUS2_URL"
        } else {
            echo 'Skipped deployment to CXS Nexus'
        }
    }

    stage('Tag') {
        if(env.PUBLISH_TO_SONATYPE == 'true') {
            withCredentials([usernamePassword(credentialsId: 'CXSGithub', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                sh 'git commit -a -m "New release candidate"'
                sh "git tag ${env.MAJOR_VERSION_NUMBER}-${env.BUILD_NUMBER}"
                sh('git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/RestComm/media-plugin-vad-noise-threshold.git --tags')
            }
        } else {
            echo 'Skipped code tagging'
        }
    }

    stage ('Release') {
        if(env.PUBLISH_TO_SONATYPE == 'true') {
            sh "mvn -fn clean deploy -Dgpg.passphrase=${env.GPG_PASSPHRASE} -Pattach-sources,generate-javadoc,release-sign-artifacts,cxs-oss-release"
        } else {
            echo 'Skipped deployment to Sonatype'
        }
    }

}