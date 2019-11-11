const express = require('express');
const createFormManger = require('./formManager.js');
const path = require('path');
const axios = require('axios');
const encryptAll = require('./encryptor.js');

const app = express();
const configPath = path.join(__dirname, 'config.json');
const starterConfigPath = path.join(__dirname, 'starterConfig.json');
const aribaTestPath = path.join(__dirname, 'aribaRequest.xml');
const otpTestPath = path.join(__dirname, 'otpRequest.xml');
const formManager = createFormManger();
const port = process.env.PORT || 3000;
const host = process.env.HOST || 'localhost';
const otpTestName = 'Mimic an Oracle Transparent Punchout Search Request';
const aribaTestName = 'Mimic an Ariba Punchout Setup Request';
const SECRET_KEY_PARAM = "secretKey";
const noEncryptFields = ['~CALLER', '~OkCode', 'FUNCTION', 'HOOK_URL', 'OCI_VERSION', '~TARGET'];

const { base_hook_url, base_catalog_url } = require('yargs').
  option('base_hook_url', { default: `http://${host}:${port}/hook` }).
  option('base_catalog_url', { default: 'https://demo.eproc.dev.opuscapita.com/eproc-line-deployment/eproc-review-tests' }).
  argv;

app.set('view engine', 'pug');

app.use(express.json());
app.use(express.urlencoded());

app.get('/', async (req, res, next) => {
  try {
    const result = await formManager.getFormsNames(configPath);
    res.render('index', { formsNames: result || [], });
  } catch (e) {
    next(e);
  }
});

app.get('/form', async (req, res, next) => {
  try {
    const result = await formManager.getForm(configPath, req.query.name);
    result.fields.find(f => f.id === 'HOOK_URL').value = base_hook_url;
    res.render('form', result);
  }
  catch (e) {
    next(e);
  }
});

app.post('/form', async (req, res, next) => {
  try {
    await formManager.editForm(configPath, req.query.name, req.body);
    res.end();
  }
  catch (e) {
    next(e);
  }
});

app.post('/hook(/:secretKey)?', (req, res, next) => {
  try {
    if (req.body['cxml-urlencoded']) {
      res.contentType('text/xml; charset=ISO-8859-1');
      res.send(req.body['cxml-urlencoded']);
    } else {
      res.render('hook', formManager.createResultForm('Hook', req.body));
    }
  } catch (e) {
    next(e);
  }
});

app.post('/encrypt', (req, res, next) => {
  try {
    const fields = Array.from(req.body)
    const secretKey = fields.find(({ id }) => id === SECRET_KEY_PARAM).value;
    res.json(encryptAll(fields, secretKey, noEncryptFields));
  } catch (e) {
    next(e);
  }
});

app.get('/aribaTester', async (req, res, next) => {
  try {
    const toRender = await formManager.getFormXML(aribaTestPath, aribaTestName);
    toRender.firstHint = `Enter the supplier ACSN ID (the tester on ACSN sends
       the ID instead of the DUNS) and Password below`;
    toRender.secondHint = `After clicking submit view source.
     You will see cxml response there.
    Notice the cXML is not inside a FORM variable if send by Ariba Buyer/ACSN.`;
    toRender.urltotest = base_catalog_url + '/opc/ariba/setup';
    res.render('formXML', toRender);
  } catch (e) {
    next(e);
  }
});

app.get('/otpTester', async (req, res, next) => {
  try {
    const toRender = await formManager.getFormXML(otpTestPath, otpTestName);
    toRender.secondHint = 'After clicking submit view source. You will see xml response there.';
    toRender.urltotest = base_catalog_url + '/opc/oracleTransparentPunchout/search'
    res.render('formXML', toRender);
  } catch (e) {
    next(e);
  }
});

app.post('/sendxml', async (req, res, next) => {
  try {
    const result = await axios.post(
      req.body.urltotest,
      req.body.xmltosend,
      { headers: { 'Content-type': 'text/xml; charset=ISO-8859-1' } }
    );
    res.contentType("text/xml");
    res.send(result.data);
  } catch (e) {
    next(e);
  }
});

app.listen(port, host, async () => {
  try {
    await formManager.setSetupConfigs(starterConfigPath, configPath, {
      BASE_CATALOG_URL: base_catalog_url,
      BASE_HOOK_URL: base_hook_url
    });
    console.log(`${new Date()}`);
    console.log('Start listening on port: ', port);
  } catch (e) {
    console.log('!!!ERROR!!!');
    console.log(e.message);
  }
});