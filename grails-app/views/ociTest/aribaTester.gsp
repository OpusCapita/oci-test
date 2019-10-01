<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html><head><title>PunchoutSetupRequest Test Tool</title></head>

<body>

<g:form controller="ociTest" action="handleTester" method="POST">
    <h3>Mimic an Ariba Punchout Setup Request</h3>

    URL to Test: <input type="text" name="urltotest" size="70"
                        value="http://test.jcatalog.com/dev-proc/opc/ariba/setup"><br>
    <BR>
    Enter the supplier ACSN ID (the tester on ACSN sends the ID instead of the DUNS) and Password below<br>


    <BR><input type="submit" name="submit"><BR>
    After clicking submit view source. You will see cxml response there.
    <BR> Notice the cXML is not inside a FORM variable if send by Ariba Buyer/ACSN.

    <BR><BR>Cxml PunchoutSetupRequest:<BR>

    <textarea cols="80" rows="25" name="xmltosend"><?xml version="1.0" encoding="UTF-8"?>
        <!DOCTYPE cXML SYSTEM "http://xml.cXML.org/schemas/cXML/1.1.009/cXML.dtd">
        <cXML timestamp="2000-11-10T05:26:11-08:00" payloadID="973862771707--4512288394343725421@206.251.25.165"
              version="1.1.009">
            <Header>
                <From>
                    <Credential domain="NetworkID">
                        <Identity>jcadmin</Identity>
                    </Credential>
                </From>
                <To>
                    <Credential domain="NetworkID">
                        <Identity>jcadmin</Identity>
                    </Credential>
                </To>
                <Sender>
                    <Credential domain="LoginName">
                        <Identity>jcadmin</Identity>
                        <SharedSecret>jcadmin</SharedSecret>
                    </Credential>
                    <UserAgent>CatalogTester</UserAgent>
                </Sender>
            </Header>
            <Request deploymentMode="test">
                <PunchOutSetupRequest operation="create">
                    <BuyerCookie>cyUV6QSY9gs1ajf8</BuyerCookie>
                    <Extrinsic name="User">john_smith</Extrinsic>
                    <Extrinsic name="CostCenter">610</Extrinsic>

                    <BrowserFormPost>
                        <URL>http://localhost:8080/oci-test/ariba-test/show_cXML.jsp</URL>
                    </BrowserFormPost>
                </PunchOutSetupRequest>
            </Request>
        </cXML>
    </textarea>
    <P>
</g:form>

</body>
</html>
