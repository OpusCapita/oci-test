const crypto = require('crypto');

const PASSWORD_PARAM = "password";
const VALIDITY_INTERVAL_PARAM = "validityInterval";
const HOOK_URL_PARAM = "HOOK_URL";

const encryptAll = (fields, secretKey, noEncryptNames) => {
  const validityInterval = Number(fields.find(({ id }) => id === VALIDITY_INTERVAL_PARAM).value);
  const result = fields.map(({ id, value }) => {
    if (id.toUpperCase() === HOOK_URL_PARAM) {
      return { id, value: `${value}/${secretKey}` };
    }
    if (noEncryptNames.includes(id)) {
      return { id, value };
    }
    if (id === PASSWORD_PARAM) {
      value = `${value} ${new Date(Date.now() + validityInterval * 1000).valueOf()}`;
    }
    return { id, value: encrypt(value, secretKey) };
  });
  return result;
};

const descryptAll = (fields, secretKey, noEncryptNames) => fields.map(({ id, value }) => {
  if (noEncryptNames.includes(id) || id.toUpperCase() === HOOK_URL_PARAM) {
    return { id, value };
  }
  return { id, value: decrypt(value, secretKey) };
})

const algorithm = 'des-ecb';

const encrypt = (value, secretKey) => {
  const cipher = crypto.createCipheriv(algorithm, secretKey, null);
  return cipher.update(value, 'utf8', 'base64') + cipher.final('base64');
};

const decrypt = (value, secretKey) => {
  const decipher = crypto.createDecipheriv(algorithm, secretKey, null);
  return decipher.update(value, 'base64', 'utf8') + decipher.final('utf8');
};

module.exports = encryptAll;