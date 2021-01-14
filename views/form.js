var encryptedLS = 'oci-test-form-encrypted';
var ie7LS = 'oci-test-form-ie7-mode';
var enableScrollLS = 'oci-test-form-enable-scroll';
var fieldsList = document.getElementById('fields-list');
var addButtons = document.getElementsByClassName('add');
var saveButtons = document.getElementsByClassName('save');
var backButtons = document.getElementsByClassName('back');
var secretKey = document.getElementsByName('secretKey')[0];
var validityInterval = document.getElementsByName('validityInterval')[0];
var useEncryption = document.getElementById('useEncryption');
var useIE7mode = document.getElementById('useIE7mode');
var enableScrollbar = document.getElementById('enableScrollbar');
var form = document.forms[0];
var errorMessage = 'Unexpected error';

function getFieldInner(fieldName) {
  return ' <div class="row input-group input-group-sm mb-2">\n' +
    '   <div class="col-2 input-group-prepend">\n' +
    '     <span class="input-group-text w-100">' + fieldName + '</span>\n' +
    '   </div>\n' +
    '   <input name="${fieldName}" class="form-control input-value" type="text">\n' +
    '   <div class="input-group-append">\n' +
    '    <button type="button" class="btn btn-sm btn-danger remove-button">X</button>\n' +
    '   </div>\n' +
    '  </div>'
}

function getMessageInner(message) {
  return '<div class="message p-4">\n' +
    '  <div style="display: flex">\n' +
    '    <div style="margin: auto">\n' +
    '    ' + message + '\n' +
    '    </div>\n' +
    '  </div>\n' +
    '</div>'
}

function getFields() {
  return Array.prototype.slice.call(fieldsList.childNodes)
    .map(function (li) {
      var inputs = li.querySelectorAll('input');
      return {
        id: inputs[0].name,
        value: inputs[0].value,
      }
    });
}

function getCheckboxs() {
  return Array.prototype.slice.call(document.querySelectorAll('input[type=checkbox]')).map(
    function (checkbox) {
      return {
        id: checkbox.name,
        value: checkbox.value,
      }
    });
}

function createMessage(message) {
  var node = document.createElement('div');
  node.style = {position: 'fixed'};
  node.innerHTML = getMessageInner(message);
  document.body.appendChild(node);
  setTimeout(function () {
    node.remove();
  }, 2000);
}

function saveForm(name, fields) {
  fetch('./form?name=' + name, {
    method: 'POST',
    body: JSON.stringify(fields),
    headers: {'Content-Type': 'application/json'},
  })
    .then(function (res) {
      console.log(res.ok ? 'Form has been saved' : errorMessage);
      createMessage(res.ok ? 'Form has been saved' : errorMessage);
    })
}

function addField(fieldName) {
  var node = document.createElement('li');
  node.innerHTML = getFieldInner(fieldName);
  node.querySelectorAll('button')[0].addEventListener('click', function () {
    fieldsList.removeChild(node)
  });
  fieldsList.appendChild(node);
}

function hideEncryption() {
  secretKey && (secretKey.parentNode.parentNode.style.display = 'none');
  validityInterval && (validityInterval.parentNode.parentNode.style.display = 'none');
}

function showEncryption() {
  secretKey && (secretKey.parentNode.parentNode.style.display = 'block');
  validityInterval && (validityInterval.parentNode.parentNode.style.display = 'block');
}

function encrypt(fields) {
  return fetch('./encrypt', {
    method: 'POST',
    body: JSON.stringify(fields),
    headers: {'Content-Type': 'application/json'},
  })
    .then(function (res) {
      if (res.ok) {
        return res.json()
      }
      createMessage(errorMessage);
    });
}

function storeChecked() {
  localStorage.setItem(encryptedLS, useEncryption.checked);
  localStorage.setItem(ie7LS, useIE7mode.checked);
  localStorage.setItem(enableScrollLS, enableScrollbar.checked);
}

function restoreChecked() {
  localStorage.getItem(encryptedLS) === 'true' && (useEncryption.checked = true);
  localStorage.getItem(ie7LS) === 'true' && (useIE7mode.checked = true);
  localStorage.getItem(enableScrollLS) === 'true' && (enableScrollbar.checked = true);
}

Array.prototype.slice.call(addButtons).forEach(
  function (btn) {
    btn.addEventListener('click', function () {
      var fieldName = prompt('Enter field name');
      fieldName && addField(fieldName);
    })
  }
);

Array.prototype.slice.call(saveButtons).forEach(
  function (btn) {
    btn.addEventListener('click', function (e) {
      var name = document.getElementById('name').innerHTML;
      var fields = getFields();
      if (fields.some(function (field) {
        field.id === ''
      })) {
        alert('Field name can\'t be empty');
      } else {
        saveForm(name, fields);
      }
    })
  });

Array.prototype.slice.call(backButtons).forEach(
  function (btn) {
    btn.addEventListener('click', function () {
      history.back()
    })
  }
);

Array.prototype.slice.call(document.getElementsByClassName('remove-button')).forEach(
  function (button) {
    button.addEventListener('click', function (e) {
      fieldsList.removeChild(e.target.parentNode.parentNode.parentNode);
    })
  }
);

form.action = form.catalog_url.value;

form.catalog_url.addEventListener('change', function (e) {
  form.action = e.target.value;
});

useEncryption.addEventListener('click', function (e) {
  e.target.checked ? showEncryption() : hideEncryption();
  storeChecked();
});

useIE7mode.addEventListener('click', function () {
  storeChecked();
})

enableScrollbar.addEventListener('click', function () {
  storeChecked();
})

function setInputInvalidMode(input) {
  input.className = 'form-control input-value invalid-input';
}

function setInputDefaultMode(input) {
  input.className = 'form-control input-value';
}

function showInputError(input, message) {
  setInputInvalidMode(input);
  createMessage(message);

  function onChange(e) {
    setInputDefaultMode(input);
    input.removeEventListener('change', onChange);
  }

  input.addEventListener('change', onChange);
}

function validateEncryptionMode() {
  try {
    if (secretKey.value.length === 0) {
      showInputError(secretKey, 'secretKey cannot be empty string');
      return false;
    }
    if (secretKey.value.length !== 8) {
      showInputError(secretKey, 'secretKey must be 8 characters');
      return false;
    }
    if (validityInterval.value.length === 0) {
      showInputError(validityInterval, 'validityInterval cannot be empty string')
      return false;
    }
    if (!Number.isInteger(+validityInterval.value)) {
      showInputError(validityInterval, 'validityInterval must be a nubmer')
      return false;
    }
  } catch (e) {
    console.log(e);
    createMessage('Unexpected error')
    return false;
  }
  return true;
}

form.addEventListener('submit', function (e) {
  if (e.target.useEncryption.checked) {
    if (validateEncryptionMode()) {
      encrypt(getFields().concat(getCheckboxs()))
        .then(function (res) {
          res.forEach(function (field) {
            form[field.id].value = field.value;
          });
          form.submit();
        });
    }
    e.preventDefault();
  }
});

restoreChecked();
useEncryption.checked ? showEncryption() : hideEncryption();
