package oci.test

import com.opuscapita.ocisetest.security.Encryptor
import org.apache.commons.codec.binary.Base64

class InboundTagLib {
    static defaultEncodeAs = [taglib: 'html']
    static namespace = "oc"

    def ociParamValue = { attrs ->
        def parameterValue = attrs.value as String

        if (attrs.containsKey('encryptor')) {
            def encryptor = attrs.encryptor as Encryptor
            out << new Date(Long.parseLong(encryptor.decrypt(parameterValue)))
        } else if (attrs.containsKey('decode')) {
            def decodedValue = Base64.Decoder.decode(parameterValue.getBytes('UTF-8')) as byte[]
            out << new String(decodedValue)
        } else {
            out << parameterValue
        }
    }

    def ociSet = { attrs ->
        pageScope."$attrs.var" = attrs.value ? new Encryptor(attrs.value as String) : null
    }
}
