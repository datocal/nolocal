workspace {

    model {
        user = person "User" "A discord user"
        discord = softwareSystem "Discord" "Discord official system" "Discord"

        nolocal = softwareSystem "NoLocal" "Discord bot system"{
            caddy = container "Caddy" "Reverse proxy with SSL" "Open source webserver written in Go, verifies Ed25519 signature" "Caddy"
            redis = container "Redis" "Data Store" "Open source in memory data store" "Redis"
            s3fs = container "S3FS" "Engine to mount S3 like buckets" "FUSE-based file system backed by Amazon S3"
            volume = container "File system" "Mounted storage to save snapshots in" "" "Volume"
            bucket = container "Bucket" "S3-like bucket using Oracle Object Storage" "" "Bucket"

            nolocalapp = container "NoLocalApp" "Provides commands to execute" "Kotlin & Spring boot bot application" "NoLocalApp"{
                model = component "model"
                application = component "application"
                infrastructure = component "infrastructure"
                discordponent = component "discord"

                application -> model "Manage use cases using"
                application -> infrastructure "Stores data & communicates with discord using"
                infrastructure -> discordponent "Communicates with discord using"
                discordponent -> discord "Register commands using"
                caddy -> discordponent "Sends commands using"
                discordponent -> infrastructure "Send commands using"
                infrastructure -> application "Respond commands using"
                infrastructure -> redis "Stores data using"
            }
        }

        user -> discord "Send command using"
        discord -> caddy "Respond commands using"
        caddy -> nolocalapp "Redirects traffic using"
        nolocalapp -> discord "Register commands using"
        nolocalapp -> redis "Stores data using"
        redis -> volume "Persists snapshots using"
        volume -> s3fs "Synchronizes its data using"
        s3fs -> bucket "Syncronizes volume using"


        deploymentEnvironment "Live" {
            deploymentNode "Oracle Cloud"{
                deploymentNode "nolocal-instance" "" "Ubuntu 20.04 LTS"{
                    containerinstance caddy
                    containerinstance nolocalapp
                    containerinstance redis
                    containerinstance volume
                    containerinstance s3fs
                }
                deploymentNode "Oracle Object Storage"{
                    containerinstance bucket
                }
            }
            deploymentNode "Discord System"{
                softwaresysteminstance discord
            }
        }



        deploymentEnvironment "Development" {

            deploymentNode "Developer's Computer" "" "Windows/Ubuntu/MacOS...."{
                containerinstance nolocalapp
                containerinstance redis
            }
            deploymentNode "Discord System"{
                softwaresysteminstance discord
            }
        }


    }

    views {
        systemLandscape nolocal "SystemLandscape" {
            include *
            autoLayout
        }

        container nolocal "Containers" {
            include *
            autoLayout
        }
        component nolocalapp "Components"{
            include *
            autoLayout
        }

        deployment nolocal "Live"{
            include *
            autoLayout
        }
        deployment nolocal "Development"{
            include *
            autoLayout
        }

        styles {
            element "Software System" {
                background #1168bd
                color #ffffff
            }
            element "Discord" {
                background #7289da
                color #ffffff
            }
            element "Person" {
                shape person
                background #08427b
                color #ffffff
            }
            element "Caddy"{
                background #0A7906
                color #ffffff
            }
            element "Redis"{
                background #A41E11
                color #ffffff
            }
            element "Volume" {
                shape Cylinder
                background #999999
            }
            element "NoLocalApp"{
                shape RoundedBox
                background #1168bd
                color #ffffff
            }
            element "Bucket"{
                shape Pipe
                background #1168bd
                color #ffffff
            }
        }
    }

}