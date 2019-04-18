import jenkins.model.Jenkins
Jenkins j = Jenkins.instance

if(!j.isQuietingDown()) {
    def job_dsl_security = j.getExtensionList('javaposse.jobdsl.plugin.GlobalJobDslSecurityConfiguration')[0]
    if(job_dsl_security.useScriptSecurity) {
        job_dsl_security.useScriptSecurity = false
        println 'Job DSL script security has changed.  It is now disabled.'
        job_dsl_security.save()
        j.save()
    }
    else {
        println 'Nothing changed.  Job DSL script security already disabled.'
    }
}
else {
    println 'Shutdown mode enabled.  Configure Job DSL script security SKIPPED.'
}




job('Seed-Job') {
    description('Seed-Job')
    environmentVariables {
        keepBuildVariables(true)
        keepSystemVariables(true)
    }
    scm {
        git {
            remote {
                url("https://github.com/ndccroyals/devops-pipeline.git")
                credentials("ndccroyals:Royals211016")
            }
            branch('master')
        }
    }
    triggers {
        scm('H/30 * * * *')
        cron('H/60 H/24 * * *')
    }
    concurrentBuild(false)

    steps {
        dsl {
            external "*.groovy"
            removeAction('DELETE')
            removeViewAction('DELETE')
            ignoreExisting(false)
         }
    }
}
