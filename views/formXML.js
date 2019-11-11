document.getElementById('do-button').addEventListener('click', () => {
    const urltotest = document.forms[0].urltotest.value;
    const xmltosend = document.forms[0].xmltosend.textContent;
    fetch('/sendxml', {
        method: "POST",
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            urltotest,
            xmltosend,
        }),
    });
});