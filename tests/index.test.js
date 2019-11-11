const cheerio = require('cheerio')
const pug = require('pug');
const renderFormPage = pug.compileFile('./views/index.pug');

const formsNames = [
  'formName1',
  'formName2',
  'formName3',
  'formName4',
  'null',
  'undefined',
]

describe('index.html', () => {
  test('should render index.html with forms names', () => {
    const $ = cheerio.load(renderFormPage({ formsNames }));
    Array.from($('li > button > a')).forEach((a, index) => {
      expect(a.firstChild.data).toBe(formsNames[index]);
      expect(a.attribs.href).toBe(`/form?name=${formsNames[index]}`);
    })
  });
})
