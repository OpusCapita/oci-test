var OCI_ENCRYPTED_COOKIE_NAME = "oci-encrypted";
var IE7_MODE_COOKIE_NAME = "ie7-mode";
var ENABLE_SCROLL_COOKIE_NAME = "enable-scroll";

function changeEncryptionVisibility() {
    var validityIntervalBlock = jQuery('#validityInterval')[0];
    var secretKeyBlock = jQuery('#secretKey')[0];
    if (getCookie(OCI_ENCRYPTED_COOKIE_NAME) === 'true' && validityIntervalBlock !== undefined) {
        validityIntervalBlock.style.display = 'table-row';
        secretKeyBlock.style.display = 'table-row';
    } else if (validityIntervalBlock !== undefined) {
        validityIntervalBlock.style.display = 'none';
        secretKeyBlock.style.display = 'none';
    }
}

function storeChecked() {
    setCookie(OCI_ENCRYPTED_COOKIE_NAME, document.getElementById("useEncryption").checked);
    setCookie(IE7_MODE_COOKIE_NAME, document.getElementById("useIE7mode").checked);
    setCookie(ENABLE_SCROLL_COOKIE_NAME, document.getElementById("enableScrollbar").checked);
}

function restoreChecked() {
    if (getCookie(OCI_ENCRYPTED_COOKIE_NAME) === "true") {
        document.getElementById("useEncryption").checked = true;
    }
    if (getCookie(IE7_MODE_COOKIE_NAME) === "true") {
        document.getElementById("useIE7mode").checked = true;
    }
    if (getCookie(ENABLE_SCROLL_COOKIE_NAME) === "true") {
        document.getElementById("enableScrollbar").checked = true;
    }
}

window.onload = function () {
    restoreChecked();
    changeEncryptionVisibility();
};
