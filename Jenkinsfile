@Library(value='kids-first/aws-infra-jenkins-shared-libraries', changelog=false) _
ecs_service_type_1_standard {
    projectName            = "d3b-disease-express"
    orgFullName            = "d3b-center"
    org                    = "d3b"
    account                = "chopd3bPrd"
    environments           = "prd,qa,service"
    docker_image_type      = "debian"
    entrypoint_command     = "nginx"
    deploy_scripts_version = "master"
    container_port         = "80"
    health_check_path      = "/entrypoint.sh"
}
