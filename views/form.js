const encryptedLS = 'oci-test-form-encrypted';
const ie7LS = 'oci-test-form-ie7-mode';
const enableScrollLS = 'oci-test-form-enable-scroll';
const fieldsList = document.getElementById('fields-list');
const addButtons = document.getElementsByClassName('add');
const saveButtons = document.getElementsByClassName('save');
const doButtons = document.getElementsByClassName('do');
const backButtons = document.getElementsByClassName('back');
const secretKey = document.getElementsByName('secretKey')[0];
const validityInterval = document.getElementsByName('validityInterval')[0];
const useEncryption = document.getElementById('useEncryption');
const useIE7mode = document.getElementById('useIE7mode');
const enableScrollbar = document.getElementById('enableScrollbar');
const form = document.forms[0];

const getFieldInner = (fieldName) => `
  <div class="row input-group mb-3">
   <div class="col-2 input-group-prepend p-0">
     <span class="input-group-text w-100">${fieldName}</span>
   </div>
   <input name=${fieldName} class="form-control input-value" type="text">
   <div class="input-group-append">
    <button type="button" class="btn btn-danger remove-button">X</button>
   </div>
  </div>
`;

const getMessageInner = (message) => `
<div class="message p-4">
  <div style="display: flex">
    <div style="margin: auto">
    ${message}
    </div>
  </div>
</div>`

const getFields = () => {
  return Array.from(fieldsList.childNodes)
    .map(li => {
      const inputs = li.querySelectorAll('input');
      return {
        id: inputs[0].name,
        value: inputs[0].value,
      }
    });
};

const getCheckboxs = () =>
  Array.from(document.querySelectorAll('input[type=checkbox]')).map(checkbox => ({
    id: checkbox.name,
    value: checkbox.value,
  }));

const createMessage = (message) => {
  const node = document.createElement('div');
  node.style = { position: 'fixed' };
  node.innerHTML = getMessageInner(message);
  document.body.appendChild(node);
  setTimeout(() => {
    node.remove();
  }, 2000);
};

const saveForm = (name, fields) => {
  fetch(`./form?name=${name}`, {
    method: 'POST',
    body: JSON.stringify(fields),
    headers: { 'Content-Type': 'application/json' },
  })
    .then((res) => {
      console.log(res.ok ? 'Form has been saved' : res.statusText);
      createMessage(res.ok ? 'Form has been saved' : res.statusText);
    })
}

const addField = (fieldName) => {
  const node = document.createElement('li');
  node.innerHTML = getFieldInner(fieldName);
  node.querySelectorAll('button')[0].addEventListener('click', () => fieldsList.removeChild(node));
  fieldsList.appendChild(node);
}

const hideEncryption = () => {
  secretKey && (secretKey.parentNode.parentNode.style.display = 'none');
  validityInterval && (validityInterval.parentNode.parentNode.style.display = 'none');
};

const showEncryption = () => {
  secretKey && (secretKey.parentNode.parentNode.style.display = 'block');
  validityInterval && (validityInterval.parentNode.parentNode.style.display = 'block');
};

const encrypt = (fields) => {
  return fetch('./encrypt', {
    method: 'POST',
    body: JSON.stringify(fields),
    headers: { 'Content-Type': 'application/json' },
  })
    .then(res => {
      if (res.ok) {
        return res.json()
      }
      createMessage(res.statusText);
    });
};

const storeChecked = () => {
  localStorage.setItem(encryptedLS, useEncryption.checked);
  localStorage.setItem(ie7LS, useIE7mode.checked);
  localStorage.setItem(enableScrollLS, enableScrollbar.checked);
}

const restoreChecked = () => {
  localStorage.getItem(encryptedLS) === 'true' && (useEncryption.checked = true);
  localStorage.getItem(ie7LS) === 'true' && (useIE7mode.checked = true);
  localStorage.getItem(enableScrollLS) === 'true' && (enableScrollbar.checked = true);
}

Array.from(addButtons).forEach(btn => btn.addEventListener('click', () => {
  const fieldName = prompt('Enter field name');
  fieldName && addField(fieldName);
}));

Array.from(saveButtons).forEach(btn => btn.addEventListener('click', (e) => {
  const name = document.getElementById('name').innerHTML;
  const fields = getFields();
  if (fields.some(field => field.id === '')) {
    alert('Field name can\'t be empty');
  } else {
    saveForm(name, fields);
  }
}));

Array.from(backButtons).forEach(btn => btn.addEventListener('click', () => history.back()));

Array.from(document.getElementsByClassName('remove-button'))
  .forEach(button => button.addEventListener('click', (e) => {
    fieldsList.removeChild(e.target.parentNode.parentNode.parentNode);
  }));

form.action = form.catalog_url.value;

form.catalog_url.addEventListener('change', (e) => {
  form.action = e.target.value;
});

useEncryption.addEventListener('click', (e) => {
  e.target.checked ? showEncryption() : hideEncryption();
  storeChecked();
});

useIE7mode.addEventListener('click', () => {
  storeChecked();
})

enableScrollbar.addEventListener('click', () => {
  storeChecked();
})

form.addEventListener('submit', (e) => {
  if (e.target.useEncryption.checked) {
    encrypt(getFields().concat(getCheckboxs()))
      .then(res => {
        res.forEach(({ id, value }) => {
          form[id].value = value;
        });
        form.submit();
      });
    e.preventDefault();
  }
});

restoreChecked();
useEncryption.checked ? showEncryption() : hideEncryption();   
