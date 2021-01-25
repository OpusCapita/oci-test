/**
 * The script will be running before process installation
 */
new GroovyShell(new Binding([
        config: [
                appHome            : appHome,
                public_oci_test_url: getPublicServiceUrl("oci-test"),
                public_opc_url     : getPublicServiceUrl("opc")
        ]
])).evaluate(new File(appHome, "scripts/install/libs/prepareConfig.groovy"))

