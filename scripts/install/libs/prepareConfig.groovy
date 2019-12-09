// create configuration as JSON
def json = new groovy.json.JsonBuilder()
json {
//  publicUrl config.publicUrl
  public_oci_test_url config.public_oci_test_url
  public_opc_url config.public_opc_url
}
// write configuration into file
new File(config.appHome, 'config.json').withWriter("UTF-8", json.&writeTo)
