<%--
  Created by IntelliJ IDEA.
  User: logich
  Date: 9/24/2019
  Time: 5:24 PM
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html><head><title>ItemSearchRequest Test Tool</title></head>

<body>

<g:form controller="ociTest" action="handleTester" method="POST">
    <h3>Mimic an Oracle Transparent Punchout Search Request</h3>

    URL to Test: <input type="text" name="urltotest" size="70"
                        value="http://test.jcatalog.com/dev-proc/opc/oracleTransparentPunchout/search"><br>

    <BR><input type="submit" name="submit"><BR>
    After clicking submit view source. You will see xml response there.

    <BR><BR>ItemSearchRequest xml:<BR>

    <textarea cols="80" rows="25" name="xmltosend"><?xml version="1.0" encoding="UTF-8"?>
        <DistributedSearchXML payloadID="123465678">
            <Header version="1.0">
                <From>
                    <Credential domain="DUNS">
                        <Identity>123456789</Identity>
                    </Credential>
                </From>
                <To>
                    <Credential domain="Name">
                        <Identity>jcadmin</Identity>
                    </Credential>
                </To>
                <Login>
                    <UserName>jcadmin</UserName>
                    <Password>jcadmin</Password>
                    <AuthenticatedKey>12345678</AuthenticatedKey>
                </Login>
            </Header>
            <Request>
                <ItemSearchRequest operation="SimpleSearch">
                    <UserInfo>
                        <UserName></UserName>
                        <AppsUserName>ppan</AppsUserName>
                        <UserPhone></UserPhone>
                        <UserEmail></UserEmail>
                    </UserInfo>
                    <SearchInfo>
                        <SearchLanguage>EN-US</SearchLanguage>
                        <SearchKeywords>blue pen</SearchKeywords>
                        <SortBy order="DESC">Price</SortBy>
                        <ResultSize>15</ResultSize>
                        <StartResult>1</StartResult>
                    </SearchInfo>
                    <UserArea>
                        <SupplementalInfo name="Group">OU1</SupplementalInfo>
                        <SupplementalInfo name="Division">NorthEast</SupplementalInfo>
                    </UserArea>
                </ItemSearchRequest>
            </Request>
        </DistributedSearchXML>
    </textarea>
    <P>
</g:form>

</body>
</html>
