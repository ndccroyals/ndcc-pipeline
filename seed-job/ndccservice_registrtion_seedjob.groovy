job('ndccservice_registration_seed_job') {
    description('Seed-Job')
    environmentVariables {
        keepBuildVariables(true)
        keepSystemVariables(true)
    }
    scm {
        git {
            remote {
                url("https://github.com/ndccroyals/ndcc-service-registration.git")
                credentials("ndccroyals:Royals211016")
            }
            branch('dev')
        }
    }
    triggers {
        scm('H/30 * * * *')
        cron('H/60 H/24 * * *')
    }
    concurrentBuild(false)

    steps {
               maven {
            mavenInstallation("M3")
            goals('-e clean test')
        }
    }
}
